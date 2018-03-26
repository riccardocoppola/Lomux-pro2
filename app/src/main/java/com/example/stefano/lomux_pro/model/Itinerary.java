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

    private Short image; //0 = non c'è, 1 = c'è
    //se presente cerca

    private List<Pinnable> pinDTOList;

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

    public Short getImage() {
        return image;
    }

    public String getImageUrl(Context context)
    {
        if (image!=0)
            return context.getString(R.string.image_path) + idItinerary + ".png";
        else
            return "0";
    }

    public void setImage(Short image) {
        this.image = image;
    }

    public List<Pinnable> getPinDTOList() {
        return pinDTOList;
    }

    public void setPinDTOList(List<Pinnable> pinDTOList) {
        this.pinDTOList = pinDTOList;
    }
}
