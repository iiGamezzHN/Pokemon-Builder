/*
Author: David Arisz

PokedexAdapter takes an arraylist of strings and adapts it to a ListView
 */

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

    public PokedexAdapter(Context context, int resource, ArrayList<String> names) {
        super(context, resource, names);
    }

    // Adapt the arraylist to the listview
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokedex_items, parent, false);
        }

        String name = getItem(position);
        String name2;
        int id = position + 1;

        // Get the url for the pokemon image
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+id+".png";

        // Set the id so that it always has 3 digits
        if(String.valueOf(position).length() == 1) {
            name2 = "#00"+id+" - "+name;
        } else if(String.valueOf(position).length() == 2) {
            name2 = "#0"+id+" - "+name;
        } else {
            name2 = "#"+id+" - "+name;
        }

        TextView textView = convertView.findViewById(R.id.tv_name);
        ImageView imageView = convertView.findViewById(R.id.pd_detail_image);

        textView.setText(name2);
        Picasso.get()
                .load(url)
                .resize(300,300)
                .into(imageView);

        return convertView;
    }
}
