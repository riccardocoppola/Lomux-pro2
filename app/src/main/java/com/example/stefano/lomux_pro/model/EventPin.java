package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Franc on 22/03/2018.
 */

public class EventPin extends Pin
{
    Event event;

    public EventPin(Event e)
    {
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
    public Location getLocation() {
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
