package com.authxero.muselablocal.payload.response;

public class CheckResponse {
    private String museLab;

    public CheckResponse(String museLab) {
        this.museLab = museLab;
    }

    public String getMuseLab() {
        return museLab;
    }

    public void setMuseLab(String museLab) {
        this.museLab = museLab;
    }
}
