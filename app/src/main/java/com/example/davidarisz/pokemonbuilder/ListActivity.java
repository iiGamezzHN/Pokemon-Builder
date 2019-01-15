package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Requests.PokemonNamesRequest;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements PokemonNamesRequest.Callback {
    private ArrayList pokemonNames;
    private PokemonDatabase db;
    private ListAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        PokemonNamesRequest request = new PokemonNamesRequest(this);
        request.getPokemon(this);

        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter = new ListAdapter(this, db.selectAll());

        ListView listView = findViewById(R.id.lv_saved_pokemon);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListViewClickListener());

        Button button = findViewById(R.id.btn_list_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));
    }

    private class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Do something
            db = PokemonDatabase.getInstance(getApplicationContext());
            cursor = (Cursor) adapterView.getItemAtPosition(position);

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String item = cursor.getString(cursor.getColumnIndex("item"));
            String ability = cursor.getString(cursor.getColumnIndex("ability"));
            String move1 = cursor.getString(cursor.getColumnIndex("move1"));
            String move2 = cursor.getString(cursor.getColumnIndex("move2"));
            String move3 = cursor.getString(cursor.getColumnIndex("move3"));
            String move4 = cursor.getString(cursor.getColumnIndex("move4"));
            String nature = cursor.getString(cursor.getColumnIndex("nature"));
            int hp_iv = cursor.getInt(cursor.getColumnIndex("hp_iv"));
            int att_iv = cursor.getInt(cursor.getColumnIndex("att_iv"));
            int def_iv = cursor.getInt(cursor.getColumnIndex("def_iv"));
            int spa_iv = cursor.getInt(cursor.getColumnIndex("spa_iv"));
            int spd_iv = cursor.getInt(cursor.getColumnIndex("spd_iv"));
            int sp_iv = cursor.getInt(cursor.getColumnIndex("sp_iv"));
            int hp_ev = cursor.getInt(cursor.getColumnIndex("hp_ev"));
            int att_ev = cursor.getInt(cursor.getColumnIndex("att_ev"));
            int def_ev = cursor.getInt(cursor.getColumnIndex("def_ev"));
            int spa_ev = cursor.getInt(cursor.getColumnIndex("spa_ev"));
            int spd_ev = cursor.getInt(cursor.getColumnIndex("spd_ev"));
            int sp_ev = cursor.getInt(cursor.getColumnIndex("sp_ev"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String url_shiny = cursor.getString(cursor.getColumnIndex("url_shiny"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));

            SavedPokemon savedPokemon = new SavedPokemon(0,name,item,ability,move1,move2,move3,move4,
                    nature,hp_iv,att_iv,def_iv,spa_iv,spd_iv,sp_iv,hp_ev,att_ev,def_ev,spa_ev,spd_ev,sp_ev,url,url_shiny,gender);

            Intent intent = new Intent(ListActivity.this, ListDetailActivity.class);
            intent.putExtra("savedTag", savedPokemon);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
    }

    public void toList(View view) {
        // Do nothing
    }

    public void toAdd(View view) {
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
