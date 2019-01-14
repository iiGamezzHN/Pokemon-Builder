package com.example.davidarisz.pokemonbuilder;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

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
        Log.d("urlTag", url);

        TextView list_name = view.findViewById(R.id.tv_name);
        TextView list_item = view.findViewById(R.id.tv_item);
        ImageView list_picture = view.findViewById(R.id.img_picture);

        list_name.setText(name);
        list_item.setText(item);
        Picasso.get().load(url).resize(300,300).centerCrop().into(list_picture);
    }
}
