package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.R;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonDataRequest;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexDetailActivity extends AppCompatActivity implements PokemonDataRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("nameTag");
        pokemonNames = intent.getStringArrayListExtra("namesTag");

        PokemonDataRequest request = new PokemonDataRequest(PokedexDetailActivity.this, name);
        request.getPokemonData(this);

        Button button = findViewById(R.id.btn_pokedex_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));

        FloatingActionButton fabButton = findViewById(R.id.floatingActionButton);
        fabButton.setImageResource(R.drawable.pokeball);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PokedexDetailActivity.this, "Clicked button", Toast.LENGTH_SHORT).show();

                String pass_name = tv_name.getText().toString();
                String pass_name2 = pass_name.substring(0,1).toLowerCase() + pass_name.substring(1);
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra("addName", pass_name2);
                intent.putStringArrayListExtra("namesTag", pokemonNames);
                startActivity(intent);
            }
        });
    }

    public void gotPokemonData(Pokemon pokemon) {
        String name = pokemon.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String weight = String.valueOf(pokemon.getWeight());
        String height = String.valueOf(pokemon.getHeight());
        String normal, shiny;

        if(name.contains("-")) {
            normal = "https://img.pokemondb.net/artwork/large/"+name+".jpg";
            shiny = "";
        } else {
            normal = pokemon.getSprites().getFront_default();
            shiny = pokemon.getSprites().getFront_shiny();
        }

        if(weight.length() < 2) {
            weight = "0" + weight.substring(0, weight.length() - 1) + "." + weight.substring(weight.length() - 1);
        } else {
            weight = weight.substring(0, weight.length() - 1) + "." + weight.substring(weight.length() - 1);
        }
        String weight2 = "Weight: " + weight + " kg";

        if(height.length() < 2) {
            height = "0" + height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        } else {
            height = height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        }
        String height2 = "Height: " + height + " m";

        tv_name = findViewById(R.id.tv_name);
        ImageView iv_normal = findViewById(R.id.img_normal);
        ImageView iv_shiny = findViewById(R.id.img_shiny);
        TextView tv_weight = findViewById(R.id.tv_weight);
        TextView tv_height = findViewById(R.id.tv_height);

        tv_name.setText(name2);
        Picasso.get().load(normal).resize(300,300).into(iv_normal);
        if(!shiny.equals("")) {
            Picasso.get().load(shiny).resize(300,300).into(iv_shiny);
        } else {
            iv_shiny.setVisibility(View.GONE);
        }
        tv_weight.setText(weight2);
        tv_height.setText(height2);
    }

    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void toAdd(View view) {
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
