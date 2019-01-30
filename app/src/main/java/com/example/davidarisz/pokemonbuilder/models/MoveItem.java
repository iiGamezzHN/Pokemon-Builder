/*
Author: David Arisz

This model is used by the GSON library to parse the Move part of the api response for a pokemon
This model gets called from the Pokemon model
 */

package com.example.davidarisz.pokemonbuilder.models;

import java.util.ArrayList;

public class MoveItem {
    private Move move;
    private ArrayList<VersionGroupDetails> version_group_details;


    public class Move {
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
    public Move getMove() {
        return move;
    }


    public void setMove(Move move) {
        this.move = move;
    }


    // VersionGroupDetails is split up in seperate object to improve readability
    public ArrayList<VersionGroupDetails> getVersion_group_details() {
        return version_group_details;
    }


    public void setVersion_group_details(ArrayList<VersionGroupDetails> version_group_details) {
        this.version_group_details = version_group_details;
    }
}
