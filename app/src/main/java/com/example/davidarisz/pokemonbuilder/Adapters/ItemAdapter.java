package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.R;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends ResourceCursorAdapter {

    public ItemAdapter(Context context, Cursor c) {
        super(context, R.layout.spinner_item_row, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
        String effect = cursor.getString(cursor.getColumnIndex("effect"));
        String effect2 = effect.substring(6);
        String sprite = cursor.getString(cursor.getColumnIndex("sprite"));

//        ImageView img_sprite = view.findViewById(R.id.img_item);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_description = view.findViewById(R.id.tv_description);

        Log.d("imageTag", sprite);

//        Picasso.get().load(sprite).resize(300,300).centerCrop().into(img_sprite);
        tv_name.setText(name2);
        tv_description.setText(effect2);
    }
}
