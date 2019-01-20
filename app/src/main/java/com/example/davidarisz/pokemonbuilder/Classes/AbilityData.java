package com.example.davidarisz.pokemonbuilder.Classes;

public class AbilityData {
    private String name, effect;
    private boolean hidden;

    public AbilityData(String name, String effect, boolean hidden) {
        this.name = name;
        this.effect = effect;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
