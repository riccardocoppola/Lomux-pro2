package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Stefano on 16/10/2017.
 */

public enum Pintype {

    EVENT(EventPin.class.getSimpleName()),
    TRIVIA(TriviaPin.class.getSimpleName()),
    VENUE(VenuePin.class.getSimpleName())
    ;

    private String typeName;

    Pintype(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    /*
    VENUE,
    STUDIO,
    WORK,
    PRIVATE,
    MONUMENT,
    LOTM*/


   /* private String type;
    private Integer idPinType;
    private List<PinHasPintype> pinHasPintypeDTOList;

    public Integer getIdPinType() {
        return idPinType;
    }

    public void setIdPinType(Integer idPinType) {
        this.idPinType = idPinType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PinHasPintype> getPinHasPintypeDTOList() {
        return pinHasPintypeDTOList;
    }

    public void setPinHasPintypeDTOList(List<PinHasPintype> pinHasPintypeDTOList) {
        this.pinHasPintypeDTOList = pinHasPintypeDTOList;
    }*/
}
