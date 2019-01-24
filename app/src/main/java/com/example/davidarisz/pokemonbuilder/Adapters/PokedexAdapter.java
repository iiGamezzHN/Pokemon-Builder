package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexAdapter extends ArrayAdapter<String> {
    private ArrayList<String> pokemonNames;
    private String name2;

    public PokedexAdapter(Context context, int resource, ArrayList<String> names) {
        super(context, resource, names);
        this.pokemonNames = names;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokedex_items, parent, false);
        }

        String name = getItem(position);
        int id = position + 1;
        if(String.valueOf(position).length() == 1) {
            name2 = "#00"+id+" - "+name;
        } else if(String.valueOf(position).length() == 2) {
            name2 = "#0"+id+" - "+name;
        } else {
            name2 = "#"+id+" - "+name;
        }

        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+id+".png";

        TextView textView = convertView.findViewById(R.id.tv_name);
        textView.setText(name2);

        ImageView imageView = convertView.findViewById(R.id.pd_detail_image);
        Picasso.get().load(url).resize(300,300).into(imageView);

        return convertView;
    }
}
