package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by s241972 on 06/11/2017.
 */

public class Album {
    private int idAlbum;
    private String name;
    private String year;
    private List<Artist> artistDTOList;
    private List<Pin> pinDTOList;
    private List<Song> songDTOList;
    private List<AlbumHasMediatype> albumHasMediatypeDTOList;

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Artist> getArtistDTOList() {
        return artistDTOList;
    }

    public void setArtistDTOList(List<Artist> artistDTOList) {
        this.artistDTOList = artistDTOList;
    }

    public List<Pin> getPinDTOList() {
        return pinDTOList;
    }

    public void setPinDTOList(List<Pin> pinDTOList) {
        this.pinDTOList = pinDTOList;
    }

    public List<Song> getSongDTOList() {
        return songDTOList;
    }

    public void setSongDTOList(List<Song> songDTOList) {
        this.songDTOList = songDTOList;
    }

    public List<AlbumHasMediatype> getAlbumHasMediatypeDTOList() {
        return albumHasMediatypeDTOList;
    }

    public void setAlbumHasMediatypeDTOList(List<AlbumHasMediatype> albumHasMediatypeDTOList) {
        this.albumHasMediatypeDTOList = albumHasMediatypeDTOList;
    }
}
