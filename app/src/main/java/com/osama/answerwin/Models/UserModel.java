package com.osama.answerwin.Models;

public class UserModel {
    String name;
    String phone;
    String email;
    int points;
    int jewels;
    String status;
    String prize;
    String role;

    public UserModel(String name, String phone, String email, int points, int jewels, String status, String prize, String role) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getJewels() {
        return jewels;
    }

    public void setJewels(int jewels) {
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
