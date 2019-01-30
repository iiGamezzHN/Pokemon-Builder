/*
Author: David Arisz

This Class contains the search model which is used in the search popup in AddActivity
 */

package com.example.davidarisz.pokemonbuilder.Classes;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchModel implements Searchable {
    private String mTitle;
    private ArrayList pokemonNames;

    public SearchModel(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public void setPokemonNames(ArrayList pokemonNames) {
        this.pokemonNames = pokemonNames;
    }
}
