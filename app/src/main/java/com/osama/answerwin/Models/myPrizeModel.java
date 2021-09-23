package com.osama.answerwin.Models;

public class myPrizeModel {
    String prize;
    String date;

    public myPrizeModel(String prize, String date) {
        this.prize = prize;
        this.date = date;
    }

    public myPrizeModel() {
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
