package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Franc on 23/03/2018.
 */

public class TriviaPin implements Pin
{
    private Trivia trivia;

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
    public Location getLocation() {
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
