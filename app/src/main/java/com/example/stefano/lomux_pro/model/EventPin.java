package com.example.stefano.lomux_pro.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

/**
 * Created by Franc on 22/03/2018.
 */

public class EventPin extends Pin
{
    Event event;

    public EventPin(Event e)
    {
        super(e.getLocation(), Pintype.EVENT);
        this.event = e;
    }

    @Override
    public String getTitle() {
        return event.getName();
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public Gallery getPictures() {
        return event.getPoster();
    }

    @Override
    public GeoPoint getLocation() {
        return event.getLocation();
    }

    @Override
    public List<Artist> getArtists() {
        return event.getArtists();
    }

    @Override
    public String getInfo() {
        return event.getDescription();
    }

    @Override
    public String getSource() {
        return event.getLinkSource();
    }

    public boolean aderisci()
    {
        return false;
    }
}
