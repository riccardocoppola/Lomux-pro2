package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Franc on 23/03/2018.
 */

public class VeneuPin implements Pin
{
    private Venue venue;

    public VeneuPin(Venue v)
    {
        this.venue = v;
    }


    @Override
    public String getTitle() {
        return venue.getVenueName();
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public Gallery getPictures() {
        return venue.getGallery();
    }

    @Override
    public Location getLocation() {
        return venue.getLocation();
    }

    @Override
    public List<Artist> getArtists() {
        return null;
    }

    @Override
    public String getInfo() {
        return venue.getVenueInfo();
    }

    @Override
    public String getSource() {
        return null;
    }
}
