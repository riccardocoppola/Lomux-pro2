package com.example.stefano.lomux_pro.model;

import android.app.Application;
import android.content.Context;

import com.example.stefano.lomux_pro.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.math.BigDecimal;
import java.util.List;

import static android.provider.Settings.System.getString;


/**
 * Created by FrancescoMargiotta on 18/07/2017.
 */

public class Pin implements ClusterItem {

    private String idPin;
    private BigDecimal lat;
    private BigDecimal lon;
    private String title;
    private String subtitle;
    private String info;
    private String sourceLink;
    private Short image;
    private Integer relevance;
    private List<Artist> artistDTOList;
    private List<Album> albumDTOList;
    private List<Itinerary> itineraryDTOList;
    private List<Song> songDTOList;
    private List<PinHasPintype> pinHasPintypeDTOList;
    private Source sourceidSource;


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

    public Short getImage() {
        return image;
    }

    public void setImage(Short image) {
        this.image = image;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public List<Artist> getArtistDTOList() {
        return artistDTOList;
    }

    public void setArtistDTOList(List<Artist> artistDTOList) {
        this.artistDTOList = artistDTOList;
    }

    public List<Album> getAlbumDTOList() {
        return albumDTOList;
    }

    public void setAlbumDTOList(List<Album> albumDTOList) {
        this.albumDTOList = albumDTOList;
    }

    public List<Itinerary> getItineraryDTOList() {
        return itineraryDTOList;
    }

    public void setItineraryDTOList(List<Itinerary> itineraryDTOList) {
        this.itineraryDTOList = itineraryDTOList;
    }

    public List<Song> getSongDTOList() {
        return songDTOList;
    }

    public void setSongDTOList(List<Song> songDTOList) {
        this.songDTOList = songDTOList;
    }

    public List<PinHasPintype> getPinHasPintypeDTOList() {
        return pinHasPintypeDTOList;
    }

    public void setPinHasPintypeDTOList(List<PinHasPintype> pinHasPintypeDTOList) {
        this.pinHasPintypeDTOList = pinHasPintypeDTOList;
    }

    public Source getSourceidSource() {
        return sourceidSource;
    }

    public void setSourceidSource(Source sourceidSource) {
        this.sourceidSource = sourceidSource;
    }

    public String getImageUrl(Context context)
    {
        if (image!=0)
            return context.getString(R.string.image_path) + idPin + ".png";
        else
           return null;
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
