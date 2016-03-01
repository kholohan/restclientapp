package com.kylecorp.RestClientApp.util.serialization.openweather;


import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public final class OpenWeatherResult
{
    @JsonProperty(value = "main")
    private Temperature   temperature;

    @JsonProperty(value = "weather")
    private List<Weather> weatherSet = new LinkedList<Weather>();


    public Temperature getTemperature()
    {
        return temperature;
    }


    public void setTemperature(Temperature temperature)
    {
        this.temperature = temperature;
    }


    public List<Weather> getWeatherSet()
    {
        return weatherSet;
    }


    public void setWeatherSet(List<Weather> weatherSet)
    {
        this.weatherSet = weatherSet;
    }
}
