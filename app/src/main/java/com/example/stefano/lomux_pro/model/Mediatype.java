package com.example.stefano.lomux_pro.model;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Mediatype {
    private Integer idMediaType;
    private String media;

    //private List<SongHasMediatype> songHasMediatypeDTOList;

    public Integer getIdMediaType() {
        return idMediaType;
    }

    public void setIdMediaType(Integer idMediaType) {
        this.idMediaType = idMediaType;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
