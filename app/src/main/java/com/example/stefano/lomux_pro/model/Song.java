package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Song {
    private Integer idSong;
    private String title;
    private List<Artist> artistDTOList;
    private List<Pin> pinDTOList;
    private Album albumidAlbum;
    private Lyrics lyricsidLyrics;
    private List<SongHasMediatype> songHasMediatypeDTOList;

    public Integer getIdSong() {
        return idSong;
    }

    public void setIdSong(Integer idSong) {
        this.idSong = idSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Album getAlbumidAlbum() {
        return albumidAlbum;
    }

    public void setAlbumidAlbum(Album albumidAlbum) {
        this.albumidAlbum = albumidAlbum;
    }

    public Lyrics getLyricsidLyrics() {
        return lyricsidLyrics;
    }

    public void setLyricsidLyrics(Lyrics lyricsidLyrics) {
        this.lyricsidLyrics = lyricsidLyrics;
    }

    public List<SongHasMediatype> getSongHasMediatypeDTOList() {
        return songHasMediatypeDTOList;
    }

    public void setSongHasMediatypeDTOList(List<SongHasMediatype> songHasMediatypeDTOList) {
        this.songHasMediatypeDTOList = songHasMediatypeDTOList;
    }
}
