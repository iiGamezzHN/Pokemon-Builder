package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class AbilityAdapter extends ArrayAdapter<AbilityData> {
    private ArrayList<AbilityData> abilities;

    public AbilityAdapter(Context context, int resource, int textViewResourceId, ArrayList<AbilityData> objects) {
        super(context, resource, textViewResourceId, objects);

        abilities = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_ability_row, parent, false);
        }

        AbilityData abilityData = getItem(position);

        String name = abilityData.getName();
        String description = abilityData.getEffect();
        boolean hidden = abilityData.isHidden();

        TextView tv_name = convertView.findViewById(R.id.tv_name_ability);
        TextView tv_description = convertView.findViewById(R.id.tv_description_ability);

        tv_name.setText(name);
        tv_description.setText(description);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_ability_row, parent, false);
        }

        AbilityData abilityData = getItem(position);

        String name = abilityData.getName();
        String description = abilityData.getEffect();
        boolean hidden = abilityData.isHidden();

        TextView tv_name = convertView.findViewById(R.id.tv_name_ability);
        TextView tv_description = convertView.findViewById(R.id.tv_description_ability);

        tv_name.setText(name);
        tv_description.setText(description);

        return convertView;
    }
}
