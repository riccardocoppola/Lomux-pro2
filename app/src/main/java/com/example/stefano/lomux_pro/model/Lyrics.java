package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by s241972 on 06/11/2017.
 */

public class Lyrics {
    private int idLyrics;
    private String lyrics;
    private String urlLirics;
    private List<Song> songDTOList;

    public int getIdLyrics() {
        return idLyrics;
    }

    public void setIdLyrics(int idLyrics) {
        this.idLyrics = idLyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getUrlLirics() {
        return urlLirics;
    }

    public void setUrlLirics(String urlLirics) {
        this.urlLirics = urlLirics;
    }

    public List<Song> getSongDTOList() {
        return songDTOList;
    }

    public void setSongDTOList(List<Song> songDTOList) {
        this.songDTOList = songDTOList;
    }
}
