/*
Author: David Arisz

This Class contains information on the move data
Name is the name of the move
Power is a number that shows how much damage the move does
Accuracy is a number that shows how often the attack hits
PP shows how often a move can be used
Category shows whether a move is physical, special or status
Effect shows what the move does
Type shows what type the move is (Fire, Water, etc)
 */

package com.example.davidarisz.pokemonbuilder.Classes;

public class MoveData {
    String name, category, effect, type;
    int power, accuracy, pp;

    public MoveData(String name, int power, int accuracy, int pp, String category, String effect, String type) {
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.category = category;
        this.effect = effect;
        this.type = type;
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

    public String getCategory() {
        return category;
    }

    public void setCategorie(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
