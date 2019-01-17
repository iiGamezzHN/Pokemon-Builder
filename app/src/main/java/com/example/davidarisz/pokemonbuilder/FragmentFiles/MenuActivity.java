package com.example.davidarisz.pokemonbuilder.FragmentFiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Activities.AddActivity;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonNamesRequest;
import com.example.davidarisz.pokemonbuilder.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements PokemonNamesRequest.Callback, Serializable {
    private ArrayList pokemonNames;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        PokemonNamesRequest request = new PokemonNamesRequest(this);
        request.getPokemon(this);
//        tv = findViewById(R.id.pokemon_textView);
//        tv.setText("Starting");
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
//        tv.setText("Done loading");
        pokemonNames = pokemon;
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("namesTag", pokemonNames);
        startActivity(intent);
    }

    public void toMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tabTag", "1");
        startActivity(intent);
    }
}
