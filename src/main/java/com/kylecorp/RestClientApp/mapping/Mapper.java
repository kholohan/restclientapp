package com.kylecorp.RestClientApp.mapping;


import java.util.List;

import com.kylecorp.RestClientApp.util.LocationInfo;
import com.kylecorp.RestClientApp.util.LocationWeatherInfo;
import com.kylecorp.RestClientApp.util.TemperatureUtil;
import com.kylecorp.RestClientApp.util.serialization.freegeo.FreeGeoResult;
import com.kylecorp.RestClientApp.util.serialization.openweather.OpenWeatherResult;
import com.kylecorp.RestClientApp.util.serialization.openweather.Temperature;
import com.kylecorp.RestClientApp.util.serialization.openweather.Weather;
import com.kylecorp.RestClientApp.util.serialization.zippopotamus.Place;
import com.kylecorp.RestClientApp.util.serialization.zippopotamus.ZippopotamusResult;


// Map deserialized classes into LocationWeatherInfo
public class Mapper
{
    public static void mapFreeGeoToLocationWeatherInfo(FreeGeoResult result, LocationWeatherInfo locationWeatherInfo)
    {
        // null check
        locationWeatherInfo.setPostalCode(result.getPostalCode());
        locationWeatherInfo.setCountry(result.getCountryCode());

        LocationInfo locationInfo = new LocationInfo();

        locationInfo.setCity(result.getCity());
        locationInfo.setLatitude(result.getLatitude());
        locationInfo.setLongitude(result.getLongitude());

        locationWeatherInfo.addLocationInfo(locationInfo);

    }


    public static void mapZippopotamusToLocationWeatherInfo(ZippopotamusResult result, LocationWeatherInfo locationWeatherInfo)
    {
        // null check
        locationWeatherInfo.setCountry(result.getCountryCode());

        for (Place place : result.getPlaces())
        {
            LocationInfo locationInfo = new LocationInfo();

            locationInfo.setCity(place.getCity());
            locationInfo.setLatitude(place.getLatitude());
            locationInfo.setLongitude(place.getLongitude());

            locationWeatherInfo.addLocationInfo(locationInfo);
        }

    }


    public static void mapOpenWeatherToLocationInfo(OpenWeatherResult openWeatherResult, LocationInfo locationInfo)
    {
        // null check
        Temperature temperature = openWeatherResult.getTemperature();
        Double temperatureKelvin = temperature.getTemperature();
        Double temperatureFarenheit = TemperatureUtil.kelvinToFarenheit(temperatureKelvin);
        locationInfo.setTemperature(temperatureFarenheit);

        List<Weather> weatherSet = openWeatherResult.getWeatherSet();
        Weather weather = weatherSet.iterator().next();
        locationInfo.setWeatherDescription(weather.getDescription());
        locationInfo.setWeather(weather.getShortDescription());

    }
}
