package com.example.stefano.lomux_pro.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

/**
 * Created by Franc on 26/03/2018.
 */

public class Pin implements Pinnable, ClusterItem {

    public Location location;
    public String title;

    public Pin(String title, Location location)
    {
        this.title = title;
        this.location = location;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(location.getLat().doubleValue(), location.getLon().doubleValue());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public Gallery getPictures() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public List<Artist> getArtists() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getSource() {
        return null;
    }
}
