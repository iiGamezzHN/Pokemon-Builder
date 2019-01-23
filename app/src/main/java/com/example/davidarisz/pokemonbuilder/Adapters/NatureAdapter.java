package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;

public class NatureAdapter extends ResourceCursorAdapter {

    public NatureAdapter(Context context, Cursor c) {
        super(context, R.layout.spinner_nature_row, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String increased = cursor.getString(cursor.getColumnIndex("increased"));
        String decreased = cursor.getString(cursor.getColumnIndex("decreased"));

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

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_description = view.findViewById(R.id.tv_inc_dec);

        tv_name.setText(name2);
        String description = "+ "+increased+"    - "+decreased;
        tv_description.setText(description);
    }
}
