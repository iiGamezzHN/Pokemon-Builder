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

    public class IncreasedStat {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public DecreasedStat getDecreased_stat() {
        return decreased_stat;
    }

    public void setDecreased_stat(DecreasedStat decreased_stat) {
        this.decreased_stat = decreased_stat;
    }

    public IncreasedStat getIncreased_stat() {
        return increased_stat;
    }

    public void setIncreased_stat(IncreasedStat increased_stat) {
        this.increased_stat = increased_stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
