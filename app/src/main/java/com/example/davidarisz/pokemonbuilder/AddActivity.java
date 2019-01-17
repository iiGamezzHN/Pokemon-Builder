package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.SavedPokemon;
import com.example.davidarisz.pokemonbuilder.Classes.SearchModel;
import com.example.davidarisz.pokemonbuilder.Requests.ItemDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.ItemNamesRequest;
import com.example.davidarisz.pokemonbuilder.Requests.NatureDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.NatureNamesRequest;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonDataRequest;
import com.example.davidarisz.pokemonbuilder.models.AbilityItem;
import com.example.davidarisz.pokemonbuilder.models.Item;
import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class AddActivity extends AppCompatActivity implements PokemonDataRequest.Callback, NatureNamesRequest.Callback,
        ItemNamesRequest.Callback, ItemDataRequest.Callback, NatureDataRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;
    private String url;
    private String url_shiny;
//    private EditText et_hp_iv;
//    private EditText et_att_iv;
//    private EditText et_def_iv;
//    private EditText et_spa_iv;
//    private EditText et_spd_iv;
//    private EditText et_sp_iv;
//    private EditText et_hp_ev;
//    private EditText et_att_ev;
//    private EditText et_def_ev;
//    private EditText et_spa_ev;
//    private EditText et_spd_ev;
//    private EditText et_sp_ev;
    private ArrayList<ArrayList<String>> natureData;

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

    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i).toString()));
        }

        return items;
    }

    // Requests for individual pokemon data, nature names and item names
    public void makeRequest () {
        // Pokemon data
        PokemonDataRequest pokemonData = new PokemonDataRequest(this, name);
        pokemonData.getPokemonData(this);

        // Nature names
        NatureNamesRequest natures = new NatureNamesRequest(this);
        natures.getNatureNames(this);

        // Item names
        ItemNamesRequest items = new ItemNamesRequest(this);
        items.getItemNames(this);
    }

    // Pokemon data
    public void gotPokemonData (Pokemon pokemon) {
        ArrayList<String> moves = new ArrayList<String>();
        ArrayList<String> abilities = new ArrayList<String>();
        // Get all moves for pokemon
        for (MoveItem moveItem : pokemon.getMoves()) {
            String move = moveItem.getMove().getName();
            String move2 = move.substring(0,1).toUpperCase() + move.substring(1);
            moves.add(move2);
        }
        // Get all abilities for pokemon
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
        url_shiny = pokemon.getSprites().getFront_shiny();

        // Creating the ArrayAdapter instance having the country list
        ArrayAdapter movesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, moves);
        movesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter abilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, abilities);
        abilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setting the ArrayAdapter data on the spinners
        ability.setAdapter(abilityAdapter);
        move1.setAdapter(movesAdapter);
        move2.setAdapter(movesAdapter);
        move3.setAdapter(movesAdapter);
        move4.setAdapter(movesAdapter);
    }

    // Nature names
    public void gotNatureNames(ArrayList natures) {
        // Set names to spinner
        Spinner nature = findViewById(R.id.spn_nature);
        ArrayAdapter abilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, natures);
        abilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature.setAdapter(abilityAdapter);

        // Test autocomplete EditText for nature names
//        test(natures);

        // Request for nature data
        for(int i=0;i < natures.size(); i++) {
            String natureName = natures.get(i).toString();
            NatureDataRequest natureDataRequest = new NatureDataRequest(getApplicationContext(), natureName);
            natureDataRequest.getNatureData(this);
        }

//        adaptNatures();
    }

    // Nature data
    public void gotNatureData(ArrayList data) {
//        natureData.add(data);
    }

//    public void adaptNatures() {
//        Spinner nature = findViewById(R.id.spn_nature);
//        NatureAdapter natureAdapter = new NatureAdapter(this, natureData);
//
//        nature.setAdapter(natureAdapter);
//    }

    // Autocomplete EditText for nature names
//    public void test(ArrayList natures) {
//        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.auto_nature);
//        ArrayAdapter itemAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, natures);
//        autoCompleteTextView.setAdapter(itemAdapter);
//
//        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                autoCompleteTextView.showDropDown();
//                return false;
//            }
//        });
//    }

    // Item names
    public void gotItemNames(ArrayList items) {
        // Set names to spinner
        Spinner item = findViewById(R.id.spn_item);
        ArrayAdapter itemAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item.setAdapter(itemAdapter);

        // Request for item data
        ItemDataRequest itemDataRequest = new ItemDataRequest(getApplicationContext(), "chesto-berry");
        itemDataRequest.getItemData(this);
    }

    // Item data
    public void gotItemData(Item item) {
        // asdf
    }

    // Add complete pokemon to database
    public void addPokemon(View view) {
        // Get database
        PokemonDatabase db = PokemonDatabase.getInstance(getApplicationContext());

        // Get elements for layout
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
        String gender;

        // Check checkboxes
        if(chk_male.isChecked()) {
            gender = "Male";
        } else if (chk_female.isChecked()){
            gender = "Female";
        } else {
            gender = "Genderless";
        }

        // Set filled in elements
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

        // Save pokemon to database
        SavedPokemon savedPokemon = new SavedPokemon(0,name,item,ability,move1,move2,move3,move4,nature,
                hp_iv,att_iv,def_iv,spa_iv,spd_iv,sp_iv,hp_ev,att_ev,def_ev,spa_ev,spd_ev,sp_ev,url, url_shiny,gender);
        Long aLong = db.insert(savedPokemon);
        Log.d("longTag", String.valueOf(aLong));
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        // Get rid of opening animation of new activity
        overridePendingTransition(0,0);
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void toAdd(View view) {
        // Do nothing
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void resetIV(View view) {
        EditText et_hp_iv = findViewById(R.id.et_hp_iv);
        EditText et_att_iv = findViewById(R.id.et_att_iv);
        EditText et_def_iv = findViewById(R.id.et_def_iv);
        EditText et_spa_iv = findViewById(R.id.et_spa_iv);
        EditText et_spd_iv = findViewById(R.id.et_spd_iv);
        EditText et_sp_iv = findViewById(R.id.et_sp_iv);

        et_hp_iv.setText(null);
        et_att_iv.setText(null);
        et_def_iv.setText(null);
        et_spa_iv.setText(null);
        et_spd_iv.setText(null);
        et_sp_iv.setText(null);
    }

    public void resetEV(View view) {
        EditText et_hp_ev = findViewById(R.id.et_hp_ev);
        EditText et_att_ev = findViewById(R.id.et_att_ev);
        EditText et_def_ev = findViewById(R.id.et_def_ev);
        EditText et_spa_ev = findViewById(R.id.et_spa_ev);
        EditText et_spd_ev = findViewById(R.id.et_spd_ev);
        EditText et_sp_ev = findViewById(R.id.et_sp_ev);

        et_hp_ev.setText(null);
        et_att_ev.setText(null);
        et_def_ev.setText(null);
        et_spa_ev.setText(null);
        et_spd_ev.setText(null);
        et_sp_ev.setText(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
