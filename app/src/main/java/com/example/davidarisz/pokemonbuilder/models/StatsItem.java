/*
Author: David Arisz

This model is used by the GSON library to parse the Stats part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

public class StatsItem {
    private int base_stat;
    private int effort;
    private Stat stat;


    public class Stat {
        private String name;
        private String url;


        // Getters and setters
        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public String getUrl() {
            return url;
        }


        public void setUrl(String url) {
            this.url = url;
        }
    }


    // Getters and setters
    public int getBase_stat() {
        return base_stat;
    }


    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }


    public int getEffort() {
        return effort;
    }


    public void setEffort(int effort) {
        this.effort = effort;
    }


    public Stat getStat() {
        return stat;
    }


    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
