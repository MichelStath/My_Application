package com.example.myapplication;

public class ActiveAlert {
    private String alertType;
    private String alertLocation;
    private String alertDate;
    private String alertReco;


    public ActiveAlert(){

    }

    public ActiveAlert(String alertType, String alertLocation, String alertDate, String alertReco){
        this.alertType = alertType;
        this.alertLocation =alertLocation;
        this.alertDate = alertDate;
        this.alertReco = alertReco;
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

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getAlertReco() {
        return alertReco;
    }

    public void setAlertReco(String alertReco) {
        this.alertReco = alertReco;
    }
}
