package com.example.stefano.lomux_pro.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

/**
 * Created by Franc on 23/03/2018.
 */

public class TriviaPin extends Pin
{
    private Trivia trivia;

    public TriviaPin(Trivia t) {
        super(t.getName(), t.getLocation());
        this.trivia = t;

    }

    @Override
    public String getTitle() {
        return trivia.getName();
    }

    @Override
    public String getSubTitle() {
        return null;
    }

    @Override
    public Gallery getPictures() {
        return trivia.getPictures();
    }

    @Override
    public GeoPoint getLocation() {
        return trivia.getLocation();
    }

    @Override
    public List<Artist> getArtists() {
        return trivia.getArtists();
    }

    @Override
    public String getInfo() {
        return trivia.getInfo();
    }

    @Override
    public String getSource() {
        return null;
    }
}
