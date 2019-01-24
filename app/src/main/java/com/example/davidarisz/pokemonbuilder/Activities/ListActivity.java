package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
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
    private PokemonDatabase db;
    private ListAdapter adapter;
    private Cursor cursor;
    private NatureDatabase natureDb;
    private ItemDatabase itemDb;
    private MoveDatabase moveDb;
    private int nr, counter, limit, tot_count;
    private ProgressBar progressBar;
    private TextView tv_progress;
    private String progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar = findViewById(R.id.progress_bar_list);
        progressBar.setVisibility(View.GONE);
        tv_progress = findViewById(R.id.tv_progress_list);
        tv_progress.setVisibility(View.GONE);

        natureDb = NatureDatabase.getInstance(getApplicationContext());
        Toast.makeText(this, "natures: "+String.valueOf(natureDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if(natureDb.selectAll().getCount() > 0) {
            Button loadNature = findViewById(R.id.load_natures);
            loadNature.setVisibility(View.GONE);
        }

        itemDb = ItemDatabase.getInstance(getApplicationContext());
        Toast.makeText(this, "items: "+String.valueOf(itemDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if(itemDb.selectAll().getCount() > 0) {
            Button loadNature = findViewById(R.id.load_items);
            loadNature.setVisibility(View.GONE);
        }

        moveDb = MoveDatabase.getInstance(getApplicationContext()); // TODO, check which button is already loaded
        Toast.makeText(this, "Moves: "+String.valueOf(moveDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
        if(moveDb.selectMove("swords-dance").getCount() == 1) {
            Button loadMove1 = findViewById(R.id.load_moves1);
            loadMove1.setVisibility(View.GONE);
        }
        if(moveDb.selectMove("sleep-talk").getCount() == 1) {
            Button loadMove2 = findViewById(R.id.load_moves2);
            loadMove2.setVisibility(View.GONE);
        }
        if(moveDb.selectMove("drain-punch").getCount() == 1) {
            Button loadMove3 = findViewById(R.id.load_moves3);
            loadMove3.setVisibility(View.GONE);
        }
        if(moveDb.selectMove("work-up").getCount() == 1) {
            Button loadMove4 = findViewById(R.id.load_moves4);
            loadMove4.setVisibility(View.GONE);
        }
        if(moveDb.selectMove("thousand-waves").getCount() == 1) {
            Button loadMove5 = findViewById(R.id.load_moves5);
            loadMove5.setVisibility(View.GONE);
        }
        if(moveDb.selectAll().getCount() < 660) {
            MoveNamesRequest moveNamesRequest = new MoveNamesRequest(this);
            moveNamesRequest.getMoveNames(this);
        }

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

    public void deleteDB(View view) {
        moveDb.remove();
        Toast.makeText(this, "Count: "+String.valueOf(moveDb.selectAll().getCount()), Toast.LENGTH_SHORT).show();
    }

    public void loadNatures(View view) {
        NatureNamesRequest natureNamesRequest = new NatureNamesRequest(this);
        natureNamesRequest.getNatureNames(this);
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Natures...");
    }

    public void gotNatureNames(ArrayList<String> natures) {
        counter = natures.size();
        nr = 0;
        progressBar.setMax(counter);
        progressBar.setProgress(nr);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);
        for(int i = 0; i < natures.size(); i++) {
            String name = natures.get(i);
            NatureDataRequest natureDataRequest = new NatureDataRequest(this, name);
            natureDataRequest.getNatureData(this);
        }
    }

    public void gotNatureData(NatureData natureData) {
        natureDb.insert(natureData);
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);
        nr += 1;
        if (nr == counter) {
            Toast.makeText(this, "done loading natures", Toast.LENGTH_SHORT).show();
            Button load_natures = findViewById(R.id.load_natures);
            load_natures.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }

    public void loadItems(View view) {
        ItemNamesRequest itemNamesRequest = new ItemNamesRequest(this);
        itemNamesRequest.getItemNames(this);
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
    }

    public void gotItemNames(ArrayList<String> items) {
        counter = items.size();
        nr = 0;
        progressBar.setMax(counter);
        progressBar.setProgress(nr);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);

//        Log.d("sizeTag", "items: "+String.valueOf(items.size()));
        for(int i = 0; i < items.size(); i++) {
            String name = items.get(i);
            ItemDataRequest itemDataRequest = new ItemDataRequest(this, name);
            itemDataRequest.getItemData(this);
        }

    }

    public void gotItemData(ItemData itemData) {
        itemDb.insert(itemData);
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + counter;
        tv_progress.setText(progress);
        nr += 1;
        if(nr == counter) {
            Toast.makeText(this, "done loading items", Toast.LENGTH_SHORT).show();
            Button load_items = findViewById(R.id.load_items);
            load_items.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
        }
    }

    public void gotMoveNames(ArrayList<String> moves) {
        movesArray = moves;
    }

    public void loadMove1(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
        if(movesArray != null) {
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

    public void loadMove2(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
        if(movesArray != null) {
            nr = 150;
            limit = 300;
            tot_count = limit-nr;
            progressBar.setMax(limit-nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit-nr);
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

    public void loadMove3(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
        if(movesArray != null) {
            nr = 300;
            limit = 450;
            tot_count = limit-nr;
            progressBar.setMax(limit-nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit-nr);
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

    public void loadMove4(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
        if(movesArray != null) {
            nr = 450;
            limit = 600;
            tot_count = limit-nr;
            progressBar.setMax(limit-nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit-nr);
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

    public void loadMove5(View view) {
        progressBar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("Loading Items...");
        if(movesArray != null) {
            nr = 600;
            limit = movesArray.size()-18;
            tot_count = limit-nr;
            progressBar.setMax(limit-nr);
            progressBar.setProgress(0);
            progress = progressBar.getProgress() + "/" + (limit-nr);
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

    public void gotMoveData(MoveData moveData) {
        moveDb.insert(moveData);
        nr += 1;
        progressBar.incrementProgressBy(1);
        progress = progressBar.getProgress() + "/" + tot_count;
        tv_progress.setText(progress);
        Log.d("ammountTag", "add to db "+nr);
        Log.d("ammountTag", moveData.getName());

        if(nr == limit) {
            if(limit == 150) {
                Button move1 = findViewById(R.id.load_moves1);
                move1.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
            if(limit == 300) {
                Button move2 = findViewById(R.id.load_moves2);
                move2.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
            if(limit == 450) {
                Button move3 = findViewById(R.id.load_moves3);
                move3.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
            if(limit == 600) {
                Button move4 = findViewById(R.id.load_moves4);
                move4.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
            if(limit == movesArray.size()-18) {
                Button move5 = findViewById(R.id.load_moves5);
                move5.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
            Log.d("ammountTag", "finished");
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
            String type1 = cursor.getString(cursor.getColumnIndex("type1"));
            String type2 = cursor.getString(cursor.getColumnIndex("type2"));

            SavedPokemon savedPokemon = new SavedPokemon(0, name, item, ability, move1, move2,
                    move3, move4, nature, hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev,
                    att_ev, def_ev, spa_ev, spd_ev, sp_ev, url, url_shiny, gender, type1, type2);

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
