package com.example.davidarisz.pokemonbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
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


public class ItemArrayAdapter2 extends ArrayAdapter<ItemData> {
    private List<ItemData> countryListFull;

    public ItemArrayAdapter2(@NonNull Context context, @NonNull List<ItemData> countryList) {
        super(context, 0, countryList);
        countryListFull = new ArrayList<>(countryList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_item_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tv_name_item);
        TextView textViewDescription = convertView.findViewById(R.id.tv_description_item);

        ItemData countryItem = getItem(position);

        if (countryItem != null) {
            textViewName.setText(countryItem.getName());
            textViewDescription.setText(countryItem.getEffect());
        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ItemData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemData item : countryListFull) {
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
            return ((ItemData) resultValue).getName();
        }
    };
}
