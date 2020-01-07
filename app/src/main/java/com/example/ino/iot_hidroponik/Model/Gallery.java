package com.example.ino.iot_hidroponik.Model;

public class Gallery {

    private String url, date,status;

    public Gallery(String url, String name, String status) {
        this.url = url;
        this.date = name;
        this.status = status;
    }

    public Gallery() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
