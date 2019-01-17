package com.example.davidarisz.pokemonbuilder.Classes;

public class NatureData {
    String name, increased, decreased;

    public NatureData(String name, String increased, String decreased) {
        this.name = name;
        this.increased = increased;
        this.decreased = decreased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncreased() {
        return increased;
    }

    public void setIncreased(String increased) {
        this.increased = increased;
    }

    public String getDecreased() {
        return decreased;
    }

    public void setDecreased(String decreased) {
        this.decreased = decreased;
    }
}
