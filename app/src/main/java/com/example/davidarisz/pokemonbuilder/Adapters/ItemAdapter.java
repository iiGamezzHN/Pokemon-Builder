package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.R;

// The basis of this code was taken from: https://codinginflow.com/tutorials/android/custom-autocompletetextview/part-2-adapter
public class ItemAdapter extends ArrayAdapter<ItemData> {
    private List<ItemData> items;

    public ItemAdapter(@NonNull Context context, @NonNull List<ItemData> items) {
        super(context, 0, items);
        this.items = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_item_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tv_name_item);
        TextView textViewDescription = convertView.findViewById(R.id.tv_description_item);

        ItemData item = getItem(position);

        if (item != null) {
            String name = item.getName();
            String name2 = name.substring(0,1).toUpperCase() + name.substring(1);

            textViewName.setText(name2);
            textViewDescription.setText(item.getEffect());
        }

        return convertView;
    }

    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ItemData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(items);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemData item : items) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String name = ((ItemData) resultValue).getName();
            String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
            return name2;
        }
    };
}
