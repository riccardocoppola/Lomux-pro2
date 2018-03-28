package com.example.stefano.lomux_pro.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

/**
 * Created by Franc on 22/03/2018.
 */

public class Venue {
    private String id;
    private String venueName;
    private String venueInfo;
    private List<Event> events;
    private GeoPoint location;
    private Gallery gallery;


    public Venue(String id,
                 String venueName,
                 String venueInfo,
                 List<Event> events,
                 GeoPoint location,
                 Gallery gallery) {
        this.id = id;
        this.venueName = venueName;
        this.venueInfo = venueInfo;
        this.events = events;
        this.location = location;
        this.gallery = gallery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueInfo() {
        return venueInfo;
    }

    public void setVenueInfo(String venueInfo) {
        this.venueInfo = venueInfo;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public GeoPoint getLocation()
    {
        return this.location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public List<Event> getEvents() {
        return events;
    }
}
