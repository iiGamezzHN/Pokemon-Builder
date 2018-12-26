package com.example.davidarisz.pokemonbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements PokemonRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);
    }

    public void gotPokemon(ArrayList<String> pokemon) {
        // Bla
    }
}
