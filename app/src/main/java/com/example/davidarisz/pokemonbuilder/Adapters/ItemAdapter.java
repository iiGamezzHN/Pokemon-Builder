package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;

public class ItemAdapter extends ResourceCursorAdapter implements Filterable {

    public ItemAdapter(Context context, Cursor c) {
        super(context, R.layout.autocomplete_item_row, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String effect = cursor.getString(cursor.getColumnIndex("effect"));
        String effect2 = effect.substring(6);
        String sprite = cursor.getString(cursor.getColumnIndex("sprite"));

//        ImageView img_sprite = view.findViewById(R.id.img_item);
        TextView tv_name = view.findViewById(R.id.tv_name_item);
        TextView tv_description = view.findViewById(R.id.tv_description_item);

        Log.d("imageTag", sprite);

        tv_name.setText(name2);
        tv_description.setText(effect2);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.autocomplete_item_row, parent, false);
        bindView(v, context, cursor);
        return v;
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("name"));
    }
}
