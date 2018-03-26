package com.example.stefano.lomux_pro.model;

import android.app.Application;
import android.content.Context;

import com.example.stefano.lomux_pro.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static android.provider.Settings.System.getString;


/**
 * Created by FrancescoMargiotta on 18/07/2017.
 */

public interface Pinnable {
    String getTitle();
    String getSubTitle();
    Gallery getPictures();
    Location getLocation();
    List<Artist> getArtists();
    String getInfo();
    String getSource();
}
