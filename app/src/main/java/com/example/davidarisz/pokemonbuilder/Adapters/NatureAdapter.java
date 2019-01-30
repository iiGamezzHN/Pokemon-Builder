/*
Author: David Arisz

NatureAdapter takes an arraylist of NatureData objects and adapts it to a Spinner
 */

package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.NatureData;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;

public class NatureAdapter extends ArrayAdapter<NatureData> {

    public NatureAdapter(Context context, int resource, int textViewResourceId, ArrayList<NatureData> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    // Set what data to show after selecting
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nature_row, parent, false);
        }

        NatureData natureData = getItem(position);

        String name = natureData.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String increased = natureData.getIncreased();
        String decreased = natureData.getDecreased();

        // Trim the strings for shorter description in the AutoCompleteTextView
        switch(increased) {
            case "attack":
                increased = "Att";
                break;
            case "defense":
                increased = "Def";
                break;
            case "special-attack":
                increased = "Spa";
                break;
            case "special-defense":
                increased = "Spd";
                break;
            case "speed":
                increased = "Sp";
                break;
        }

        switch(decreased) {
            case "attack":
                decreased = "Att";
                break;
            case "defense":
                decreased = "Def";
                break;
            case "special-attack":
                decreased = "Spa";
                break;
            case "special-defense":
                decreased = "Spd";
                break;
            case "speed":
                decreased = "Sp";
                break;
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name_nature);
        TextView tv_description = convertView.findViewById(R.id.tv_inc_dec_nature);

        tv_name.setText(name2);
        String description = "+ "+increased+"    - "+decreased;
        tv_description.setText(description);

        return convertView;
    }


    @Override
    // Set what data to show in dropdown
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nature_row, parent, false);
        }

        NatureData natureData = getItem(position);

        String name = natureData.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String increased = natureData.getIncreased();
        String decreased = natureData.getDecreased();

        // Trim the strings for shorter description in the AutoCompleteTextView
        switch(increased) {
            case "attack":
                increased = "Att";
                break;
            case "defense":
                increased = "Def";
                break;
            case "special-attack":
                increased = "Spa";
                break;
            case "special-defense":
                increased = "Spd";
                break;
            case "speed":
                increased = "Sp";
                break;
        }

        switch(decreased) {
            case "attack":
                decreased = "Att";
                break;
            case "defense":
                decreased = "Def";
                break;
            case "special-attack":
                decreased = "Spa";
                break;
            case "special-defense":
                decreased = "Spd";
                break;
            case "speed":
                decreased = "Sp";
                break;
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name_nature);
        TextView tv_description = convertView.findViewById(R.id.tv_inc_dec_nature);

        tv_name.setText(name2);
        String description = "+ "+increased+"    - "+decreased;
        tv_description.setText(description);

        return convertView;
    }
}
