/*
Author: David Arisz

This model is used by the GSON library to parse the HeldItem part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

public class HeldItem {
    private Item item;


    public class Item {
        private String name;


        // Getters and setters
        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }
    }


    // Getters and setters
    public Item getItem() {
        return item;
    }


    public void setItem(Item item) {
        this.item = item;
    }
}
