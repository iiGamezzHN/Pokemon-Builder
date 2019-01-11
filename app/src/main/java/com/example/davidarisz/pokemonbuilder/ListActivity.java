package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements PokemonRequest.Callback {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
    }

    public void toList(View view) {
        // Do nothing
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
    }
}
