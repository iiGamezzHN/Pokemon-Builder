package com.example.davidarisz.pokemonbuilder.models;


import java.util.ArrayList;

public class MoveItem {
    private Move move;
    private ArrayList<VersionGroupDetails> version_group_details;

    public class Move {
        private String name;
        private String url;

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
}
