package com.example.stefano.lomux_pro.model;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Source {
    private int idSource;
    private String sourceName;
    //private List<PinDTO> pinDTOList;

    public int getIdSource() {
        return idSource;
    }

    public void setIdSource(int idSource) {
        this.idSource = idSource;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
