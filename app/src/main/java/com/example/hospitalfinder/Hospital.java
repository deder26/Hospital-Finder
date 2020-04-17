package com.example.hospitalfinder;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.sqrt;

public class Hospital implements Serializable {

    private String name,phone,alert;
    private double Latitude,Longitude;


    public Hospital() {
    }

    public Hospital(String name, String phone, String alert, double latitude, double longitude) {
        this.name = name;

        this.phone = phone;
        this.alert = alert;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

}
