package com.example.weatherapp;

public class Weather {
    private String day;
    private String status;
    private String imgIcon;
    private String minTemp;
    private String maxTemp;

    public Weather(String day, String status, String imgIcon, String minTemp, String maxTemp) {
        this.day = day;
        this.status = status;
        this.imgIcon = imgIcon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(String imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }
}
