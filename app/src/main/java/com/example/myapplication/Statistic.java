package com.example.myapplication;

public class Statistic {
    private String alertType;
    private String alertLocation;
    private String alertDate;


    public Statistic() {
    }

    public Statistic(String alertType, String alertLocation, String alertDate){
        this.alertType = alertType;
        this.alertLocation = alertLocation;
        this.alertDate = alertDate;
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
}
