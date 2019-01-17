package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.Classes.NatureData;
import com.example.davidarisz.pokemonbuilder.Classes.SavedPokemon;
import com.example.davidarisz.pokemonbuilder.Databases.ItemDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.MoveDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.NatureDatabase;
import com.example.davidarisz.pokemonbuilder.Databases.PokemonDatabase;
import com.example.davidarisz.pokemonbuilder.Adapters.ListAdapter;
import com.example.davidarisz.pokemonbuilder.R;
import com.example.davidarisz.pokemonbuilder.Requests.ItemDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.ItemNamesRequest;
import com.example.davidarisz.pokemonbuilder.Requests.MoveDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.MoveNamesRequest;
import com.example.davidarisz.pokemonbuilder.Requests.NatureDataRequest;
import com.example.davidarisz.pokemonbuilder.Requests.NatureNamesRequest;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonNamesRequest;
import com.example.davidarisz.pokemonbuilder.models.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements PokemonNamesRequest.Callback, NatureNamesRequest.Callback,
    NatureDataRequest.Callback, ItemNamesRequest.Callback, ItemDataRequest.Callback, MoveNamesRequest.Callback,
    MoveDataRequest.Callback {
    private ArrayList pokemonNames;
    private PokemonDatabase db;
    private ListAdapter adapter;
    private Cursor cursor;
    private NatureDatabase natureDb;
    private ItemDatabase itemDb;
    private MoveDatabase moveDb;
    private int nr;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        natureDb = NatureDatabase.getInstance(getApplicationContext());
        if(natureDb.selectAll().getCount() > 0) {
            // Do nothing
        } else {
            NatureNamesRequest natureNamesRequest = new NatureNamesRequest(this);
            natureNamesRequest.getNatureNames(this);
        }

        itemDb = ItemDatabase.getInstance(getApplicationContext());
        if(itemDb.selectAll().getCount() > 0) {
            // Do nothing
//            Toast.makeText(this, String.valueOf(itemDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
//            Cursor cursor = itemDb.selectAll();
//            try {
//                while (cursor.moveToNext()) {
//                    String test = cursor.getString(cursor.getColumnIndex("name"));
//                    String test1 = cursor.getString(cursor.getColumnIndex("effect"));
//                    String test2 = cursor.getString(cursor.getColumnIndex("sprite"));
////                    Toast.makeText(this, test+", "+test1+", "+test2, Toast.LENGTH_SHORT).show();
//                }
//            } finally {
//                cursor.close();
//            }
        } else {
            ItemNamesRequest itemNamesRequest = new ItemNamesRequest(this);
            itemNamesRequest.getItemNames(this);
        }

        moveDb = MoveDatabase.getInstance(getApplicationContext());
        Toast.makeText(this, String.valueOf(moveDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if(moveDb.selectAll().getCount() > 0) {
            // Do nothing
        } else {
            TextView textView = findViewById(R.id.loading);
            textView.setText("loading");
            MoveNamesRequest moveNamesRequest = new MoveNamesRequest(this);
            moveNamesRequest.getMoveNames(this);
        }

        MoveNamesRequest moveNamesRequest = new MoveNamesRequest(this);
        moveNamesRequest.getMoveNames(this);

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

    public void gotNatureNames(ArrayList<String> natures) {
        for(int i = 0; i < natures.size(); i++) {
            String name = natures.get(i);
            NatureDataRequest natureDataRequest = new NatureDataRequest(this, name);
            natureDataRequest.getNatureData(this);
        }
    }

    public void gotNatureData(NatureData natureData) {
        natureDb.insert(natureData);
    }

    public void gotItemNames(ArrayList<String> items) {
        for(int i = 0; i < items.size(); i++) {
            String name = items.get(i);
            ItemDataRequest itemDataRequest = new ItemDataRequest(this, name);
            itemDataRequest.getItemData(this);
        }
    }

    public void gotItemData(ItemData itemData) {
        itemDb.insert(itemData);
    }

    public void gotMoveNames(ArrayList<String> moves) {
        counter = moves.size();
        Toast.makeText(this, String.valueOf(counter), Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.loading);
        for(int i = 0; i < moves.size(); i++) {
            textView.setText("data loading");
            String name = moves.get(1);
//            Toast.makeText(this, String.valueOf(nr), Toast.LENGTH_SHORT).show();
//            MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
//            moveDataRequest.getMoveData(this);
        }
    }

    public void gotMoveData(MoveData moveData) {
        nr += 1;
        moveDb.insert(moveData);
        if(nr == counter) {
            TextView textView = findViewById(R.id.loading);
            textView.setText("done loading");
            Toast.makeText(this, "done loading moves", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, String.valueOf(counter), Toast.LENGTH_SHORT).show();
        }
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
    }

    private void updateData() { // Update the data
        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter.swapCursor(db.selectAll());
    }
}
