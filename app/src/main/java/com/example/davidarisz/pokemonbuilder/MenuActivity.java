package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
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
        Toast.makeText(this, "method", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, pokemon);
//        ListView listView = findViewById(R.id.pokemon_listView);
//
//        listView.setAdapter(adapter);
//
//        // Sends you to the MenuActivity for the category that you clicked
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemValue = (String) parent.getItemAtPosition(position);
//                Intent intent = new Intent(MenuActivity.this, ListActivity.class);
//                intent.putExtra("pokemon_name", itemValue); // Serializes category name to retrieve it later
//                //startActivity(intent);
//            }
//        });
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("namesTag", pokemonNames);
        startActivity(intent);
    }
}
