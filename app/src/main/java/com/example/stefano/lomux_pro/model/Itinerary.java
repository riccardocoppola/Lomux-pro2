package com.example.stefano.lomux_pro.model;

import android.content.Context;

import com.example.stefano.lomux_pro.R;

import java.util.List;

/**
 * Created by s241972 on 06/11/2017.
 */

public class Itinerary {
    private int idItinerary;

    private String name;

    private String info;

    private int image; //0 = non c'è, 1 = c'è
    //se presente cerca

    private List<Pin> pinDTOList;

    public int getIdItinerary() {
        return idItinerary;
    }

    public void setIdItinerary(int idItinerary) {
        this.idItinerary = idItinerary;
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

    public int getImage() {
        return image;
    }

    public String getImageUrl(Context context)
    {
        if (image!=0)
            return context.getString(R.string.image_path) + idItinerary + ".png";
        else
            return "0";
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Pin> getPinDTOList() {
        return pinDTOList;
    }

    public void setPinDTOList(List<Pin> pinDTOList) {
        this.pinDTOList = pinDTOList;
    }
}
