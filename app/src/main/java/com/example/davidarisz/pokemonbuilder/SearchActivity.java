package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchActivity extends AppCompatActivity implements PokemonRequest.Callback, DataRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);

        tv = findViewById(R.id.tvSelected);
        tv.setText("Adding: ");
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(SearchActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        tv = findViewById(R.id.tvSelected);
                        String adding = "Adding: " + name;
                        tv.setText(adding);

                        makeRequest();
                    }
                }).show();
            }
        });
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
    }

    public void makeRequest () {
        DataRequest request = new DataRequest(SearchActivity.this);
        request.getData(this);
    }

    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i).toString()));
        }

        return items;
    }

    public void gotData (Pokemon pokemon) {
        ArrayList<String> moves = new ArrayList<String>();
        for (MoveItem moveItem : pokemon.getMoves()) {
            moves.add(moveItem.getMove().getName());
        }

        Spinner move1 = findViewById(R.id.move1);
        Spinner move2 = findViewById(R.id.move2);
        Spinner move3 = findViewById(R.id.move3);
        Spinner move4 = findViewById(R.id.move4);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, moves);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        move1.setAdapter(arrayAdapter);
        move2.setAdapter(arrayAdapter);
        move3.setAdapter(arrayAdapter);
        move4.setAdapter(arrayAdapter);
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void toSearch(View view) {
        // Do nothing
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        startActivity(intent);
    }
}
