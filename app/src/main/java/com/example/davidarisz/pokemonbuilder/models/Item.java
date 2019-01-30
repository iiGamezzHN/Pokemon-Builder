/*
Author: David Arisz

This model is used by the GSON library to parse the Item api response
 */

package com.example.davidarisz.pokemonbuilder.models;

import java.util.ArrayList;


public class Item {
    private ArrayList<EffectEntries> effect_entries;
    private String name;
    private String sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/"+name+".png";


    public class EffectEntries {
        private String short_effect;


        // Getters and setters
        public String getShort_effect() {
            return short_effect;
        }


        public void setShort_effect(String short_effect) {
            this.short_effect = short_effect;
        }
    }


    // Getters and setters
    public ArrayList<EffectEntries> getEffect_entries() {
        return effect_entries;
    }


    public void setEffect_entries(ArrayList<EffectEntries> effect_entries) {
        this.effect_entries = effect_entries;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSprite() {
        return sprite;
    }


    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
