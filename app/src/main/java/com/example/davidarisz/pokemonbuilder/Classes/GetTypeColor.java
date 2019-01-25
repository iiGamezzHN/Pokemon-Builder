package com.example.davidarisz.pokemonbuilder.Classes;

public class GetTypeColor {

    public GetTypeColor() {
    }

    public String ReturnColor(String type) {
        String color = "";

        if(type.equals("Normal")) {
            color = "#a8a878";
        } else if(type.equals("Fire")) {
            color = "#f08030";
        } else if(type.equals("Water")) {
            color = "#6890f0";
        } else if(type.equals("Grass")) {
            color = "#78c850";
        } else if(type.equals("Electric")) {
            color = "#f8d030";
        } else if(type.equals("Ice")) {
            color = "#98d8d8";
        } else if(type.equals("Fighting")) {
            color = "#c03028";
        } else if(type.equals("Poison")) {
            color = "#a040a0";
        } else if(type.equals("Ground")) {
            color = "#e0c068";
        } else if(type.equals("Flying")) {
            color = "#a890f0";
        } else if(type.equals("Psychic")) {
            color = "#f85888";
        } else if(type.equals("Bug")) {
            color = "#a8b820";
        } else if(type.equals("Rock")) {
            color = "#b8a038";
        } else if(type.equals("Ghost")) {
            color = "#705898";
        } else if(type.equals("Dark")) {
            color = "#705848";
        } else if(type.equals("Dragon")) {
            color = "#7038f8";
        } else if(type.equals("Steel")) {
            color = "#b8b8d0";
        } else if(type.equals("Fairy")) {
            color = "#f0b6bc";
        }
        return color;
    }
}
