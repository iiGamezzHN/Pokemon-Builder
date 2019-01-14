package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class AddActivity extends AppCompatActivity implements PokemonDataRequest.Callback, NatureNamesRequest.Callback,
        ItemNamesRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        Button button = findViewById(R.id.btn_add_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        tv = findViewById(R.id.tv_name);
        tv.setText("Adding: ");

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(AddActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
                        tv = findViewById(R.id.tv_name);
                        String adding = "Adding: " + name2;
                        tv.setText(adding);

                        makeRequest();
                    }
                }).show();
            }
        });
    }

    public void makeRequest () {
        PokemonDataRequest pokemonData = new PokemonDataRequest(this, name);
        pokemonData.getData(this);

        NatureNamesRequest natures = new NatureNamesRequest(this);
        natures.getNatureNames(this);

        ItemNamesRequest items = new ItemNamesRequest(this);
        items.getItemNames(this);
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
        for (MoveItem moveItem : pokemon.getMoves()) {
            String move = moveItem.getMove().getName();
            String move2 = move.substring(0,1).toUpperCase() + move.substring(1);
            moves.add(move2);
        }
        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            String ability = abilityItem.getAbility().getName();
            String ability2 = ability.substring(0,1).toUpperCase() + ability.substring(1);
            abilities.add(ability2);
        }

        Spinner ability = findViewById(R.id.spn_ability);
        Spinner move1 = findViewById(R.id.spn_move1);
        Spinner move2 = findViewById(R.id.spn_move2);
        Spinner move3 = findViewById(R.id.spn_move3);
        Spinner move4 = findViewById(R.id.spn_move4);
        url = pokemon.getSprites().getFront_default();

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

    public void gotNatureNames(ArrayList natures) {
        Spinner nature = findViewById(R.id.spn_nature);
        ArrayAdapter abilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, natures);
        abilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature.setAdapter(abilityAdapter);
    }

    public void gotItemNames(ArrayList items) {
        Spinner item = findViewById(R.id.spn_item);
        ArrayAdapter itemAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item.setAdapter(itemAdapter);
    }

    public void addPokemon(View view) {
        PokemonDatabase db = PokemonDatabase.getInstance(getApplicationContext());

        CheckBox chk_male = findViewById(R.id.chk_male);
        CheckBox chk_female = findViewById(R.id.chk_female);
        Spinner spn_item = findViewById(R.id.spn_item);
        Spinner spn_ability = findViewById(R.id.spn_ability);
        Spinner spn_move1 = findViewById(R.id.spn_move1);
        Spinner spn_move2 = findViewById(R.id.spn_move2);
        Spinner spn_move3 = findViewById(R.id.spn_move3);
        Spinner spn_move4 = findViewById(R.id.spn_move4);
        Spinner spn_nature = findViewById(R.id.spn_nature);
        EditText et_hp_iv = findViewById(R.id.et_hp_iv);
        EditText et_att_iv = findViewById(R.id.et_att_iv);
        EditText et_def_iv = findViewById(R.id.et_def_iv);
        EditText et_spa_iv = findViewById(R.id.et_spa_iv);
        EditText et_spd_iv = findViewById(R.id.et_spd_iv);
        EditText et_sp_iv = findViewById(R.id.et_sp_iv);
        EditText et_hp_ev = findViewById(R.id.et_hp_ev);
        EditText et_att_ev = findViewById(R.id.et_att_ev);
        EditText et_def_ev = findViewById(R.id.et_def_ev);
        EditText et_spa_ev = findViewById(R.id.et_spa_ev);
        EditText et_spd_ev = findViewById(R.id.et_spd_ev);
        EditText et_sp_ev = findViewById(R.id.et_sp_ev);

        if(chk_male.isChecked()) {
            String gender = "Male";
        } else if (chk_female.isChecked()){
            String gender = "Female";
        } else {
            String gender = "Genderless";
        }
        String item = spn_item.getSelectedItem().toString();
        String ability = spn_ability.getSelectedItem().toString();
        String move1 = spn_move1.getSelectedItem().toString();
        String move2 = spn_move2.getSelectedItem().toString();
        String move3 = spn_move3.getSelectedItem().toString();
        String move4 = spn_move4.getSelectedItem().toString();
        String nature = spn_nature.getSelectedItem().toString();
        int hp_iv = Integer.parseInt(et_hp_iv.getText().toString());
        int att_iv = Integer.parseInt(et_att_iv.getText().toString());
        int def_iv = Integer.parseInt(et_def_iv.getText().toString());
        int spa_iv = Integer.parseInt(et_spa_iv.getText().toString());
        int spd_iv = Integer.parseInt(et_spd_iv.getText().toString());
        int sp_iv = Integer.parseInt(et_sp_iv.getText().toString());
        int hp_ev = Integer.parseInt(et_hp_ev.getText().toString());
        int att_ev = Integer.parseInt(et_att_ev.getText().toString());
        int def_ev = Integer.parseInt(et_def_ev.getText().toString());
        int spa_ev = Integer.parseInt(et_spa_ev.getText().toString());
        int spd_ev = Integer.parseInt(et_spd_ev.getText().toString());
        int sp_ev = Integer.parseInt(et_sp_ev.getText().toString());

//        String item = "Master ball";
//        String nature = "Docile";

        SavedPokemon savedPokemon = new SavedPokemon(0,name,item,ability,move1,move2,move3,move4,nature,
                hp_iv,att_iv,def_iv,spa_iv,spd_iv,sp_iv,hp_ev,att_ev,def_ev,spa_ev,spd_ev,sp_ev,url);
        Long aLong = db.insert(savedPokemon);
        Log.d("longTag", String.valueOf(aLong));
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
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
