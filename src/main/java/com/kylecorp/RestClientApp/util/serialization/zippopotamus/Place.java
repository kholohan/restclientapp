package com.kylecorp.RestClientApp.util.serialization.zippopotamus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place
{
	@JsonProperty(value = "state abbreviation")
	private String	regionCode;

	@JsonProperty(value = "place name")
	private String	city;

	@JsonProperty(value = "latitude")
	private Double	latitude;

	@JsonProperty(value = "longitude")
	private Double	longitude;

	public String getRegionCode()
	{
		return regionCode;
	}

	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}
}
