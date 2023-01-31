package com.example.myapplication;

import java.util.PrimitiveIterator;
import java.util.SplittableRandom;

public class AlertClass {

    private String username;
    private String alertLocation;
    private String alertTime;
    private String alertType;
    private String alertDesc;

    public AlertClass(){

    }

    public AlertClass(String username, String alertLocation, String alertTime, String alertType, String alertDesc){
        this.username = username;
        this.alertLocation = alertLocation;
        this.alertTime = alertTime;
        this.alertType = alertType;
        this.alertDesc = alertDesc;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlertLocation() {
        return alertLocation;
    }

    public void setAlertLocation(String alertLocation) {
        this.alertLocation = alertLocation;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertDesc() {
        return alertDesc;
    }

    public void setAlertDesc(String alertDesc) {
        this.alertDesc = alertDesc;
    }
}
