package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.models.AbilityItem;
import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class AddActivity extends AppCompatActivity implements DataRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        Button button = findViewById(R.id.search_tag);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        tv = findViewById(R.id.tvSelected);
        tv.setText("Adding: ");

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(AddActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
                        tv = findViewById(R.id.tvSelected);
                        String adding = "Adding: " + name2;
                        tv.setText(adding);

                        makeRequest();
                    }
                }).show();
            }
        });
    }

    public void makeRequest () {
        DataRequest request = new DataRequest(AddActivity.this, name);
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
        ArrayList<String> abilities = new ArrayList<String>();
        ArrayList<String> natures = new ArrayList<String>();
        for (MoveItem moveItem : pokemon.getMoves()) {
            moves.add(moveItem.getMove().getName());
        }
        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            abilities.add(abilityItem.getAbility().getName());
        }

        Spinner ability = findViewById(R.id.spn_ability);
        Spinner move1 = findViewById(R.id.move1);
        Spinner move2 = findViewById(R.id.move2);
        Spinner move3 = findViewById(R.id.move3);
        Spinner move4 = findViewById(R.id.move4);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter movesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, moves);
        movesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter abilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, abilities);
        abilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the spinners
        ability.setAdapter(abilityAdapter);
        move1.setAdapter(movesAdapter);
        move2.setAdapter(movesAdapter);
        move3.setAdapter(movesAdapter);
        move4.setAdapter(movesAdapter);
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void toSearch(View view) {
        // Do nothing
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
