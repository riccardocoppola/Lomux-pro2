package com.example.stefano.lomux_pro.model;

/**
 * Created by s241972 on 06/11/2017.
 */

public class PinHasPintype {
    protected PinHasPintypePK pinHasPintypeDTOPK;
    private Short isprincipal;
    private Pinnable pinDTO;
    private Pintype pintypeDTO;

    public PinHasPintypePK getPinHasPintypeDTOPK() {
        return pinHasPintypeDTOPK;
    }

    public void setPinHasPintypeDTOPK(PinHasPintypePK pinHasPintypeDTOPK) {
        this.pinHasPintypeDTOPK = pinHasPintypeDTOPK;
    }

    public Short getIsprincipal() {
        return isprincipal;
    }

    public void setIsprincipal(Short isprincipal) {
        this.isprincipal = isprincipal;
    }

    public Pinnable getPinDTO() {
        return pinDTO;
    }

    public void setPinDTO(Pinnable pinDTO) {
        this.pinDTO = pinDTO;
    }

    public Pintype getPintypeDTO() {
        return pintypeDTO;
    }

    public void setPintypeDTO(Pintype pintypeDTO) {
        this.pintypeDTO = pintypeDTO;
    }
}
