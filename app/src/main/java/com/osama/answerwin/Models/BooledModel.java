package com.osama.answerwin.Models;

public class BooledModel {
    String UserID;
    String userName;
    int date;

    public BooledModel(String userID, String userName, int date) {
        UserID = userID;
        this.userName = userName;
        this.date = date;
    }


    public BooledModel() {
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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
