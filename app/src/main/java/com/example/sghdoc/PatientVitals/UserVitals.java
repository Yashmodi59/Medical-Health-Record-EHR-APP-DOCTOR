package com.example.sghdoc.PatientVitals;

public class UserVitals {
    private String bloodpressure,bloodtemperature,heartrate,respirationrate,date;

    public UserVitals() {
    }

    public UserVitals(String bloodpressure, String bloodtemperature, String heartrate, String respirationrate, String date) {
        this.bloodpressure = bloodpressure;
        this.bloodtemperature = bloodtemperature;
        this.heartrate = heartrate;
        this.respirationrate = respirationrate;
        this.date = date;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getBloodtemperature() {
        return bloodtemperature;
    }

    public void setBloodtemperature(String bloodtemperature) {
        this.bloodtemperature = bloodtemperature;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getRespirationrate() {
        return respirationrate;
    }

    public void setRespirationrate(String respirationrate) {
        this.respirationrate = respirationrate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
