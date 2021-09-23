package com.osama.answerwin.Models;

public class WinnersModel {
    String name;
    String prize;

    public WinnersModel(String name, String prize) {
        this.name = name;
        this.prize = prize;
    }

    public WinnersModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
