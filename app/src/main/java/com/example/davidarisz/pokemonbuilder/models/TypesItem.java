/*
Author: David Arisz

This model is used by the GSON library to parse the Types part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

public class TypesItem {
    private int slot;
    private Type type;


    public class Type {
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
    public int getSlot() {
        return slot;
    }


    public void setSlot(int slot) {
        this.slot = slot;
    }


    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        this.type = type;
    }
}
