package com.kylecorp.RestClientApp.util;

import java.util.HashSet;
import java.util.Set;

public class LocationWeatherInfo
{
	private String				postalCode;
	private String				country;
	private Set<LocationInfo>	locationInfoSet	= new HashSet<LocationInfo>();	// A
																				// zip
																				// code
																				// may
																				// not
																				// be
																				// exclusive
																				// with
																				// one
																				// city/state
																				// ex:
																				// 86515
																				// belongs
																				// to
																				// both
																				// Arizona
																				// and
																				// New
																				// Mexico

	public LocationWeatherInfo()
	{

	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public Set<LocationInfo> getLocationInfoSet()
	{
		return locationInfoSet;
	}

	public void setLocationWeatherInfoSet(Set<LocationInfo> locationInfoSet)
	{
		this.locationInfoSet = locationInfoSet;
	}

	public void addLocationInfo(LocationInfo locationInfo)
	{
		locationInfoSet.add(locationInfo);
	}

}
