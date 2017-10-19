package com.example.stefano.lomux_pro.model;

/**
 * Created by Stefano on 18/10/2017.
 */

public class SongHasMediatype {

    protected SongHasMediatypePK songHasMediatypeDTOPK;
    private String urlMedia;
    private Mediatype mediatypeDTO;
    private Song songDTO;

    public SongHasMediatypePK getSongHasMediatypeDTOPK() {
        return songHasMediatypeDTOPK;
    }

    public void setSongHasMediatypeDTOPK(SongHasMediatypePK songHasMediatypeDTOPK) {
        this.songHasMediatypeDTOPK = songHasMediatypeDTOPK;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public Mediatype getMediatypeDTO() {
        return mediatypeDTO;
    }

    public void setMediatypeDTO(Mediatype mediatypeDTO) {
        this.mediatypeDTO = mediatypeDTO;
    }

    public Song getSongDTO() {
        return songDTO;
    }

    public void setSongDTO(Song songDTO) {
        this.songDTO = songDTO;
    }
}
