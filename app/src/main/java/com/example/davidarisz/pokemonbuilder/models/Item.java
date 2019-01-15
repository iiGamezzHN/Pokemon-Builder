package com.example.davidarisz.pokemonbuilder.models;

public class Item {
    private EffectEntries effect_entries;
    private String name;
    private String sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/"+name+".png";

    public class EffectEntries {
        private String short_effect;

        public String getShort_effect() {
            return short_effect;
        }

        public void setShort_effect(String short_effect) {
            this.short_effect = short_effect;
        }
    }

    public EffectEntries getEffect_entries() {
        return effect_entries;
    }

    public void setEffect_entries(EffectEntries effect_entries) {
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
