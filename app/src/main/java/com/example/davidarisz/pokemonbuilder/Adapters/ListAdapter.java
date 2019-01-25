package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;
import com.squareup.picasso.Picasso;

public class ListAdapter extends ResourceCursorAdapter {
    public ListAdapter(Context context,Cursor c) {
        super(context, R.layout.list_items, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String item = cursor.getString(cursor.getColumnIndex("item"));
        String url = cursor.getString(cursor.getColumnIndex("url"));

        TextView list_name = view.findViewById(R.id.tv_name_list); // TODO, show boosts and type
        list_name.setBackgroundColor(Color.parseColor("#999999"));
        TextView list_item = view.findViewById(R.id.tv_item_list);
        list_item.setBackgroundColor(Color.parseColor("#999999"));
        ImageView list_picture = view.findViewById(R.id.img_picture_list);

        list_name.setText(name);
        list_item.setText(item);
        Picasso.get().load(url).resize(300,300).centerCrop().into(list_picture);
    }
}
