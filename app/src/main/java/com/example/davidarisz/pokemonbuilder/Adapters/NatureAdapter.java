package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.Classes.NatureData;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;

public class NatureAdapter extends ArrayAdapter<NatureData> {
    private ArrayList<NatureData> natures;

    public NatureAdapter(Context context, int resource, int textViewResourceId, ArrayList<NatureData> objects) {
        super(context, resource, textViewResourceId, objects);

        natures = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nature_row, parent, false);
        }

        NatureData natureData = getItem(position);

        String name = natureData.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String increased = natureData.getIncreased();
        String decreased = natureData.getDecreased();

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
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nature_row, parent, false);
        }

        NatureData natureData = getItem(position);

        String name = natureData.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String increased = natureData.getIncreased();
        String decreased = natureData.getDecreased();

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
