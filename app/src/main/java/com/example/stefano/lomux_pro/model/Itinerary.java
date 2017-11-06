package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by s241972 on 06/11/2017.
 */

public class Itinerary {
    private int idItinerary;

    private String name;

    private String info;

    private Short image;

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

    public Short getImage() {
        return image;
    }

    public void setImage(Short image) {
        this.image = image;
    }

    public List<Pin> getPinDTOList() {
        return pinDTOList;
    }

    public void setPinDTOList(List<Pin> pinDTOList) {
        this.pinDTOList = pinDTOList;
    }
}
