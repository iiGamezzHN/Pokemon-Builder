/*
Author: David Arisz

MoveAdapter takes an arraylist of MoveData objects and adapts it to an AutoCompleteTextView
 */

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

import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.R;

import java.util.ArrayList;
import java.util.List;

// The basis of this code was taken from: https://codinginflow.com/tutorials/android/custom-autocompletetextview/part-2-adapter
public class MoveAdapter extends ArrayAdapter<MoveData> {
    private List<MoveData> moves;

    public MoveAdapter(@NonNull Context context, @NonNull List<MoveData> moves) {
        super(context, 0, moves);
        this.moves = new ArrayList<>(moves);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return movesFilter;
    }


    @NonNull
    @Override
    // Set what data to show in dropdown
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_move_row, parent, false
            );
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name_move);
        TextView tv_power = convertView.findViewById(R.id.tv_power_move);
        TextView tv_accuracy = convertView.findViewById(R.id.tv_accuracy_move);
        TextView tv_pp = convertView.findViewById(R.id.tv_pp_move);
        TextView tv_category = convertView.findViewById(R.id.tv_category_move);
        TextView tv_effect = convertView.findViewById(R.id.tv_effect_move);
        TextView tv_type = convertView.findViewById(R.id.tv_type_move);

        MoveData moveData = getItem(position);

        if (moveData != null) {
            String name = moveData.getName();
            String name2 = name.substring(0,1).toUpperCase() + name.substring(1);
            String power = "Power: "+String.valueOf(moveData.getPower());
            String accuracy = "Accuracy: "+String.valueOf(moveData.getAccuracy());
            String pp = "PP: "+String.valueOf(moveData.getPp());
            String category = "Category: "+moveData.getCategory();
            String effect = "Description: "+moveData.getEffect();
            String type = "Type: "+moveData.getType();

            tv_name.setText(name2);
            tv_power.setText(power);
            tv_accuracy.setText(accuracy);
            tv_pp.setText(pp);
            tv_category.setText(category);
            tv_effect.setText(effect);
            tv_type.setText(type    );
        }

        return convertView;
    }


    // Make a filter that gives search results
    private Filter movesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<MoveData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(moves);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                // Give items that match what you search for
                for (MoveData move : moves) {
                    if (move.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(move);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }


        // Show filter results
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }


        // Give back name when selecting an option
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((MoveData) resultValue).getName();
        }
    };
}
