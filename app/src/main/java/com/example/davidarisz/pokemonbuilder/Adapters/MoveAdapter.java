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
            tv_name.setText(name2);
            tv_power.setText("Power: "+String.valueOf(moveData.getPower()));
            tv_accuracy.setText("Accuracy: "+String.valueOf(moveData.getAccuracy()));
            tv_pp.setText("PP: "+String.valueOf(moveData.getPp()));
            tv_category.setText("Category: "+moveData.getCategory());
            tv_effect.setText("Description: "+moveData.getEffect());
            tv_type.setText("Type: "+moveData.getType());
        }

        return convertView;
    }

    private Filter movesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<MoveData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(moves);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((MoveData) resultValue).getName();
        }
    };
}
