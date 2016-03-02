package com.kylecorp.RestClientApp.util;

public class LocationInfo
{
	private String	city;
	private String	region;			// State, region or empty
	private Double	latitude;			// Lat and long used to retrieve weather
										// from
										// http://openweathermap.org/current
	private Double	longitude;
	private Double	temperature;		// in F
	private String	weatherDescription;
	private String	weather;

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getTemperature()
	{
		return temperature;
	}

	public void setTemperature(double temperature)
	{
		this.temperature = temperature;
	}

	public String getWeatherDescription()
	{
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription)
	{
		this.weatherDescription = weatherDescription;
	}

	public String getWeather()
	{
		return weather;
	}

	public void setWeather(String weather)
	{
		this.weather = weather;
	}
}
