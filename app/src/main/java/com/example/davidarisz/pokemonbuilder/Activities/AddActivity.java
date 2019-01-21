package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Adapters.AbilityAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.ItemAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.ItemArrayAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.ItemArrayAdapter2;
import com.example.davidarisz.pokemonbuilder.Adapters.NatureAdapter;
import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.Classes.SavedPokemon;
import com.example.davidarisz.pokemonbuilder.Classes.SearchModel;
import com.example.davidarisz.pokemonbuilder.Databases.ItemDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.NatureDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.PokemonDatabase;
import com.example.davidarisz.pokemonbuilder.R;
import com.example.davidarisz.pokemonbuilder.Requests.AbilityDataRequest;
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

public class AddActivity extends AppCompatActivity implements PokemonDataRequest.Callback, AbilityDataRequest.Callback {
    private ArrayList pokemonNames;
    private ArrayList<AbilityData> abilities = new ArrayList<>();
    private TextView tv;
    private String name, url, url_shiny;
    private int nr_abilities, nr_loop;

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
        ArrayList<ItemData> items = new ArrayList<>();
        // Pokemon data
        PokemonDataRequest pokemonData = new PokemonDataRequest(this, name);
        pokemonData.getPokemonData(this);

        // Item Data
        ItemDatabase itemDb = ItemDatabase.getInstance(getApplicationContext());
        Cursor cursor = itemDb.selectAll();
        while (cursor.moveToNext()) {
            String item_name = cursor.getString(cursor.getColumnIndex("name"));
            String item_effect = cursor.getString(cursor.getColumnIndex("effect"));
            String item_sprite = cursor.getString(cursor.getColumnIndex("sprite"));
            ItemData itemData = new ItemData(item_name, item_effect, item_sprite);
//            Log.d("cursorTag", item_name+item_effect+sprite);
            items.add(itemData);
        }

        ItemData test = items.get(1);
        Log.d("cursorTag", test.getName()+test.getEffect()+test.getSprite());

        Log.d("adapterTag", ""+items.size());
        final AutoCompleteTextView auto_items = findViewById(R.id.auto_items);
        ItemArrayAdapter2 itemAdapter = new ItemArrayAdapter2(this, items);
//        ItemAdapter itemAdapter = new ItemAdapter(this, itemDb.selectAll());
        auto_items.setAdapter(itemAdapter);
        auto_items.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        auto_items.showDropDown();
        auto_items.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_items.showDropDown();
                return false;
            }
        });

        // Nature data
        NatureDatabase natureDb = NatureDatabase.getInstance(getApplicationContext());
        Spinner natures = findViewById(R.id.spn_nature);
        NatureAdapter natureAdapter = new NatureAdapter(this, natureDb.selectAll());
        natures.setAdapter(natureAdapter);

//        Cursor cursor = itemDb.selectAll();
//        while (cursor.moveToNext()) {
//            String test = cursor.getString(cursor.getColumnIndex("sprite"));
//            Log.d("natureDbTag", test);
//        }
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
        nr_abilities = pokemon.getAbilities().size();
        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            String ability = abilityItem.getAbility().getName();
            Boolean hidden = abilityItem.is_hidden();

            AbilityDataRequest abilityDataRequest = new AbilityDataRequest(this, ability, hidden);
            abilityDataRequest.getAbilityData(this);
        }

        Log.d("loopTag", "After loop");

        Spinner move1 = findViewById(R.id.spn_move1);
        Spinner move2 = findViewById(R.id.spn_move2);
        Spinner move3 = findViewById(R.id.spn_move3);
        Spinner move4 = findViewById(R.id.spn_move4);
        url = pokemon.getSprites().getFront_default();
        url_shiny = pokemon.getSprites().getFront_shiny();

        // Creating the ArrayAdapter instance having the country list
        ArrayAdapter movesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, moves);
        movesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter abilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, abilities);
//        abilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setting the ArrayAdapter data on the spinners
//        ability.setAdapter(abilityAdapter);
        move1.setAdapter(movesAdapter);
        move2.setAdapter(movesAdapter);
        move3.setAdapter(movesAdapter);
        move4.setAdapter(movesAdapter);
    }

    public void gotAbilityData(AbilityData abilityData) {
        nr_loop += 1;
        Log.d("loopTag", "During loop");
        abilities.add(abilityData);
        if(nr_loop == nr_abilities) {
            Log.d("loopTag", ""+abilities.size());
            Spinner ability = findViewById(R.id.spn_ability);
            AbilityAdapter abilityAdapter = new AbilityAdapter(this, R.layout.spinner_ability_row, R.id.tv_name_ability, abilities);
            ability.setAdapter(abilityAdapter);
        }
    }

    // Add complete pokemon to database
    public void addPokemon(View view) {
        // Get database
        PokemonDatabase db = PokemonDatabase.getInstance(getApplicationContext());

        // Get elements for layout
        CheckBox chk_male = findViewById(R.id.chk_male);
        CheckBox chk_female = findViewById(R.id.chk_female);
//        Spinner spn_item = findViewById(R.id.spn_item);
        AutoCompleteTextView auto_item = findViewById(R.id.auto_items);
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
        String item = auto_item.toString();
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
        db.insert(savedPokemon);
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
