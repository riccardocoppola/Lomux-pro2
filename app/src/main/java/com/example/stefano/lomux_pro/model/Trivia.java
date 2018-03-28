package com.example.stefano.lomux_pro.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

/**
 * Created by Franc on 23/03/2018.
 */

public class Trivia
{
    private String id;
    private String name;
    private String info;
    private List<Artist> artists;
    private GeoPoint location;
    private Gallery pictures;
    private List<Genre> genres;

    public Trivia(String id,
                  String name,
                  String info,
                  List<Artist> artists,
                  GeoPoint location,
                  Gallery pictures,
                  List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.artists = artists;
        this.location = location;
        this.pictures = pictures;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Gallery getPictures() {
        return pictures;
    }

    public void setPictures(Gallery pictures) {
        this.pictures = pictures;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
