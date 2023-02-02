package com.example.myapplication;

public class AdminAlerts {
    private String alertType;
    private String alertLocation;
    private Boolean isRead;
    private String reco;

    public AdminAlerts(){

    }

    public AdminAlerts(String alertType, String alertLocation, Boolean isRead, String reco){
        this.alertType = alertType;
        this.alertLocation = alertLocation;
        this.isRead = isRead;
        this.reco = reco;
    }


    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertLocation() {
        return alertLocation;
    }

    public void setAlertLocation(String alertLocation) {
        this.alertLocation = alertLocation;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getReco() {
        return reco;
    }

    public void setReco(String reco) {
        this.reco = reco;
    }
}
