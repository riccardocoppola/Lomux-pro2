package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Song {
    private int idSong;
    private String title;
    private String lyrics;
    private Artist artistidArtist;
    private List<SongHasMediatype> songHasMediatypeDTOList;
    //private List<PinDTO> pinDTOList;

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Artist getArtistidArtist() {
        return artistidArtist;
    }

    public void setArtistidArtist(Artist artistidArtist) {
        this.artistidArtist = artistidArtist;
    }

    public List<SongHasMediatype> getSongHasMediatypeDTOList() {
        return songHasMediatypeDTOList;
    }

    public void setSongHasMediatypeDTOList(List<SongHasMediatype> songHasMediatypeDTOList) {
        this.songHasMediatypeDTOList = songHasMediatypeDTOList;
    }
}
