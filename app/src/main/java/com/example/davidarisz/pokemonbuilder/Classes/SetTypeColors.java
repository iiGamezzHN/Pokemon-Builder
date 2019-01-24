package com.example.davidarisz.pokemonbuilder.Classes;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Activities.PokedexDetailActivity;
import com.example.davidarisz.pokemonbuilder.R;

public class SetTypeColors { // TODO, ask if this is a good way to avoid huge walls of text
    private Context context;
    private String type1;
    private String type2;

    public SetTypeColors(Context context, String type1, String type2) {
        this.context = context;
        this.type1 = type1;
        this.type2 = type2;
        setColors();
    }

    public void setColors() {
        TextView tv_type1 = (TextView) ((PokedexDetailActivity)context).findViewById(R.id.type1);
        TextView tv_type2 = ((PokedexDetailActivity)context).findViewById(R.id.type2);

        if(type1.equals("Normal")) {
        } else if(type1.equals("Fire")) {
            tv_type1.setBackgroundColor(Color.parseColor("#f08030"));
        } else if(type1.equals("Water")) {
            tv_type1.setBackgroundColor(Color.parseColor("#6890f0"));
        } else if(type1.equals("Grass")) {
            tv_type1.setBackgroundColor(Color.parseColor("#78c850"));
        } else if(type1.equals("Electric")) {
            tv_type1.setBackgroundColor(Color.parseColor("#f8d030"));
        } else if(type1.equals("Ice")) {
            tv_type1.setBackgroundColor(Color.parseColor("#98d8d8"));
        } else if(type1.equals("Fighting")) {
            tv_type1.setBackgroundColor(Color.parseColor("#c03028"));
        } else if(type1.equals("Poison")) {
            tv_type1.setBackgroundColor(Color.parseColor("#a040a0"));
        } else if(type1.equals("Ground")) {
            tv_type1.setBackgroundColor(Color.parseColor("#e0c068"));
        } else if(type1.equals("Flying")) {
            tv_type1.setBackgroundColor(Color.parseColor("#a890f0"));
        } else if(type1.equals("Psychic")) {
            tv_type1.setBackgroundColor(Color.parseColor("#f85888"));
        } else if(type1.equals("Bug")) {
            tv_type1.setBackgroundColor(Color.parseColor("#a8b820"));
        } else if(type1.equals("Rock")) {
            tv_type1.setBackgroundColor(Color.parseColor("#b8a038"));
        } else if(type1.equals("Ghost")) {
            tv_type1.setBackgroundColor(Color.parseColor("#705898"));
        } else if(type1.equals("Dark")) {
            tv_type1.setBackgroundColor(Color.parseColor("#705848"));
        } else if(type1.equals("Dragon")) {
            tv_type1.setBackgroundColor(Color.parseColor("#7038f8"));
        } else if(type1.equals("Steel")) {
            tv_type1.setBackgroundColor(Color.parseColor("#b8b8d0"));
        } else if(type1.equals("Fairy")) {
            tv_type1.setBackgroundColor(Color.parseColor("#f0b6bc"));
        }

        if(type2.equals("Normal")) {
        } else if(type2.equals("Fire")) {
            tv_type2.setBackgroundColor(Color.parseColor("#f08030"));
        } else if(type2.equals("Water")) {
            tv_type2.setBackgroundColor(Color.parseColor("#6890f0"));
        } else if(type2.equals("Grass")) {
            tv_type2.setBackgroundColor(Color.parseColor("#78c850"));
        } else if(type2.equals("Electric")) {
            tv_type2.setBackgroundColor(Color.parseColor("#f8d030"));
        } else if(type2.equals("Ice")) {
            tv_type2.setBackgroundColor(Color.parseColor("#98d8d8"));
        } else if(type2.equals("Fighting")) {
            tv_type2.setBackgroundColor(Color.parseColor("#c03028"));
        } else if(type2.equals("Poison")) {
            tv_type2.setBackgroundColor(Color.parseColor("#a040a0"));
        } else if(type2.equals("Ground")) {
            tv_type2.setBackgroundColor(Color.parseColor("#e0c068"));
        } else if(type2.equals("Flying")) {
            tv_type2.setBackgroundColor(Color.parseColor("#a890f0"));
        } else if(type2.equals("Psychic")) {
            tv_type2.setBackgroundColor(Color.parseColor("#f85888"));
        } else if(type2.equals("Bug")) {
            tv_type2.setBackgroundColor(Color.parseColor("#a8b820"));
        } else if(type2.equals("Rock")) {
            tv_type2.setBackgroundColor(Color.parseColor("#b8a038"));
        } else if(type2.equals("Ghost")) {
            tv_type2.setBackgroundColor(Color.parseColor("#705898"));
        } else if(type2.equals("Dark")) {
            tv_type2.setBackgroundColor(Color.parseColor("#705848"));
        } else if(type2.equals("Dragon")) {
            tv_type2.setBackgroundColor(Color.parseColor("#7038f8"));
        } else if(type2.equals("Steel")) {
            tv_type2.setBackgroundColor(Color.parseColor("#b8b8d0"));
        } else if(type2.equals("Fairy")) {
            tv_type2.setBackgroundColor(Color.parseColor("#f0b6bc"));
        }
    }
}
