package com.example.davidarisz.pokemonbuilder.Classes;

public class MoveData {
    String name;
    int power, accuracy, pp;

    public MoveData(String name, int power, int accuracy, int pp) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }
}
