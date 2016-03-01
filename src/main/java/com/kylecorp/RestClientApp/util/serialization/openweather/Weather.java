package com.kylecorp.RestClientApp.util.serialization.openweather;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Weather
{
    @JsonProperty(value = "main")
    private String shortDescription;

    @JsonProperty(value = "description")
    private String description;


    public String getShortDescription()
    {
        return shortDescription;
    }


    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }
}
