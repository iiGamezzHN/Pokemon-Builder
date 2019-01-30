/*
Author: David Arisz

ListAdapter takes a Cursor object from a database and adapts it to a ListView
 */

package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.GetTypeColor;
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
        String type1 = cursor.getString(cursor.getColumnIndex("type1"));
        String type2 = cursor.getString(cursor.getColumnIndex("type2"));
        String color1 = new GetTypeColor().ReturnColor(type1);
        String color2 = "";

        // If there is a 2nd type, get the color
        if (type2 != "") {
            color2 = new GetTypeColor().ReturnColor(type2);
        }

        ImageView list_picture = view.findViewById(R.id.img_picture_list);
        TextView list_name = view.findViewById(R.id.tv_name_list);
        TextView list_item = view.findViewById(R.id.tv_item_list);
        TextView list_type1 = view.findViewById(R.id.tv_type1_adapter);
        TextView list_type2 = view.findViewById(R.id.tv_type2_adapter);

        list_name.setText(name);
        list_item.setText(item);
        Picasso.get().load(url)
                .resize(300,300)
                .centerCrop()
                .into(list_picture);
        list_type1.setText(type1);
        list_type2.setText(type2);

        // Set the rounded shape and color 1st type textview
        GradientDrawable drawable = (GradientDrawable)list_type1.getBackground();
        drawable.setColor(Color.parseColor(color1));

        // Set rounded shape and color for 2nd type textview, or set the visibility to gone
        if (color2 != "") {
            GradientDrawable drawable2 = (GradientDrawable)list_type2.getBackground();
            drawable2.setColor(Color.parseColor(color2));
        } else {
            list_type2.setVisibility(View.GONE);
        }
    }
}
