package com.google.mooveaze.model;

public class Kiosk {
    private int id;
    private double distance;
    private String address;
    private String city;
    private String name;
    private String state;
    private String zip;
    private String vendor;

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public double getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getVendor() {
        return vendor;
    }
}
