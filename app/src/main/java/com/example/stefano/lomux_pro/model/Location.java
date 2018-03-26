package com.example.stefano.lomux_pro.model;

import java.math.BigDecimal;

/**
 * Created by Franc on 22/03/2018.
 */

public class Location
{
    private BigDecimal lat;
    private BigDecimal lon;
    private String address;

    public Location(Double lat, Double lon)
    {
        this.lat = BigDecimal.valueOf(lat.doubleValue());
        this.lon = BigDecimal.valueOf(lon.doubleValue());
    }

    public Location(double lat, double lon)
    {
        this.lat = BigDecimal.valueOf(lat);
        this.lon = BigDecimal.valueOf(lon);
    }

    public Location(String address)
    {
        this.address = address;
    }
}
