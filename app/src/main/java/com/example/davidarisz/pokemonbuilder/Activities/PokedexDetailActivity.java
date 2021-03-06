/*
Author: David Arisz

PokedexDetailActivity is where you see the extra information on a pokemon that you've clicked on in
PokedexActivity. You can also use the button in the bottom right to go add the pokemon to your list
if you want to.
 */

package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.Classes.GetTypeColor;
import com.example.davidarisz.pokemonbuilder.R;
import com.example.davidarisz.pokemonbuilder.Requests.PokemonDataRequest;
import com.example.davidarisz.pokemonbuilder.models.HeldItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.example.davidarisz.pokemonbuilder.models.StatsItem;
import com.example.davidarisz.pokemonbuilder.models.TypesItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexDetailActivity extends AppCompatActivity implements PokemonDataRequest.Callback {
    private ArrayList pokemonNames;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_detail);

        // Set 'selected' color to current 'tab'
        Button button = findViewById(R.id.btn_pokedex_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab2));

        Intent intent = getIntent();
        name = intent.getStringExtra("nameTag");
        pokemonNames = intent.getStringArrayListExtra("namesTag");

        // Request api for data of selected pokemon
        PokemonDataRequest request = new PokemonDataRequest(PokedexDetailActivity.this, name);
        request.getPokemonData(this);

        FloatingActionButton fabButton = findViewById(R.id.floatingActionButton);
        fabButton.setOnClickListener(addPokemon);
    }


    // OnClickListener for floating action button to add pokemon you're currently on
    private View.OnClickListener addPokemon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(PokedexDetailActivity.this, "Adding pokemon", Toast.LENGTH_SHORT).show();

            String pass_name = name;
            String pass_name2 = pass_name.substring(0, 1).toLowerCase() + pass_name.substring(1);

            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            intent.putExtra("addName", pass_name2);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
        }
    };


    public void gotPokemonData(Pokemon pokemon) {
        String name = pokemon.getName();
        String name2;
        int id = pokemon.getId();

        // Set the id so that it always has 3 digits
        if (String.valueOf(id).length() == 1) {
            name2 = "#00" + id + " - " + name.substring(0, 1).toUpperCase() + name.substring(1);
        } else if (String.valueOf(id).length() == 2) {
            name2 = "#0" + id + " - " + name.substring(0, 1).toUpperCase() + name.substring(1);
        } else {
            name2 = "#" + id + " - " + name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        String weight = String.valueOf(pokemon.getWeight());
        String height = String.valueOf(pokemon.getHeight());
        String normal, shiny, hp, att, def, spa, spd, sp, type1, type2;
        hp = att = def = spa = spd = sp = type1 = type2 = "";

        // Names with - often don't have sprite links
        if (name.contains("-")) {
            normal = "https://img.pokemondb.net/artwork/large/" + name + ".jpg";
            shiny = "";
        } else {
            normal = pokemon.getSprites().getFront_default();
            shiny = pokemon.getSprites().getFront_shiny();
        }

        // Get all types of pokemon
        for (TypesItem typesItem : pokemon.getTypes()) {

            if (typesItem.getSlot() == 1) {
                type1 = typesItem.getType().getName().substring(0, 1).toUpperCase() + typesItem.getType().getName().substring(1);
            } else if (typesItem.getSlot() == 2) {
                type2 = typesItem.getType().getName().substring(0, 1).toUpperCase() + typesItem.getType().getName().substring(1);
            }
        }

        // Get all stats of pokemon
        for (StatsItem statsItem : pokemon.getStats()) {

            switch (statsItem.getStat().getName()) {
                case ("hp"):
                    hp = "<b>Hp:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
                case ("attack"):
                    att = "<b>Att:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
                case ("defense"):
                    def = "<b>Def:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
                case ("special-attack"):
                    spa = "<b>Spa:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
                case ("special-defense"):
                    spd = "<b>SpD:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
                case ("speed"):
                    sp = "<b>Sp:</b> " + String.valueOf(statsItem.getBase_stat());
                    break;
            }
        }

        // Formatting for weight
        if (weight.length() < 2) {
            weight = "0" + weight.substring(0, weight.length() - 1) + "." + weight.substring(weight.length() - 1);
        } else {
            weight = weight.substring(0, weight.length() - 1) + "." + weight.substring(weight.length() - 1);
        }

        String weight2 = "<b>Weight:</b> " + weight + " kg";

        // Formatting for height
        if (height.length() < 2) {
            height = "0" + height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        } else {
            height = height.substring(0, height.length() - 1) + "." + height.substring(height.length() - 1);
        }

        String height2 = "<b>Height:</b> " + height + " m";
        String item2 = "<b>Held item:</b> None";

        // Get helditems of pokemon
        for (HeldItem heldItem : pokemon.getHeld_items()) {
            String item = heldItem.getItem().getName();
            item2 = "<b>Held item:</b> " + item.substring(0, 1).toUpperCase() + item.substring(1);
        }

        String color1 = new GetTypeColor().ReturnColor(type1);
        String color2 = "";

        // If there is a 2nd type, get the color
        if (type2 != "") {
            color2 = new GetTypeColor().ReturnColor(type2);
        }

        TextView tv_name = findViewById(R.id.tv_name);
        ImageView iv_normal = findViewById(R.id.img_normal);
        ImageView iv_shiny = findViewById(R.id.img_shiny);
        TextView tv_type1 = findViewById(R.id.tv_type1_pokedex_detail);
        TextView tv_type2 = findViewById(R.id.tv_type2_pokedex_detail);
        TextView tv_weight = findViewById(R.id.tv_weight);
        TextView tv_height = findViewById(R.id.tv_height);
        TextView tv_hp = findViewById(R.id.tv_hp);
        TextView tv_att = findViewById(R.id.tv_att);
        TextView tv_def = findViewById(R.id.tv_def);
        TextView tv_spa = findViewById(R.id.tv_spa);
        TextView tv_spd = findViewById(R.id.tv_spd);
        TextView tv_sp = findViewById(R.id.tv_sp);
        TextView tv_item = findViewById(R.id.held_item);

        tv_name.setText(name2);
        Picasso.get().load(normal).resize(300, 300).into(iv_normal);

        // Remove shiny imageview of url is empty
        if (!shiny.equals("")) {
            Picasso.get().load(shiny).resize(300, 300).into(iv_shiny);
        } else {
            iv_shiny.setVisibility(View.GONE);
        }

        tv_weight.setText(Html.fromHtml(weight2));
        tv_height.setText(Html.fromHtml(height2));
        tv_type1.setText(type1);
        tv_type2.setText(type2);

        // Set the rounded shape and color 1st type textview
        GradientDrawable drawable = (GradientDrawable) tv_type1.getBackground();
        drawable.setColor(Color.parseColor(color1));

        // Set rounded shape and color for 2nd type textview, or set the visibility to gone
        if (color2 != "") {
            GradientDrawable drawable2 = (GradientDrawable) tv_type2.getBackground();
            drawable2.setColor(Color.parseColor(color2));
        } else {
            tv_type2.setVisibility(View.GONE);
        }

        // Use html in string so show bold text
        tv_hp.setText(Html.fromHtml(hp));
        tv_att.setText(Html.fromHtml(att));
        tv_def.setText(Html.fromHtml(def));
        tv_spa.setText(Html.fromHtml(spa));
        tv_spd.setText(Html.fromHtml(spd));
        tv_sp.setText(Html.fromHtml(sp));
        tv_item.setText(Html.fromHtml(item2));
    }


    // Go to list activity
    public void toList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);

        // Get rid of opening animation of new activity
        overridePendingTransition(0, 0);
    }


    // Go to add activity
    public void toAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putStringArrayListExtra("namesTag", pokemonNames);
        startActivity(intent);

        overridePendingTransition(0, 0);
    }


    // Go 'back' to pokedex activity
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
