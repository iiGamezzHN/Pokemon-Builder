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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Adapters.AbilityAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.ItemAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.MoveAdapter;
import com.example.davidarisz.pokemonbuilder.Adapters.NatureAdapter;
import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.Classes.NatureData;
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
    private ArrayList<String> pokemonNames;
    private ArrayList<AbilityData> abilities = new ArrayList<>();

    private String name, url, url_shiny;
    private String item, ability, move1, move2, move3, move4, nature, gender, type1, type2 = "";
    private int nr_abilities, nr_loop;
    private int hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev, def_ev, spa_ev, spd_ev, sp_ev = -1;

    private ItemAdapter itemAdapter;

    private TextView tv_name;
    private EditText et_hp_iv, et_att_iv, et_def_iv, et_spa_iv, et_spd_iv, et_sp_iv;
    private EditText et_hp_ev, et_att_ev, et_def_ev, et_spa_ev, et_spd_ev, et_sp_ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        // Check if you came from the pokedex to add a pokemon
        if(getIntent().getStringExtra("addName") != null) {
            name = getIntent().getStringExtra("addName");
            String name2 = name.substring(0,1).toUpperCase() + name.substring(1); // TODO, needs comment?
            tv_name = findViewById(R.id.tv_name);
            String adding = "Adding: " + name2;
            tv_name.setText(adding);
        } else {
            tv_name = findViewById(R.id.tv_name);
            String adding = "Adding: ";
            tv_name.setText(adding);
        }

        // Set 'selected' color to current 'tab'
        Button button = findViewById(R.id.btn_add_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        // Load all EditTextViews for later
        setEditTextViews();

        // Shows popup search screen, to search for a pokemon
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(AddActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();

                        name = searchable.getTitle();
                        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
                        String adding = "Adding: " + name2;

                        tv_name = findViewById(R.id.tv_name);
                        tv_name.setText(adding);

                        // When a pokemon is selected, make the needed requests
                        makeRequest();
                    }
                }).show();
            }
        });
    }


    private void setEditTextViews() {
        et_hp_iv = findViewById(R.id.et_hp_iv);
        et_att_iv = findViewById(R.id.et_att_iv);
        et_def_iv = findViewById(R.id.et_def_iv);
        et_spa_iv = findViewById(R.id.et_spa_iv);
        et_spd_iv = findViewById(R.id.et_spd_iv);
        et_sp_iv = findViewById(R.id.et_sp_iv);
        et_hp_ev = findViewById(R.id.et_hp_ev);
        et_att_ev = findViewById(R.id.et_att_ev);
        et_def_ev = findViewById(R.id.et_def_ev);
        et_spa_ev = findViewById(R.id.et_spa_ev);
        et_spd_ev = findViewById(R.id.et_spd_ev);
        et_sp_ev = findViewById(R.id.et_sp_ev);
    }


    // Used in search popup
    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i)));
        }

        return items;
    }


    // Requests for individual pokemon data, nature names and item names
    public void makeRequest () {
        // Loading individual pokemon data
        PokemonDataRequest pokemonData = new PokemonDataRequest(this, name);
        pokemonData.getPokemonData(this);

        // Loading item data
        ArrayList<ItemData> items = new ArrayList<>();
        ItemDatabase itemDb = ItemDatabase.getInstance(getApplicationContext());
        Cursor cursor = itemDb.selectAll();

        while (cursor.moveToNext()) {
            String item_name = cursor.getString(cursor.getColumnIndex("name"));
            String item_effect = cursor.getString(cursor.getColumnIndex("effect"));
            String item_sprite = cursor.getString(cursor.getColumnIndex("sprite"));
            ItemData itemData = new ItemData(item_name, item_effect, item_sprite);
            items.add(itemData);
        }

        final AutoCompleteTextView auto_items = findViewById(R.id.auto_items);
        itemAdapter = new ItemAdapter(AddActivity.this, items);
        auto_items.setAdapter(itemAdapter);
        auto_items.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);

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
                ItemData itemData = itemAdapter.getItem(position); //TODO, get to add
                String itemName = itemData.getName();
                item = itemName.substring(0,1).toUpperCase() + itemName.substring(1);
            }
        });

        // Loading nature data
        NatureDatabase natureDb = NatureDatabase.getInstance(getApplicationContext());
        ArrayList<NatureData> natureArray = new ArrayList<>();
        Cursor cursor2 = natureDb.selectAll();

        while (cursor2.moveToNext()) {
            String nature_name = cursor2.getString(cursor2.getColumnIndex("name"));
            String nature_increased = cursor2.getString(cursor2.getColumnIndex("increased"));
            String nature_decreased = cursor2.getString(cursor2.getColumnIndex("decreased"));
            NatureData natureData = new NatureData(nature_name, nature_increased, nature_decreased);
            natureArray.add(natureData);
        }

        Spinner natures = findViewById(R.id.spn_nature);
        final NatureAdapter natureAdapter = new NatureAdapter(this, R.layout.spinner_nature_row,
                R.id.tv_name_nature, natureArray);
        natures.setAdapter(natureAdapter);
    }


    // Pokemon data
    public void gotPokemonData (Pokemon pokemon) {
        ArrayList<MoveData> moves = new ArrayList<>();
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
                MoveData moveData = new MoveData(move2,power,accuracy,pp,category,effect,type);
                moves.add(moveData);
            }
        }

        final MoveAdapter moveAdapter = new MoveAdapter(AddActivity.this, moves);
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
        auto_moves1.setOnItemClickListener(new AdapterView.OnItemClickListener() { // TODO, set clicklisteners for items and moves to get l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MoveData moveData = moveAdapter.getItem(position);
                move1 = moveData.getName();
            }
        });
        auto_moves2.setOnItemClickListener(new AdapterView.OnItemClickListener() { // TODO, set clicklisteners for items and moves to get l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MoveData moveData = moveAdapter.getItem(position);
                move2 = moveData.getName();
            }
        });
        auto_moves3.setOnItemClickListener(new AdapterView.OnItemClickListener() { // TODO, set clicklisteners for items and moves to get l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MoveData moveData = moveAdapter.getItem(position);
                move3 = moveData.getName();
            }
        });
        auto_moves4.setOnItemClickListener(new AdapterView.OnItemClickListener() { // TODO, set clicklisteners for items and moves to get l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MoveData moveData = moveAdapter.getItem(position);
                move4 = moveData.getName();
            }
        });

        // Get all types for pokemon
        for(TypesItem typesItem : pokemon.getTypes()) {
            if(typesItem.getSlot() == 1) {
                type1 = typesItem.getType().getName();
            } else if(typesItem.getSlot() == 2) {
                type2 = typesItem.getType().getName();
            }
        }

        // Get all abilities for pokemon
        nr_abilities = pokemon.getAbilities().size();
        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            String ability = abilityItem.getAbility().getName();
            Boolean hidden = abilityItem.is_hidden();

            AbilityDataRequest abilityDataRequest = new AbilityDataRequest(this, ability, hidden);
            abilityDataRequest.getAbilityData(this);
        }

        url = pokemon.getSprites().getFront_default();
        if (pokemon.getSprites().getFront_shiny() != null) {
            url_shiny = pokemon.getSprites().getFront_shiny();
        } else {
            url_shiny = "";
        }

        //Get all types for pokemon
        for(TypesItem typesItem : pokemon.getTypes()) {
            if(typesItem.getSlot() == 1) {
                String typeName = typesItem.getType().getName();
                type1 = typeName.substring(0,1).toUpperCase() + typeName.substring(1);
            } else if (typesItem.getSlot() == 2) {
                String typeName = typesItem.getType().getName();
                type2 = typeName.substring(0,1).toUpperCase() + typeName.substring(1);
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

        if (isAnyStringNullOrEmpty(move1, move2, move3, move4)) {
            Toast.makeText(AddActivity.this, "Oops, you forgot to fill in some fields!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_hp_iv.getText())) {
            Toast.makeText(AddActivity.this, "Oops, you forgot to fill in IV's!", Toast.LENGTH_SHORT).show();
        } else {

            // Get elements for layout
            CheckBox chk_male = findViewById(R.id.chk_male);
            CheckBox chk_female = findViewById(R.id.chk_female);
            CheckBox chk_genderless = findViewById(R.id.chk_genderless);
            Spinner spn_ability = findViewById(R.id.spn_ability);
            Spinner spn_nature = findViewById(R.id.spn_nature);

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
            AbilityData abilityData = (AbilityData) spn_ability.getSelectedItem();
            ability = abilityData.getName();

            NatureData natureData = (NatureData) spn_nature.getSelectedItem();
            String natureName = natureData.getName();
            nature = natureName.substring(0, 1).toUpperCase() + natureName.substring(1);
            Log.d("natureTag", nature); // TODO, show what gets a boost and what doesn't

            if (TextUtils.isEmpty(et_hp_iv.getText()) || TextUtils.isEmpty(et_att_iv.getText()) || // TODO, complete the checks for all inputs
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

            if (isAnyIntNullOrEmpty(hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev,
                    def_ev, spa_ev, spd_ev, sp_ev)) {
                Toast.makeText(this, "Oops, you forgot to fill", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "adding to db", Toast.LENGTH_SHORT).show();
                addPokemon(view);
            }
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
        Intent intent = new Intent(this, AddActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void resetIV(View view) {
        et_hp_iv.setText(null);
        et_att_iv.setText(null);
        et_def_iv.setText(null);
        et_spa_iv.setText(null);
        et_spd_iv.setText(null);
        et_sp_iv.setText(null);
    }

    public void resetEV(View view) {
        et_hp_ev.setText(null);
        et_att_ev.setText(null);
        et_def_ev.setText(null);
        et_spa_ev.setText(null);
        et_spd_ev.setText(null);
        et_sp_ev.setText(null);
    }

    // Testing purposes
    public void setIV(View view) {
        et_hp_iv.setText(""+1);
        et_att_iv.setText(""+2);
        et_def_iv.setText(""+3);
        et_spa_iv.setText(""+4);
        et_spd_iv.setText(""+5);
        et_sp_iv.setText(""+6);
    }

    // Testing purposes
    public void setEV(View view) {
        et_hp_ev.setText(""+7);
        et_att_ev.setText(""+8);
        et_def_ev.setText(""+9);
        et_spa_ev.setText(""+10);
        et_spd_ev.setText(""+11);
        et_sp_ev.setText(""+12);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
