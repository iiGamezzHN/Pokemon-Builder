package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Adapters.AbilityAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.ItemArrayAdapter2;
import com.example.davidarisz.pokemonbuilder.Adapters.MoveAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.NatureAdapter;
import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.Classes.SavedPokemon;
import com.example.davidarisz.pokemonbuilder.Classes.SearchModel;
import com.example.davidarisz.pokemonbuilder.Databases.ItemDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.MoveDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.NatureDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.PokemonDatabase;
import com.example.davidarisz.pokemonbuilder.R;
import com.example.davidarisz.pokemonbuilder.Requests.AbilityDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonDataRequest;
import com.example.davidarisz.pokemonbuilder.models.AbilityItem;
import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.example.davidarisz.pokemonbuilder.models.TypesItem;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class AddActivity extends AppCompatActivity implements PokemonDataRequest.Callback, AbilityDataRequest.Callback {
    private ArrayList pokemonNames;
    private ArrayList<AbilityData> abilities = new ArrayList<>();
    private TextView tv_name;
    private String name, url, url_shiny;
    private int nr_abilities, nr_loop;
    private String item, ability, move1, move2, move3, move4, nature, gender, type1, type2 = "";
    private int hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev, def_ev, spa_ev, spd_ev, sp_ev;
    private ItemArrayAdapter2 itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");
        hp_iv= att_iv= def_iv= spa_iv= spd_iv= sp_iv= hp_ev= att_ev= def_ev= spa_ev= spd_ev= sp_ev-1;
        if(getIntent().getStringExtra("addName") != null) {
            name = getIntent().getStringExtra("addName");
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
            tv_name = findViewById(R.id.tv_name);
            String adding = "Adding: " + name2;
            tv_name.setText(adding);

            // Not doing this causes error: Unable to add window -- token null is not valid; is your activity running?
            // On auto_items.showDropDown()
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    makeRequest();
                }
            }, 1000);
        } else {
            tv_name = findViewById(R.id.tv_name);
            tv_name.setText("Adding: ");
        }

        Button button = findViewById(R.id.btn_add_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(AddActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
                        tv_name = findViewById(R.id.tv_name);
                        String adding = "Adding: " + name2;
                        tv_name.setText(adding);

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
        itemAdapter = new ItemArrayAdapter2(AddActivity.this, items);
        auto_items.setAdapter(itemAdapter);
        auto_items.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        auto_items.showDropDown();
        auto_items.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_items.showDropDown();
                return false;
            }
        });
        auto_items.setOnItemClickListener(new AdapterView.OnItemClickListener() { // TODO, set clicklisteners for items and moves to get l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ItemData test = itemAdapter.getItem(position);
                Log.d("itemTag", "name: "+test.getName());
                Log.d("itemTag", "effect: "+test.getEffect());
            }
        });

        // Nature data
        NatureDatabase natureDb = NatureDatabase.getInstance(getApplicationContext());
        Spinner natures = findViewById(R.id.spn_nature);
        NatureAdapter natureAdapter = new NatureAdapter(this, natureDb.selectAll());
        natures.setAdapter(natureAdapter);
    }

    // Pokemon data
    public void gotPokemonData (Pokemon pokemon) {
        ArrayList<MoveData> moves = new ArrayList<>();
        ArrayList<String> abilities = new ArrayList<String>();
        MoveDatabase moveDb = MoveDatabase.getInstance(getApplicationContext());
        // Get all moves for pokemon
        for (MoveItem moveItem : pokemon.getMoves()) {
            String move = moveItem.getMove().getName();
            String move2 = move.substring(0,1).toUpperCase() + move.substring(1);

            Cursor cursor = moveDb.selectMove(move);
            while (cursor.moveToNext()) {
                int power = cursor.getInt(cursor.getColumnIndex("power"));
                int accuracy = cursor.getInt(cursor.getColumnIndex("accuracy"));
                int pp = cursor.getInt(cursor.getColumnIndex("pp"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String effect = cursor.getString(cursor.getColumnIndex("effect"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
//                Log.d("moveTag", move+", "+power+", "+accuracy+", "+pp);
                MoveData moveData = new MoveData(move2,power,accuracy,pp,category,effect,type);
                moves.add(moveData);
            }
        }

        Log.d("sizeTag", "nr of moves: "+moves.size());

        MoveAdapter moveAdapter = new MoveAdapter(AddActivity.this, moves);
        final AutoCompleteTextView auto_moves1 = findViewById(R.id.auto_move1);
        final AutoCompleteTextView auto_moves2 = findViewById(R.id.auto_move2);
        final AutoCompleteTextView auto_moves3 = findViewById(R.id.auto_move3);
        final AutoCompleteTextView auto_moves4 = findViewById(R.id.auto_move4);

        auto_moves1.setAdapter(moveAdapter);
        auto_moves2.setAdapter(moveAdapter);
        auto_moves3.setAdapter(moveAdapter);
        auto_moves4.setAdapter(moveAdapter);

        auto_moves1.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        auto_moves2.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        auto_moves3.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        auto_moves4.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        auto_moves1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_moves1.showDropDown();
                return false;
            }
        });
        auto_moves2.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_moves2.showDropDown();
                return false;
            }
        });
        auto_moves3.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_moves3.showDropDown();
                return false;
            }
        });
        auto_moves4.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                auto_moves4.showDropDown();
                return false;
            }
        });

        // Get all abilities for pokemon
        nr_abilities = pokemon.getAbilities().size();
        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            String ability = abilityItem.getAbility().getName();
            Boolean hidden = abilityItem.is_hidden();

            AbilityDataRequest abilityDataRequest = new AbilityDataRequest(this, ability, hidden);
            abilityDataRequest.getAbilityData(this);
        }

        url = pokemon.getSprites().getFront_default();
        url_shiny = pokemon.getSprites().getFront_shiny();

        //Get all types for pokemon
        for(TypesItem typesItem : pokemon.getTypes()) {
            if(typesItem.getSlot() == 1) {
                type1 = typesItem.getType().getName();
            } else if (typesItem.getSlot() == 2) {
                type2 = typesItem.getType().getName();
            }
        }
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

    public boolean isAnyStringNullOrEmpty(String... strings) {
        for (String s : strings)
            if (s == null || s.isEmpty())
                return true;
        return false;
    }

    public boolean isAnyIntNullOrEmpty(int... ints) {
        for (int i : ints)
            if (i == -1 || i >= 32)
                return true;
        return false;
    }

    public void checkInputs(View view) {
        // Get elements for layout
        CheckBox chk_male = findViewById(R.id.chk_male);
        CheckBox chk_female = findViewById(R.id.chk_female);
        CheckBox chk_genderless = findViewById(R.id.chk_genderless);
//        Spinner spn_item = findViewById(R.id.spn_item);
        AutoCompleteTextView auto_item = findViewById(R.id.auto_items);
        Spinner spn_ability = findViewById(R.id.spn_ability);
        AutoCompleteTextView auto_move1 = findViewById(R.id.auto_move1);
        AutoCompleteTextView auto_move2 = findViewById(R.id.auto_move2);
        AutoCompleteTextView auto_move3 = findViewById(R.id.auto_move3);
        AutoCompleteTextView auto_move4 = findViewById(R.id.auto_move4);
        Spinner spn_nature = findViewById(R.id.spn_nature);
        EditText et_hp_iv = findViewById(R.id.et_hp_iv); // TODO, maybe show base stats
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

        // Check checkboxes
        if (chk_male.isChecked()) {
            gender = "Male";
        } else if (chk_female.isChecked()) {
            gender = "Female";
        } else if (chk_genderless.isChecked()) {
            gender = "Genderless";
        } else {
            gender = "";
        }

        // Set filled in elements
        item = auto_item.toString();
        ability = spn_ability.getSelectedItem().toString();
        move1 = auto_move1.toString();  // TODO, fix this so i can add pokemon again
        move2 = auto_move2.toString();
        move3 = auto_move3.toString();
        move4 = auto_move4.toString();
        nature = spn_nature.getSelectedItem().toString(); // TODO, show what gets a boost and what doesn't

        if(TextUtils.isEmpty(et_hp_iv.getText()) || TextUtils.isEmpty(et_att_iv.getText()) || // TODO, complete the checks for all inputs
                TextUtils.isEmpty(et_def_iv.getText()) || TextUtils.isEmpty(et_spa_iv.getText()) || // TODO, fix these checks
                TextUtils.isEmpty(et_spd_iv.getText()) || TextUtils.isEmpty(et_sp_iv.getText()) ||
                TextUtils.isEmpty(et_hp_ev.getText()) || TextUtils.isEmpty(et_att_ev.getText()) ||
                TextUtils.isEmpty(et_def_ev.getText()) || TextUtils.isEmpty(et_spa_ev.getText()) ||
                TextUtils.isEmpty(et_spd_ev.getText()) || TextUtils.isEmpty(et_sp_ev.getText())) {
            Toast.makeText(this, "Oops, you forgot to fill in some IV's/EV's!", Toast.LENGTH_SHORT).show();
        } else {
            hp_iv = Integer.parseInt(et_hp_iv.getText().toString());
            att_iv = Integer.parseInt(et_att_iv.getText().toString());
            def_iv = Integer.parseInt(et_def_iv.getText().toString());
            spa_iv = Integer.parseInt(et_spa_iv.getText().toString());
            spd_iv = Integer.parseInt(et_spd_iv.getText().toString());
            sp_iv = Integer.parseInt(et_sp_iv.getText().toString());
            hp_ev = Integer.parseInt(et_hp_ev.getText().toString());
            att_ev = Integer.parseInt(et_att_ev.getText().toString());
            def_ev = Integer.parseInt(et_def_ev.getText().toString());
            spa_ev = Integer.parseInt(et_spa_ev.getText().toString());
            spd_ev = Integer.parseInt(et_spd_ev.getText().toString());
            sp_ev = Integer.parseInt(et_sp_ev.getText().toString());
        }

        if (isAnyStringNullOrEmpty(item, ability, move1, move2, move3, move4, nature, gender)) {
            Toast.makeText(AddActivity.this, "Oops, you forgot to fill in some fields!", Toast.LENGTH_SHORT).show();
        } else if (isAnyIntNullOrEmpty(hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev,
                def_ev, spa_ev, spd_ev, sp_ev)) {
            Toast.makeText(this, "Oops, you forgot to fill", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "adding to db", Toast.LENGTH_SHORT).show();
            addPokemon(view);
        }
    }

    // Add complete pokemon to database
    public void addPokemon(View view) {

        // Get database
        PokemonDatabase db = PokemonDatabase.getInstance(getApplicationContext());

        // Save pokemon to database
        SavedPokemon savedPokemon = new SavedPokemon(0, name, item, ability, move1, move2, move3,
                move4, nature, hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev, def_ev,
                spa_ev, spd_ev, sp_ev, url, url_shiny, gender, type1, type2);
        db.insert(savedPokemon);
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        // Get rid of opening animation of new activity
        overridePendingTransition(0, 0);
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
