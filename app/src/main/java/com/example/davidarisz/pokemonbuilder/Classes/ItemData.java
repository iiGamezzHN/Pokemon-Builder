/*
Author: David Arisz

This Class contains information on the item data
Name is the name of the item
Effect is the effect that the item has
Sprite is the url of the item image
 */

package com.example.davidarisz.pokemonbuilder.Classes;

public class ItemData {
    String name, effect, sprite;

    public ItemData(String name, String effect, String sprite) {
        this.name = name;
        this.effect = effect;
        this.sprite = sprite;
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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
