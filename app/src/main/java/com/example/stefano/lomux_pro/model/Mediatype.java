package com.example.stefano.lomux_pro.model;

import java.util.List;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Mediatype {
    private int idMediaType;

    private String media;
    private List<SongHasMediatype> songHasMediatypeDTOList;
    private List<AlbumHasMediatype> albumHasMediatypeDTOList;

    public int getIdMediaType() {
        return idMediaType;
    }

    public void setIdMediaType(int idMediaType) {
        this.idMediaType = idMediaType;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public List<SongHasMediatype> getSongHasMediatypeDTOList() {
        return songHasMediatypeDTOList;
    }

    public void setSongHasMediatypeDTOList(List<SongHasMediatype> songHasMediatypeDTOList) {
        this.songHasMediatypeDTOList = songHasMediatypeDTOList;
    }

    public List<AlbumHasMediatype> getAlbumHasMediatypeDTOList() {
        return albumHasMediatypeDTOList;
    }

    public void setAlbumHasMediatypeDTOList(List<AlbumHasMediatype> albumHasMediatypeDTOList) {
        this.albumHasMediatypeDTOList = albumHasMediatypeDTOList;
    }
}
