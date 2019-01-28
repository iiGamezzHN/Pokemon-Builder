package com.example.davidarisz.pokemonbuilder.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.GetTypeColor;
import com.example.davidarisz.pokemonbuilder.Classes.SavedPokemon;
import com.example.davidarisz.pokemonbuilder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListDetailActivity extends AppCompatActivity {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        Intent intent = getIntent();
        pokemonNames = intent.getStringArrayListExtra("namesTag");
        SavedPokemon savedPokemon = (SavedPokemon) getIntent().getSerializableExtra("savedTag");

        String name = savedPokemon.getName();
        String url = savedPokemon.getUrl();
        String url_shiny = savedPokemon.getUrl_shiny();
        String type1 = savedPokemon.getType1();
        String type2 = savedPokemon.getType2();
        String gender = savedPokemon.getGender();
        String item = savedPokemon.getItem();
        String ability = savedPokemon.getAbility();
        String move1 = savedPokemon.getMove1();
        String move2 = savedPokemon.getMove2();
        String move3 = savedPokemon.getMove3();
        String move4 = savedPokemon.getMove4();
        String nature = savedPokemon.getNature();
        int hp_iv = savedPokemon.getHp_iv();
        int att_iv = savedPokemon.getAtt_iv();
        int def_iv = savedPokemon.getDef_iv();
        int spa_iv = savedPokemon.getSpa_iv();
        int spd_iv = savedPokemon.getSpd_iv();
        int sp_iv = savedPokemon.getSp_iv();
        int hp_ev = savedPokemon.getHp_ev();
        int att_ev = savedPokemon.getAtt_ev();
        int def_ev = savedPokemon.getDef_ev();
        int spa_ev = savedPokemon.getSpa_ev();
        int spd_ev = savedPokemon.getSpd_ev();
        int sp_ev = savedPokemon.getSp_ev();

        String color1 = new GetTypeColor().ReturnColor(type1);

        String color2 = "";

        // If there is a 2nd type, get the color
        if (type2 != "") {
            color2 = new GetTypeColor().ReturnColor(type2);
        }

        TextView tv_name = findViewById(R.id.tv_name);
        ImageView img_normal = findViewById(R.id.img_normal);
        ImageView img_shiny = findViewById(R.id.img_shiny);
        TextView tv_type1 = findViewById(R.id.tv_type1_list_detail);
        TextView tv_type2 = findViewById(R.id.tv_type2_list_detail);
        TextView tv_gender = findViewById(R.id.tv_gender);
        TextView tv_item = findViewById(R.id.tv_item);
        TextView tv_ability = findViewById(R.id.tv_ability);
        TextView tv_move1 = findViewById(R.id.tv_move1);
        TextView tv_move2 = findViewById(R.id.tv_move2);
        TextView tv_move3 = findViewById(R.id.tv_move3);
        TextView tv_move4 = findViewById(R.id.tv_move4);
        TextView tv_nature = findViewById(R.id.tv_nature);
        TextView tv_hp_iv = findViewById(R.id.tv_hp_iv);
        TextView tv_att_iv = findViewById(R.id.tv_att_iv);
        TextView tv_def_iv = findViewById(R.id.tv_def_iv);
        TextView tv_spa_iv = findViewById(R.id.tv_spa_iv);
        TextView tv_spd_iv = findViewById(R.id.tv_spd_iv);
        TextView tv_sp_iv = findViewById(R.id.tv_sp_iv);
        TextView tv_hp_ev = findViewById(R.id.tv_hp_ev);
        TextView tv_att_ev = findViewById(R.id.tv_att_ev);
        TextView tv_def_ev = findViewById(R.id.tv_def_ev);
        TextView tv_spa_ev = findViewById(R.id.tv_spa_ev);
        TextView tv_spd_ev = findViewById(R.id.tv_spd_ev);
        TextView tv_sp_ev = findViewById(R.id.tv_sp_ev);

        tv_name.setText(name);
        Picasso.get().load(url).into(img_normal);

        if (url_shiny != "") {
            Picasso.get().load(url_shiny).into(img_shiny);
        } else {
            img_shiny.setVisibility(View.GONE);
        }

        tv_type1.setText(type1);

        // Set the rounded shape color to type color
        GradientDrawable drawable = (GradientDrawable)tv_type1.getBackground();
        drawable.setColor(Color.parseColor(color1));

        // Set background color for 2nd type, or set the visibility to gone
        if (color2 != "") {
            GradientDrawable drawable2 = (GradientDrawable)tv_type2.getBackground();
            drawable2.setColor(Color.parseColor(color2));
        } else {
            tv_type2.setVisibility(View.GONE);
        }

        tv_type2.setText(type2);
        tv_gender.setText(gender);
        tv_item.setText(item);
        tv_ability.setText(ability);
        tv_move1.setText(move1);
        tv_move2.setText(move2);
        tv_move3.setText(move3);
        tv_move4.setText(move4);
        tv_nature.setText(nature);
        tv_hp_ev.setText(String.valueOf(hp_ev));
        tv_att_ev.setText(String.valueOf(att_ev));
        tv_def_ev.setText(String.valueOf(def_ev));
        tv_spa_ev.setText(String.valueOf(spa_ev));
        tv_spd_ev.setText(String.valueOf(spd_ev));
        tv_sp_ev.setText(String.valueOf(sp_ev));
        tv_hp_iv.setText(String.valueOf(hp_iv));
        tv_att_iv.setText(String.valueOf(att_iv));
        tv_def_iv.setText(String.valueOf(def_iv));
        tv_spa_iv.setText(String.valueOf(spa_iv));
        tv_spd_iv.setText(String.valueOf(spd_iv));
        tv_sp_iv.setText(String.valueOf(sp_iv));

        Button button = findViewById(R.id.btn_list_tab);
        button.setBackgroundColor(getResources().getColor(R.color.selectedTab));
    }

    public void toList(View view) {
        if(pokemonNames != null) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void toAdd(View view) {
        if(pokemonNames != null) {
            Intent intent = new Intent(this, AddActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void toPokedex(View view) {
        if(pokemonNames != null) {
            Intent intent = new Intent(this, PokedexActivity.class);
            intent.putStringArrayListExtra("namesTag", pokemonNames);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}
