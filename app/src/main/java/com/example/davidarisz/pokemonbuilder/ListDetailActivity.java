package com.example.davidarisz.pokemonbuilder;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListDetailActivity extends AppCompatActivity {
    ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Intent intent = getIntent();
        pokemonNames = intent.getStringArrayListExtra("namesTag");

        SavedPokemon savedPokemon = (SavedPokemon) getIntent().getSerializableExtra("savedTag");

        String name = savedPokemon.getName();
        String url = savedPokemon.getUrl();
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

        TextView tv_name = findViewById(R.id.tv_name);
        ImageView img_normal = findViewById(R.id.img_normal);
        ImageView img_shiny = findViewById(R.id.img_shiny);
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

        String url2 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/3.png";

        tv_name.setText(name);
        Picasso.get().load(url).into(img_normal);
        Picasso.get().load(url2).into(img_shiny);
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
}
