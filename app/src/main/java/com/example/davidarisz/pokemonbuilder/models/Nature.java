package com.example.davidarisz.pokemonbuilder.models;

import java.util.ArrayList;

public class Nature {
    private ArrayList<DecreasedStat> decreased_stat;
    private ArrayList<IncreasedStat> increased_stat;
    private String name;

    public class DecreasedStat {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class IncreasedStat {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public ArrayList<DecreasedStat> getDecreased_stat() {
        return decreased_stat;
    }

    public void setDecreased_stat(ArrayList<DecreasedStat> decreased_stat) {
        this.decreased_stat = decreased_stat;
    }

    public ArrayList<IncreasedStat> getIncreased_stat() {
        return increased_stat;
    }

    public void setIncreased_stat(ArrayList<IncreasedStat> increased_stat) {
        this.increased_stat = increased_stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
