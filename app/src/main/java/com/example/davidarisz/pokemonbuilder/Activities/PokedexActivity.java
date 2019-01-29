package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.davidarisz.pokemonbuilder.Adapters.PokedexAdapter;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;

public class PokedexActivity extends AppCompatActivity {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        // Set 'selected' color to current 'tab'
        Button button = findViewById(R.id.btn_pokedex_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        PokedexAdapter adapter = new PokedexAdapter(this, R.layout.pokedex_items, pokemonNames);

        ListView listView = findViewById(R.id.lv_pokedex);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(selectPokemon);
    }


    // Sends you to the DetailActivity for the clicked dish
    private AdapterView.OnItemClickListener selectPokemon = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = String.valueOf(pokemonNames.get(position));

            Intent intent = new Intent(PokedexActivity.this, PokedexDetailActivity.class);
            intent.putExtra("nameTag", name);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
        }
    };


    // Go to list activity
    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);

        // Get rid of opening animation of new activity
        overridePendingTransition(0,0);
    }


    // Go to add activity
    public void toAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);

        overridePendingTransition(0,0);
    }


    // Go to pokedex activity
    public void toPokedex(View view) {
        // Already there
    }

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0, 0);
    }
}
