package com.example.davidarisz.pokemonbuilder.models;

public class HeldItem {
    private Item item;

    public class Item {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
