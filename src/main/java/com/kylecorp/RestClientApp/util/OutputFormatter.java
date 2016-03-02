package com.kylecorp.RestClientApp.util;

import java.text.DecimalFormat;
import java.util.Set;

public class OutputFormatter
{
	public static String output(LocationWeatherInfo locationWeatherInfo)
	{
		DecimalFormat df = new DecimalFormat("#.00");

		StringBuffer sb = new StringBuffer();
		if (locationWeatherInfo != null)
		{
			sb.append("Weather for ").append(
					locationWeatherInfo.getPostalCode());

			Set<LocationInfo> locationInfoSet = locationWeatherInfo
					.getLocationInfoSet();
			for (LocationInfo locationInfo : locationInfoSet)
			{
				if (locationInfoSet.size() > 1)
				{
					sb.append("\n\t");
				}
				sb.append(" ").append(locationInfo.getCity()).append(", ")
						.append(locationInfo.getRegion()).append(", ")
						.append(locationWeatherInfo.getCountry()).append(" ")
						.append(df.format(locationInfo.getTemperature()))
						.append("F").append(" and ")
						.append(locationInfo.getWeather());
			}
		}

		return sb.toString();
	}
}
