/*
Author: David Arisz

ListActivity is where you can see all your saved pokemon, and click them to go see their detals.
It is also where you have to load in all the data for items, natures and moves the very first time
you start the app after installing it
 */

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
import android.widget.ProgressBar;
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

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements PokemonNamesRequest.Callback, NatureNamesRequest.Callback,
        NatureDataRequest.Callback, ItemNamesRequest.Callback, ItemDataRequest.Callback, MoveNamesRequest.Callback,
        MoveDataRequest.Callback {
    private ArrayList pokemonNames, movesArray;
    private ListAdapter adapter;
    private Cursor cursor;

    private PokemonDatabase db;
    private NatureDatabase natureDb;
    private ItemDatabase itemDb;
    private MoveDatabase moveDb;

    private int nr, counter, limit, tot_count;
    private String progress;

    private ProgressBar progressBar;
    private TextView tv_progress;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Set 'selected' color to current 'tab'
        Button button = findViewById(R.id.btn_list_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        // Set progressbars for loading data into databases
        progressBar = findViewById(R.id.progress_bar_list);
        tv_progress = findViewById(R.id.tv_progress_list);

        progressBar.setVisibility(View.GONE);
        tv_progress.setVisibility(View.GONE);

        // If the databases are filled, delete the loading buttons
        natureDb = NatureDatabase.getInstance(getApplicationContext());
        Toast.makeText(this, "natures: " + String.valueOf(natureDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if (natureDb.selectAll().getCount() > 0) {
            Button loadNature = findViewById(R.id.load_natures);
            loadNature.setVisibility(View.GONE);
        }

        itemDb = ItemDatabase.getInstance(getApplicationContext());
        Toast.makeText(this, "items: " + String.valueOf(itemDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if (itemDb.selectAll().getCount() > 0) {
            Button loadNature = findViewById(R.id.load_items);
            loadNature.setVisibility(View.GONE);
        }

        moveDb = MoveDatabase.getInstance(getApplicationContext()); // TODO, check which button is already loaded
        Toast.makeText(this, "Moves: " + String.valueOf(moveDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();

        // If move database has specific moves, those corresponding buttons won't show
        if (moveDb.selectMove("swords-dance").getCount() == 1) {
            Button loadMove1 = findViewById(R.id.load_moves1);
            loadMove1.setVisibility(View.GONE);
        }

        if (moveDb.selectMove("sleep-talk").getCount() == 1) {
            Button loadMove2 = findViewById(R.id.load_moves2);
            loadMove2.setVisibility(View.GONE);
        }

        if (moveDb.selectMove("drain-punch").getCount() == 1) {
            Button loadMove3 = findViewById(R.id.load_moves3);
            loadMove3.setVisibility(View.GONE);
        }

        if (moveDb.selectMove("work-up").getCount() == 1) {
            Button loadMove4 = findViewById(R.id.load_moves4);
            loadMove4.setVisibility(View.GONE);
        }

        if (moveDb.selectMove("thousand-waves").getCount() == 1) {
            Button loadMove5 = findViewById(R.id.load_moves5);
            loadMove5.setVisibility(View.GONE);
        }

        // Continue loading if database isn't full
        if (moveDb.selectAll().getCount() < 660) {
            MoveNamesRequest moveNamesRequest = new MoveNamesRequest(this);
            moveNamesRequest.getMoveNames(this);
        } else {
            Button delDatabase = findViewById(R.id.del_database);
            delDatabase.setVisibility(View.GONE);
        }

        PokemonNamesRequest request = new PokemonNamesRequest(this);
        request.getPokemon(this);

        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter = new ListAdapter(this, db.selectAll());

        listView = findViewById(R.id.lv_saved_pokemon);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListViewClickListener());
        listView.setOnItemLongClickListener(new ListViewLongClickListener());
    }


    // Set pokemon names to arraylist
    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
    }


    // Testing method for deleting move database
    public void deleteDB(View view) {
        moveDb.remove();
        Toast.makeText(this, "Count: " + String.valueOf(moveDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
    }


    // Make request to the api for all nature names
    public void loadNatures(View view) {
        NatureNamesRequest natureNamesRequest = new NatureNamesRequest(this);
        natureNamesRequest.getNatureNames(this);

        // Showing progres to show user loading feedback
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);

        tv_progress.setText("Loading Natures...");
    }


    // Make new api request for data for specific natures
    public void gotNatureNames(ArrayList<String> natures) {
        counter = natures.size();
        nr = 0;

        // Set progress parameters
        progressBar.setMax(counter);
        progressBar.setProgress(nr);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);

        // For each nature, make a data request to the api
        for (int i = 0; i < natures.size(); i++) {
            String name = natures.get(i);

            NatureDataRequest natureDataRequest = new NatureDataRequest(this, name);
            natureDataRequest.getNatureData(this);
        }
    }


    // Make nature data arraylist, show the progress, check if loading is done
    public void gotNatureData(NatureData natureData) {
        natureDb.insert(natureData);

        // Updating progress feedback
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);
        nr += 1;

        // Stop showing buttons and progress when loading is done
        if (nr == counter) {
            Toast.makeText(this, "done loading natures", Toast.LENGTH_SHORT).show();

            Button load_natures = findViewById(R.id.load_natures);
            load_natures.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Make request to the api for all item names
    public void loadItems(View view) {
        ItemNamesRequest itemNamesRequest = new ItemNamesRequest(this);
        itemNamesRequest.getItemNames(this);

        // Showing progres to show user loading feedback
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
    }


    // Make new api request for data for specific natures
    public void gotItemNames(ArrayList<String> items) {
        counter = items.size();
        nr = 0;

        // Set progress parameters
        progressBar.setMax(counter);
        progressBar.setProgress(nr);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);

        // For each nature, make a data request to the api
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i);
            ItemDataRequest itemDataRequest = new ItemDataRequest(this, name);
            itemDataRequest.getItemData(this);
        }

    }


    // Insert item data into database, show the progress, check if loading is done
    public void gotItemData(ItemData itemData) {
        itemDb.insert(itemData);

        // Updating progress feedback
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);
        nr += 1;

        // Stop showing buttons and progress when loading is done
        if (nr == counter) {
            Toast.makeText(this, "done loading items", Toast.LENGTH_SHORT).show();
            Button load_items = findViewById(R.id.load_items);
            load_items.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Set all move names to array
    public void gotMoveNames(ArrayList<String> moves) {
        movesArray = moves;
    }


    // Make api request for specific move data and load it into database
    public void loadMoves(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading ...");

        if (movesArray != null) {

            // Check which button was clicked
            // Loading is divided into parts, otherwise there are too many requests at once
            switch (view.getId()) {
                case R.id.load_moves1:
                    nr = 0;
                    limit = 150;
                    tot_count = limit;

                    progressBar.setMax(limit);
                    progressBar.setProgress(nr);
                    progress = progressBar.getProgress() + "/" + limit;
                    tv_progress.setText(progress);

                    for (int i = nr; i < limit; i++) {
                        String name = (String) movesArray.get(i);
                        MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                        moveDataRequest.getMoveData(this);
                        Log.d("ammountTag", "" + i);
                    }

                    break;
                case R.id.load_moves2:
                    nr = 150;
                    limit = 300;
                    tot_count = limit - nr;

                    progressBar.setMax(limit - nr);
                    progressBar.setProgress(0);
                    progress = progressBar.getProgress() + "/" + (limit - nr);
                    tv_progress.setText(progress);

                    for (int i = nr; i < limit; i++) {
                        String name = (String) movesArray.get(i);
                        MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                        moveDataRequest.getMoveData(this);
                        Log.d("ammountTag", "" + i);
                    }

                    break;
                case R.id.load_moves3:
                    nr = 300;
                    limit = 450;
                    tot_count = limit - nr;

                    progressBar.setMax(limit - nr);
                    progressBar.setProgress(0);
                    progress = progressBar.getProgress() + "/" + (limit - nr);
                    tv_progress.setText(progress);

                    for (int i = nr; i < limit; i++) {
                        String name = (String) movesArray.get(i);
                        MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                        moveDataRequest.getMoveData(this);
                        Log.d("ammountTag", "" + i);
                    }

                    break;
                case R.id.load_moves4:
                    nr = 450;
                    limit = 600;
                    tot_count = limit - nr;

                    progressBar.setMax(limit - nr);
                    progressBar.setProgress(0);
                    progress = progressBar.getProgress() + "/" + (limit - nr);
                    tv_progress.setText(progress);

                    for (int i = nr; i < limit; i++) {
                        String name = (String) movesArray.get(i);
                        MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                        moveDataRequest.getMoveData(this);
                        Log.d("ammountTag", "" + i);
                    }

                    break;
                case R.id.load_moves5:
                    nr = 600;
                    limit = movesArray.size() - 18;
                    tot_count = limit - nr;

                    progressBar.setMax(limit - nr);
                    progressBar.setProgress(0);
                    progress = progressBar.getProgress() + "/" + (limit - nr);
                    tv_progress.setText(progress);

                    for (int i = nr; i < limit; i++) {
                        String name = (String) movesArray.get(i);
                        MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                        moveDataRequest.getMoveData(this);
                        Log.d("ammountTag", "" + i);
                    }

                    break;

            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Insert move data into database, show the progress, check if loading is done
    public void gotMoveData(MoveData moveData) {
        moveDb.insert(moveData);

        // Update user feedback
        nr += 1;
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + tot_count;
        tv_progress.setText(progress);

        Log.d("ammountTag", "add to db " + nr);
        Log.d("ammountTag", moveData.getName());

        // Check if loading is done for when different buttons are clicked
        if (nr == limit) {

            if (limit == 150) {
                Button move1 = findViewById(R.id.load_moves1);
                move1.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }

            if (limit == 300) {
                Button move2 = findViewById(R.id.load_moves2);
                move2.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }

            if (limit == 450) {
                Button move3 = findViewById(R.id.load_moves3);
                move3.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }

            if (limit == 600) {
                Button move4 = findViewById(R.id.load_moves4);
                move4.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }

            if (limit == movesArray.size() - 18) {
                Button move5 = findViewById(R.id.load_moves5);
                move5.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
        }
    }


    // OnItemClickListener for listview to see details for a specific pokemon
    private class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            // Load database and get selected pokemon
            db = PokemonDatabase.getInstance(getApplicationContext());
            cursor = (Cursor) adapterView.getItemAtPosition(position);

            // Get data from selected pokemon
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
            String type1 = cursor.getString(cursor.getColumnIndex("type1"));
            String type2 = cursor.getString(cursor.getColumnIndex("type2"));

            // Make SavedPokemon object with data from pokemon
            SavedPokemon savedPokemon = new SavedPokemon(0, name, item, ability, move1, move2,
                    move3, move4, nature, hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev,
                    att_ev, def_ev, spa_ev, spd_ev, sp_ev, url, url_shiny, gender, type1, type2);

            // Go to detail activity of selected pokemon
            Intent intent = new Intent(ListActivity.this, ListDetailActivity.class);
            intent.putExtra("savedTag", savedPokemon);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);

            // Get rid of opening animation of new activity
            overridePendingTransition(0, 0);
        }
    }


    // LongClickListener for deleting pokemon from the database
    private class ListViewLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            cursor = (Cursor) parent.getItemAtPosition(position);
            int entry_id = cursor.getInt(cursor.getColumnIndex("_id"));

            // Delete entry from database
            db.remove(entry_id);
            updateData();

            return true;
        }
    }


    // Already on this activity
    public void toList(View view) {
        // Do nothing
    }


    // Go to add activity
    public void toAdd(View view) {
        if (pokemonNames == null) {
            Toast.makeText(this, "List not loaded yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AddActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);

            overridePendingTransition(0, 0);
        }
    }


    // Go to pokedex activity
    public void toPokedex(View view) {
        if (pokemonNames == null) {
            Toast.makeText(this, "List not loaded yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PokedexActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);

            overridePendingTransition(0, 0);
        }
    }


    //
    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0, 0);
        updateData();
    }


    //
    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }


    // Update the database and listview
    private void updateData() {
        db = PokemonDatabase.getInstance(getApplicationContext());
        adapter.swapCursor(db.selectAll());
    }

    // Probably obsolete
    public void loadMove1(View view) {


        if (movesArray != null) {
            nr = 0;
            limit = 150;
            tot_count = limit;
            progressBar.setMax(limit);
            progressBar.setProgress(nr);
            progress = progressBar.getProgress() + "/" + limit;
            tv_progress.setText(progress);

            for (int i = nr; i < limit; i++) {
                String name = (String) movesArray.get(i);
                MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                moveDataRequest.getMoveData(this);
                Log.d("ammountTag", "" + i);
            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Probably obsolete
    public void loadMove2(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");

        if (movesArray != null) {
            nr = 150;
            limit = 300;
            tot_count = limit - nr;
            progressBar.setMax(limit - nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit - nr);
            tv_progress.setText(progress);

            for (int i = nr; i < limit; i++) {
                String name = (String) movesArray.get(i);
                MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                moveDataRequest.getMoveData(this);
                Log.d("ammountTag", "" + i);
            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Probably obsolete
    public void loadMove3(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");

        if (movesArray != null) {
            nr = 300;
            limit = 450;
            tot_count = limit - nr;
            progressBar.setMax(limit - nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit - nr);
            tv_progress.setText(progress);

            for (int i = nr; i < limit; i++) {
                String name = (String) movesArray.get(i);
                MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                moveDataRequest.getMoveData(this);
                Log.d("ammountTag", "" + i);
            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Probably obsolete
    public void loadMove4(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");

        if (movesArray != null) {
            nr = 450;
            limit = 600;
            tot_count = limit - nr;
            progressBar.setMax(limit - nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit - nr);
            tv_progress.setText(progress);

            for (int i = nr; i < limit; i++) {
                String name = (String) movesArray.get(i);
                MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                moveDataRequest.getMoveData(this);
                Log.d("ammountTag", "" + i);
            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }


    // Probably obsolete
    public void loadMove5(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");

        if (movesArray != null) {
            nr = 600;
            limit = movesArray.size() - 18;
            tot_count = limit - nr;
            progressBar.setMax(limit - nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit - nr);
            tv_progress.setText(progress);

            for (int i = nr; i < limit; i++) {
                String name = (String) movesArray.get(i);
                MoveDataRequest moveDataRequest = new MoveDataRequest(this, name);
                moveDataRequest.getMoveData(this);
                Log.d("ammountTag", "" + i);
            }
        } else {
            Toast.makeText(this, "moves not loaded", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }
}
