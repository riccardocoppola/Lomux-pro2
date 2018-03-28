package com.example.stefano.lomux_pro.model;

import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;


public interface Pinnable extends ClusterItem{
    String getTitle();
    String getSubTitle();
    Gallery getPictures();
    GeoPoint getLocation();
    List<Artist> getArtists();
    String getInfo();
    String getSource();
}
