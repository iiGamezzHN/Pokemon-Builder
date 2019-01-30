/*
Author: David Arisz

This model is used by the GSON library to parse the Ability api response
 */

package com.example.davidarisz.pokemonbuilder.models;

import java.util.ArrayList;

public class Ability {
    private ArrayList<EffectEntries> effect_entries;
    private String name;


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
}
