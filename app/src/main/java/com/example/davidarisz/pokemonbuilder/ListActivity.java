package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements PokemonRequest.Callback {
    private ArrayList pokemonNames;
    private PokemonDatabase db;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);

        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter = new ListAdapter(this, db.selectAll());

        ListView listView = findViewById(R.id.lv_saved_pokemon);
        listView.setAdapter(adapter);

        Button button = findViewById(R.id.btn_list_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
    }

    public void toList(View view) {
        // Do nothing
    }

    public void toSearch(View view) {
        if(pokemonNames == null) {
            Toast.makeText(this, "List not loaded yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AddActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void toPokedex(View view) {
        if(pokemonNames == null) {
            Toast.makeText(this, "List not loaded yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PokedexActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        updateData();
//        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        String test = String.valueOf(db.selectAll().getCount());
        Log.d("databaseTag", test);
    }

    private void updateData() { // Update the data
        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter.swapCursor(db.selectAll());
    }
}
