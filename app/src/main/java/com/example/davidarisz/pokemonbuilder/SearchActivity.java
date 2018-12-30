package com.example.davidarisz.pokemonbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchActivity extends AppCompatActivity {
    private ArrayList pokemonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        pokemonNames = getIntent().getStringArrayListExtra("pokemonNames");

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(SearchActivity.this, "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        Toast.makeText(SearchActivity.this, ""+searchable.getTitle(), Toast.LENGTH_SHORT).show();
                        baseSearchDialogCompat.dismiss();
                    }
                }).show();
            }
        });
    }

    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i <= pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i).toString()));
        }

        return items;
    }
}
