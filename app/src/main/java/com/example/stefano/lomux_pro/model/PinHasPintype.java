package com.example.stefano.lomux_pro.model;

/**
 * Created by s241972 on 06/11/2017.
 */

public class PinHasPintype {
    protected PinHasPintypePK pinHasPintypeDTOPK;
    private int isprincipal;
    private Pin pinDTO;
    private Pintype pintypeDTO;

    public PinHasPintypePK getPinHasPintypeDTOPK() {
        return pinHasPintypeDTOPK;
    }

    public void setPinHasPintypeDTOPK(PinHasPintypePK pinHasPintypeDTOPK) {
        this.pinHasPintypeDTOPK = pinHasPintypeDTOPK;
    }

    public int getIsprincipal() {
        return isprincipal;
    }

    public void setIsprincipal(int isprincipal) {
        this.isprincipal = isprincipal;
    }

    public Pin getPinDTO() {
        return pinDTO;
    }

    public void setPinDTO(Pin pinDTO) {
        this.pinDTO = pinDTO;
    }

    public Pintype getPintypeDTO() {
        return pintypeDTO;
    }

    public void setPintypeDTO(Pintype pintypeDTO) {
        this.pintypeDTO = pintypeDTO;
    }
}
