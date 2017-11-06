package com.example.stefano.lomux_pro.model;

/**
 * Created by s241972 on 06/11/2017.
 */

public class AlbumHasMediatype {
    protected AlbumHasMediatypePK albumHasMediatypeDTOPK;
    private String urlMedia;
    private Album albumDTO;
    private Mediatype mediatypeDTO;

    public AlbumHasMediatypePK getAlbumHasMediatypeDTOPK() {
        return albumHasMediatypeDTOPK;
    }

    public void setAlbumHasMediatypeDTOPK(AlbumHasMediatypePK albumHasMediatypeDTOPK) {
        this.albumHasMediatypeDTOPK = albumHasMediatypeDTOPK;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public Album getAlbumDTO() {
        return albumDTO;
    }

    public void setAlbumDTO(Album albumDTO) {
        this.albumDTO = albumDTO;
    }

    public Mediatype getMediatypeDTO() {
        return mediatypeDTO;
    }

    public void setMediatypeDTO(Mediatype mediatypeDTO) {
        this.mediatypeDTO = mediatypeDTO;
    }
}
