package com.osama.answerwin.Models;

public class BooledModel {
    String userName;
    int date;

    public BooledModel(String userName, int date) {
        this.userName = userName;
        this.date = date;
    }

    public BooledModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
