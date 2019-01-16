package com.example.davidarisz.pokemonbuilder.models;

public class Nature {
    private DecreasedStat decreased_stat;
    private IncreasedStat increased_stat;
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

    public void checkStat(String name) {

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

    public DecreasedStat getDecreasedStat() {
        return decreased_stat;
    }

    public void setDecreasedStat(DecreasedStat decreased_stat) {
        this.decreased_stat = decreased_stat;
    }

    public IncreasedStat getIncreasedStat() {
        return increased_stat;
    }

    public void setIncreasedStat(IncreasedStat increased_stat) {
        this.increased_stat = increased_stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
