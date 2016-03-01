package com.kylecorp.RestClientApp.util;


public class OutputFormatter
{
    public static String output(LocationWeatherInfo locationWeatherInfo)
    {
        StringBuilder sb = new StringBuilder(); // scoped to this stack, so not
                                                // worried too much about thread
                                                // safety
        if (locationWeatherInfo != null)
        {
            sb.append("Weather for ").append(locationWeatherInfo.getPostalCode());

            for (LocationInfo locationInfo : locationWeatherInfo.getLocationInfoSet())
            {
                sb.append("\n\t").append(locationInfo.getCity()).append(", ").append(locationInfo.getRegion()).append(", ")
                  .append(locationWeatherInfo.getCountry()).append("\t").append(locationInfo.getTemperature()).append("F")
                  .append(" and ").append(locationInfo.getWeather());
            }
        }

        return sb.toString();
    }
}
