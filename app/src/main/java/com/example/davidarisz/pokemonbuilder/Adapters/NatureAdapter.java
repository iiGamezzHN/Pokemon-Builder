package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NatureAdapter extends ArrayAdapter {
    public NatureAdapter(Context context, ArrayList<ArrayList<String>> natureData) {
        super(context, 0, natureData);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data item for this position
        ArrayList data = (ArrayList) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_nature_row, parent, false);
        }

        // Lookup view for data population
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_description = (TextView) convertView.findViewById(R.id.tv_inc_dec);

        // Populate the data into the template view using the data object
        String name = data.get(0).toString();
        String increase = data.get(1).toString();
        String decrease = data.get(2).toString();
        String description = "+ " + increase + " - " + decrease;

        tv_name.setText(name);
        tv_description.setText(description);

        // Return the completed view to render on screen
        return convertView;
    }
}
