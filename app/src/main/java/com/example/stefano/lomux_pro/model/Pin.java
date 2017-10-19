package com.example.stefano.lomux_pro.model;

import android.app.Application;
import android.content.Context;

import com.example.stefano.lomux_pro.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.math.BigDecimal;

import static android.provider.Settings.System.getString;


/**
 * Created by FrancescoMargiotta on 18/07/2017.
 */

public class Pin implements ClusterItem {

    private String idPin;
    private BigDecimal lat;
    private BigDecimal lon;
    private String title="";
    private String subtitle="";
    private String info;
    private String sourceLink;
    private short  image;
    private int  relevance;
    //private List<Itinerary> itineraryDTOList;
    private Pintype pinTypeidPinType;
    private Song songidSong;
    private Source sourceidSource;




    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public short getImage() {
        return image;
    }

    public void setImage(short image) {
        this.image = image;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public Pintype getPinTypeidPinType() {
        return pinTypeidPinType;
    }

    public void setPinTypeidPinType(Pintype pinTypeidPinType) {
        this.pinTypeidPinType = pinTypeidPinType;
    }

    public String getIdPin() {
        return idPin;
    }

    public void setIdPin(String idPin) {
        this.idPin = idPin;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getImageUrl(Context context)
    {
        if (image!=0)
            return context.getString(R.string.image_path) + idPin + ".png";
        else
            return null;
    }

    public Song getSongidSong() {
        return songidSong;
    }

    public void setSongidSong(Song songidSong) {
        this.songidSong = songidSong;
    }

    public Source getSourceidSource() {
        return sourceidSource;
    }

    public void setSourceidSource(Source sourceidSource) {
        this.sourceidSource = sourceidSource;
    }

    @Override
    public LatLng getPosition() {
        LatLng ret = new LatLng(lat.doubleValue(),lon.doubleValue());
        return ret;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return subtitle;
    }


}
