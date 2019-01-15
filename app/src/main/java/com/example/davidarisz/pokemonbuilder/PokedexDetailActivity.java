package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexDetailActivity extends AppCompatActivity implements PokemonDataRequest.Callback {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokedex);

        Intent intent = getIntent();
        String name = intent.getStringExtra("nameTag");
        pokemonNames = intent.getStringArrayListExtra("namesTag");

        PokemonDataRequest request = new PokemonDataRequest(PokedexDetailActivity.this, name);
        request.getData(this);

        Button button = findViewById(R.id.btn_pokedex_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));
    }

    public void gotData(Pokemon pokemon) {
        String name = pokemon.getName();
        String weight = String.valueOf(pokemon.getWeight());
        String height = String.valueOf(pokemon.getHeight());

//        String normal = "https://img.pokemondb.net/artwork/large/"+name+".jpg";
//        String shiny = "https://img.pokemondb.net/artwork/large/"+name+".jpg";
        String normal = pokemon.getSprites().getFront_default();
        String shiny = pokemon.getSprites().getFront_shiny();

        TextView tv_name = findViewById(R.id.tv_name);
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        tv_name.setText(name2);

        ImageView iv_normal = findViewById(R.id.img_normal);
        Picasso.get().load(normal).into(iv_normal);

        ImageView iv_shiny = findViewById(R.id.img_shiny);
        Picasso.get().load(shiny).into(iv_shiny);

        TextView tv_weight = findViewById(R.id.tv_weight);
        weight = weight.substring(0,weight.length()-1) + "." + weight.substring(weight.length()-1);
        String weight2 = "Weight: " + weight + " kg";
        tv_weight.setText(weight2);

        TextView tv_height = findViewById(R.id.tv_height);
        if(height.length() < 2) {
            height = "0" + height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        } else {
            height = height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        }
        String height2 = "Height: " + height + " m";
        tv_height.setText(height2);
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void toSearch(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void toPokedex(View view) {
        Intent intent = new Intent(this, PokedexActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
