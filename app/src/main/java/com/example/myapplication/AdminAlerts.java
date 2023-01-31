package com.example.myapplication;

public class AdminAlerts {
    private String alertType;
    private String alertLocation;
    private Boolean isRead;

    public AdminAlerts(){

    }

    public AdminAlerts(String alertType, String alertLocation, Boolean isRead){
        this.alertType = alertType;
        this.alertLocation = alertLocation;
        this.isRead = isRead;
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
}
