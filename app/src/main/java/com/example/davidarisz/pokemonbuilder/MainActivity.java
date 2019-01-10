package com.example.davidarisz.pokemonbuilder;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PokemonRequest.Callback, DataRequest.Callback {
    private ArrayList<String> pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonRequest request = new PokemonRequest(this);
        request.getPokemon(this);

        loadFragments();
    }

    public void loadFragments () {
        // Set up the tab layout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Pokemon List"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Pokemon"));
        tabLayout.addTab(tabLayout.newTab().setText("Pokedex"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Set up the ViewPager (part below the tabs)
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void gotPokemon(final ArrayList<String> pokemon) {
        pokemonNames = pokemon;
        Log.d("pokeTag", String.valueOf(pokemon.size()));

//        AddFragment addFragment = new AddFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("pokemonTag", pokemonNames);
//        Log.d("bundleTag2", String.valueOf(bundle));
//
//
//        // set Fragmentclass Arguments
//
//        addFragment.setArguments(bundle);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container , addFragment).commit();




    }

    public void gotData (Pokemon pokemon) {
        ArrayList<String> moves = new ArrayList<String>();
        for (MoveItem moveItem : pokemon.getMoves()) {
            moves.add(moveItem.getMove().getName());
        }

        Spinner move1 = findViewById(R.id.move1);
        Spinner move2 = findViewById(R.id.move2);
        Spinner move3 = findViewById(R.id.move3);
        Spinner move4 = findViewById(R.id.move4);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, moves);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        move1.setAdapter(arrayAdapter);
        move2.setAdapter(arrayAdapter);
        move3.setAdapter(arrayAdapter);
        move4.setAdapter(arrayAdapter);
    }

    public ArrayList getPokemon() {
        return pokemonNames;
    }

    public void makeRequest () {
        DataRequest request = new DataRequest(MainActivity.this);
        request.getData(this);
    }
}
