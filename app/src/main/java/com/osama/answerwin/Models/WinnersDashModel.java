package com.osama.answerwin.Models;

public class WinnersDashModel {
    String name;
    String phone;
    String jewels;
    String points;

    public WinnersDashModel(String name, String phone, String jewels, String points) {
        this.name = name;
        this.phone = phone;
        this.jewels = jewels;
        this.points = points;
    }

    public WinnersDashModel() {
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

    public String getJewels() {
        return jewels;
    }

    public void setJewels(String jewels) {
        this.jewels = jewels;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
