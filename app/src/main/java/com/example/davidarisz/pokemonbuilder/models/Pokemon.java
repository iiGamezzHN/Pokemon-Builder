package com.example.davidarisz.pokemonbuilder.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pokemon {

    // Variables are initialized in order of api response
    // All objects in arraylists are seperated into diffent files to improve readability
    private ArrayList<AbilityItem> abilities;
    private int base_experience;
    private ArrayList<GameIndexItem> game_indices;
    private int height;
    private ArrayList<HeldItem> held_items;
    private int id;
    private boolean is_default;
    private String location_area_encounters;
    private ArrayList<MoveItem> moves;
    private String name;
    private int order;
    private Sprites sprites;
    private ArrayList<StatsItem> stats;
    private ArrayList<TypesItem> types;
    private int weight;


    // Getters and setters
    public ArrayList<AbilityItem> getAbilities() {
        return abilities;
    }


    public void setAbilities(ArrayList<AbilityItem> abilities) {
        this.abilities = abilities;
    }


    public int getBase_experience() {
        return base_experience;
    }


    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }


    public ArrayList<GameIndexItem> getGame_indices() {
        return game_indices;
    }


    public void setGame_indices(ArrayList<GameIndexItem> game_indices) {
        this.game_indices = game_indices;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public ArrayList<HeldItem> getHeld_items() {
        return held_items;
    }


    public void setHeld_items(ArrayList<HeldItem> held_items) {
        this.held_items = held_items;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public boolean is_default() {
        return is_default;
    }


    public void set_default(boolean is_default) {
        this.is_default = is_default;
    }


    public String getLocation_area_encounters() {
        return location_area_encounters;
    }


    public void setLocation_area_encounters(String location_area_encounters) {
        this.location_area_encounters = location_area_encounters;
    }


    public ArrayList<MoveItem> getMoves() {
        return moves;
    }


    public void setMoves(ArrayList<MoveItem> moves) {
        this.moves = moves;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getOrder() {
        return order;
    }


    public void setOrder(int order) {
        this.order = order;
    }


    public Sprites getSprites() {
        return sprites;
    }


    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }


    public ArrayList<StatsItem> getStats() {
        return stats;
    }


    public void setStats(ArrayList<StatsItem> stats) {
        this.stats = stats;
    }


    public ArrayList<TypesItem> getTypes() {
        return types;
    }


    public void setTypes(ArrayList<TypesItem> types) {
        this.types = types;
    }


    public int getWeight() {
        return weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }
}
