package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements PokemonRequest.Callback, Serializable {
    private ArrayList pokemonNames;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);
        tv = findViewById(R.id.pokemon_textView);
        tv.setText("Starting");
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        tv.setText("Done loading");
        pokemonNames = pokemon;
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("namesTag", pokemonNames);
        startActivity(intent);
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tabTag", "1");
        startActivity(intent);
    }
}
