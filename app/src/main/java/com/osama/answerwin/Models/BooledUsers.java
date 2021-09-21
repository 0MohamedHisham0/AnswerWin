package com.osama.answerwin.Models;

public class BooledUsers {
    String UserID;
    String date;

    public BooledUsers(String userID, String date) {
        UserID = userID;
        this.date = date;
    }

    public BooledUsers() {
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
