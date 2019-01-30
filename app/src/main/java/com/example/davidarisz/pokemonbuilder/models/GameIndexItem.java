/*
Author: David Arisz

This model is used by the GSON library to parse the GameIndex part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

public class GameIndexItem {
    private int game_index;
    private Version version;


    class Version {
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
    public int getGame_index() {
        return game_index;
    }


    public void setGame_index(int game_index) {
        this.game_index = game_index;
    }


    public Version getVersion() {
        return version;
    }


    public void setVersion(Version version) {
        this.version = version;
    }
}

