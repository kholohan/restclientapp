package com.kylecorp.RestClientApp.util.serialization.openweather;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Temperature
{
    @JsonProperty(value = "temp")
    private Double temperature; // kelvin


    public Double getTemperature()
    {
        return temperature;
    }


    public void setTemperature(Double temperature)
    {
        this.temperature = temperature;
    }

}
