package com.example.davidarisz.pokemonbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidarisz.pokemonbuilder.models.Pokemon;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchActivity extends AppCompatActivity implements DataRequest.Callback {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tv = findViewById(R.id.tvSelected);
        tv.setText("Adding: ");
        pokemonNames = getIntent().getStringArrayListExtra("namesTag");

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(SearchActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        tv = findViewById(R.id.tvSelected);
                        String adding = "Adding: " + name;
                        tv.setText(adding);

                        makeRequest();
                    }
                }).show();
            }
        });

        Toast.makeText(this, "after click", Toast.LENGTH_SHORT).show();

        if (name != null) {
            Toast.makeText(this, "name not null", Toast.LENGTH_SHORT).show();

        }
    }

    public void makeRequest () {
        DataRequest request = new DataRequest(this);
        request.getData(this);
    }

    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i).toString()));
        }

        return items;
    }

    public void gotData (Pokemon pokemon) {
        // asdf
    }
}
