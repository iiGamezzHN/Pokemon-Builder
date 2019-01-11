package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PokedexActivity extends AppCompatActivity {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        PokedexAdapter adapter = new PokedexAdapter(this, R.layout.pokedex_items, pokemonNames);

        ListView listView = findViewById(R.id.lv_pokedex);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            // Sends you to the DetailActivity for the clicked dish
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MenuActivity.this, DetailActivity.class);
//                intent.putExtra("menu_item", menu_items.get(position));
//                startActivity(intent);
//            }
//        });
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
    }

    public void toPokedex(View view) {
        // Do nothing
    }
}
