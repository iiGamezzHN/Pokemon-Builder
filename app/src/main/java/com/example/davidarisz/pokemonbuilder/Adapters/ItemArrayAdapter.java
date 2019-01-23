package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<ItemData> {
    private List<ItemData> newItems;
    Filter filter;

    public ItemArrayAdapter(Context context, List<ItemData> items) {
        super(context, 0, items);
        newItems = items;
        Log.d("adapterTag", "nr of items " + newItems.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_item_row, parent, false
            );
        }
        ItemData itemData = newItems.get(position);
//        Log.d("adapterTag", "getView: " + cursor.getCount());
        String name = itemData.getName();
        Log.d("adapterTag", "name: " + name);
        String name2 = name.substring(0, 1).toUpperCase() + name.substring(1);
//        String effect = cursor.getString(cursor.getColumnIndex("effect"));
//        String effect2 = effect.substring(6);
//        String sprite = cursor.getString(cursor.getColumnIndex("sprite"));
//
////        ImageView img_sprite = view.findViewById(R.id.img_item);
        TextView tv_name = convertView.findViewById(R.id.tv_name_item);
//        TextView tv_description = convertView.findViewById(R.id.tv_description_item);

        tv_name.setText(name2);
//        tv_description.setText(effect2);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }


    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("adapterTag", "is used");
            FilterResults results = new FilterResults();
            List<ItemData> suggestion = new ArrayList<>();


            suggestion.addAll(newItems);
            if (constraint == null || constraint.length() == 0) {
                suggestion.addAll(newItems);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemData itemData : newItems) {
                    if (itemData.getName().contains(filterPattern)) {
                        suggestion.add(itemData);
                    }
                }
            }

            Log.d("adapterTag", "result count " + results.count);
            Log.d("adapterTag", "sug count " + suggestion.size());
            results.values = suggestion;
            results.count = suggestion.size();

            clear();
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
//
//            Log.d("adapterTag", "publishResults: " + results.toString());
//            Log.d("adapterTag", "publishResults: " + results.values);
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ItemData) resultValue).getName();
//            return "hoi";
        }
    }

}

