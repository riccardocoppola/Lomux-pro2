package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Source {
    private Integer idSource;
    private String sourceName;
    private List<Pin> pinDTOList;

    public Integer getIdSource() {
        return idSource;
    }

    public void setIdSource(Integer idSource) {
        this.idSource = idSource;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<Pin> getPinDTOList() {
        return pinDTOList;
    }

    public void setPinDTOList(List<Pin> pinDTOList) {
        this.pinDTOList = pinDTOList;
    }
}
