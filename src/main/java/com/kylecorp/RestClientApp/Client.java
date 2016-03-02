package com.kylecorp.RestClientApp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.kylecorp.RestClientApp.logging.LogService;
import com.kylecorp.RestClientApp.mapping.Mapper;
import com.kylecorp.RestClientApp.rest.RestClient;
import com.kylecorp.RestClientApp.util.LocationInfo;
import com.kylecorp.RestClientApp.util.LocationWeatherInfo;
import com.kylecorp.RestClientApp.util.OutputFormatter;
import com.kylecorp.RestClientApp.util.serialization.freegeo.FreeGeoResult;
import com.kylecorp.RestClientApp.util.serialization.openweather.OpenWeatherResult;
import com.kylecorp.RestClientApp.util.serialization.zippopotamus.ZippopotamusResult;

public class Client
{
	private final static String								CURRENT_LOCATION_KEY		= "CURRENT_LOCATION_KEY";

	private final static long								DEFAULT_CACHE_SIZE			= 50;
	private final static long								DEFAULT_CACHE_TIMEOUT		= 120;																				// seconds
	private final static String								FREE_GEO_URI				= "https://freegeoip.net/json/";
	private final static Logger								LOGGER						= LogService
																								.getLogger();

	private final static String								OPEN_WEATHER_MAP_APP_ID		= "&appid=284fb95647882aa3e8c4a8f556b00c51";
	private final static String								OPEN_WEATHER_MAP_URI_LAT	= "http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}";
	private final static String								OPEN_WEATHER_MAP_URI_ZIP	= "http://api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}";

	private final static String								UNITED_STATES				= "us";
	private final static String								ZIPPOPOTAMUS_URI			= "http://api.zippopotam.us/{country}/{postalCode}";
	private final LoadingCache<String, LocationWeatherInfo>	loadingCache;
	private final ExecutorService							loadingCacheExecutor;
	private long											maxCacheElements;

	private final RestClient								restClient;

	public Client()
	{
		loadingCache = buildLoadingCache(DEFAULT_CACHE_SIZE);
		loadingCacheExecutor = Executors.newCachedThreadPool();

		restClient = new RestClient();
	}

	public static Client createClient()
	{
		return new Client();
	}

	private LoadingCache<String, LocationWeatherInfo> buildLoadingCache(
			final long maxCacheElements)
	{

		final CacheLoader<String, LocationWeatherInfo> loader = new CacheLoader<String, LocationWeatherInfo>()
		{
			private LocationWeatherInfo getLocationWeatherInfo(
					final String postalCode)
			{
				return buildLocationWeatherInfo(postalCode);

			}

			@Override
			public LocationWeatherInfo load(final String zipCode)
			{
				return getLocationWeatherInfo(zipCode);
			}

			@Override
			public ListenableFuture<LocationWeatherInfo> reload(
					final String postalCode, final LocationWeatherInfo prev)
			{
				final ListenableFutureTask<LocationWeatherInfo> task = ListenableFutureTask
						.create(new Callable<LocationWeatherInfo>()
						{
							@Override
							public LocationWeatherInfo call()
							{
								return getLocationWeatherInfo(postalCode);
							}
						});

				loadingCacheExecutor.execute(task);

				return task;
			}

		};

		this.maxCacheElements = maxCacheElements;
		return CacheBuilder.newBuilder().maximumSize(maxCacheElements)
				.expireAfterWrite(DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS)
				.build(loader);
	}

	private LocationWeatherInfo buildLocationWeatherInfo(final String postalCode)
	{
		final LocationWeatherInfo locationWeatherInfo = new LocationWeatherInfo();
		// Get postalCode and lat/long if none specified
		if (postalCode.equals(CURRENT_LOCATION_KEY))
		{
			final FreeGeoResult freeGeoResult = getFreeGeoResult(locationWeatherInfo);
			Mapper.mapFreeGeoToLocationWeatherInfo(freeGeoResult,
					locationWeatherInfo);
		} else
		{
			locationWeatherInfo.setPostalCode(postalCode);
			final ZippopotamusResult zippopotamusResult = getZippopotamusResult(
					locationWeatherInfo, postalCode);
			Mapper.mapZippopotamusToLocationWeatherInfo(zippopotamusResult,
					locationWeatherInfo);
		}

		for (final LocationInfo locationInfo : locationWeatherInfo
				.getLocationInfoSet())
		{
			final OpenWeatherResult openWeatherResult = getOpenWeatherResult(
					locationWeatherInfo, locationInfo);
			Mapper.mapOpenWeatherToLocationInfo(openWeatherResult, locationInfo);
		}

		return locationWeatherInfo;
	}

	private FreeGeoResult getFreeGeoResult(
			final LocationWeatherInfo locationWeatherInfo)
	{
		// Parse country_code, region_code, city, zip_code, latitude, longitude
		final FreeGeoResult result = restClient.restGetOperation(FREE_GEO_URI,
				FreeGeoResult.class);
		return result;
	}

	private OpenWeatherResult getOpenWeatherResult(
			final LocationWeatherInfo locationWeatherInfo,
			final LocationInfo locationInfo)
	{

		final Double latitude = locationInfo.getLatitude();
		final Double longitude = locationInfo.getLongitude();
		final String zipCode = locationWeatherInfo.getPostalCode();
		final String country = locationWeatherInfo.getCountry();

		OpenWeatherResult openWeatherResult = null;

		if (latitude != null && longitude != null)
		{
			openWeatherResult = restClient.restGetOperation(
					OPEN_WEATHER_MAP_URI_LAT + OPEN_WEATHER_MAP_APP_ID,
					OpenWeatherResult.class, latitude, longitude);
		} else if (zipCode != null)
		{
			openWeatherResult = restClient.restGetOperation(
					OPEN_WEATHER_MAP_URI_ZIP + OPEN_WEATHER_MAP_APP_ID,
					OpenWeatherResult.class, zipCode, country);
		}

		return openWeatherResult;
	}

	public String getWeather(final String postalCode)
	{
		LocationWeatherInfo locationWeatherInfo = getWeatherInfo(postalCode);
		return OutputFormatter.output(locationWeatherInfo);
	}

	public String getWeather()
	{
		LocationWeatherInfo locationWeatherInfo = getWeatherInfo(CURRENT_LOCATION_KEY);
		return OutputFormatter.output(locationWeatherInfo);
	}

	private LocationWeatherInfo getWeatherInfo(final String postalCode)
	{

		final LocationWeatherInfo locationWeatherInfo = null;
		try
		{
			return loadingCache.get(postalCode);
		} catch (final ExecutionException ex)
		{
			LOGGER.warn("Unable to retrieve the weather");
		}

		return locationWeatherInfo;
	}

	// In the future we may want to do countries other than US
	private ZippopotamusResult getZippopotamusResult(
			final LocationWeatherInfo locationWeatherInfo,
			final String postalCode)
	{
		// http://api.zippopotam.us/us/
		final ZippopotamusResult result = restClient.restGetOperation(
				ZIPPOPOTAMUS_URI, ZippopotamusResult.class, UNITED_STATES,
				postalCode);
		return result;
	}

}
