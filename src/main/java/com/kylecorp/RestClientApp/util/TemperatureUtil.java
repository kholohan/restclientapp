package com.kylecorp.RestClientApp.util;

public class TemperatureUtil
{
	public static double kelvinToFarenheit(double value)
	{
		double farenheit = value * 1.8 - 459.67;
		return farenheit;

	}
}
