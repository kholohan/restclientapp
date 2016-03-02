package com.kylecorp.RestClientApp.util.serialization.freegeo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FreeGeoResult
{
	@JsonProperty(value = "country_code")
	private String	countryCode;

	@JsonProperty(value = "region_code")
	private String	regionCode;

	@JsonProperty(value = "city")
	private String	city;

	@JsonProperty(value = "latitude")
	private Double	latitude;

	@JsonProperty(value = "longitude")
	private Double	longitude;

	@JsonProperty(value = "zip_code")
	private String	postalCode;

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

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

	public String getPostalCode()
	{
		return postalCode;
	}

}
