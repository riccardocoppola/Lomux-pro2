package com.example.stefano.lomux_pro.model;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Artist {

    private int idArtist;
    private String name;
    //private List<SongDTO> songDTOList;

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
