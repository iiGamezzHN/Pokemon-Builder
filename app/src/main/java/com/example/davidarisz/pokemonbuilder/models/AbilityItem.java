/*
Author: David Arisz

This model is used by the GSON library to parse the Ability part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

public class AbilityItem {
    private Ability ability;
    private boolean is_hidden;
    private int slot;


    public class Ability {
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
    public Ability getAbility() {
        return ability;
    }


    public void setAbility(Ability ability) {
        this.ability = ability;
    }


    public boolean is_hidden() {
        return is_hidden;
    }


    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }


    public int getSlot() {
        return slot;
    }


    public void setSlot(int slot) {
        this.slot = slot;
    }
}

