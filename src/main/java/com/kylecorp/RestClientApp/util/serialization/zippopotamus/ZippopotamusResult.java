package com.kylecorp.RestClientApp.util.serialization.zippopotamus;


import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public final class ZippopotamusResult
{
    @JsonProperty(value = "country abbreviation")
    private String      countryCode;

    @JsonProperty(value = "post code")
    private String      postalCode;

    // Not all zip codes are exlusive to a single place, and some zip codes
    // might span multiple states. However for the sake of this project, will be
    // utilizing information from index 0. This will need to be re-written to
    // accommodate.
    @JsonProperty("places")
    private List<Place> places = new LinkedList<Place>();


    public String getCountryCode()
    {
        return countryCode;
    }


    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }


    public String getPostalCode()
    {
        return postalCode;
    }


    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }


    public List<Place> getPlaces()
    {
        return places;
    }


    public void setPlaces(List<Place> places)
    {
        this.places = places;
    }

}
