package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
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

    public PokedexAdapter(Context context, int resource, ArrayList<String> names) {
        super(context, resource, names);
        this.pokemonNames = names;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokedex_items, parent, false);
        }

        String name = getItem(position);
        String url = "https://raw.githubuserco…r/sprites/pokemon/"+name+".png";

        TextView textView = convertView.findViewById(R.id.tv_name);
        textView.setText(name); // TODO, Show pokeball icon/ id in front of name

        ImageView imageView = convertView.findViewById(R.id.pd_detail_image);
        Picasso.get().load(url).into(imageView);

        return convertView;
    }
}
