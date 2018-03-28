package com.example.stefano.lomux_pro.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

/**
 * Created by Franc on 26/03/2018.
 */

public abstract class Pin implements Pinnable, ClusterItem {

    public GeoPoint location;
    public Pintype pintype;


    public Pin(GeoPoint location, Pintype pintype)
    {
        this.location = location;
        this.pintype = pintype;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
    @Override
    public String getSnippet() {
        return null;
    }
}
