package com.osama.answerwin.Models;

public class UserModel {
    String userName;
    String phone;
    String email;
    String points;
    String jewels;
    String status;
    String prize;
    String role;

    public UserModel(String userName, String phone, String email, String points, String jewels, String status, String prize, String role) {
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.points = points;
        this.jewels = jewels;
        this.status = status;
        this.prize = prize;
        this.role = role;
    }

    public UserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getJewels() {
        return jewels;
    }

    public void setJewels(String jewels) {
        this.jewels = jewels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
