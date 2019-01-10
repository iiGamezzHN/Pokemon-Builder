package com.example.davidarisz.pokemonbuilder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.models.AbilityItem;
import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.example.davidarisz.pokemonbuilder.models.Sprites;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private ArrayList pokemonNames;
    private TextView tv;
    public static String name;
    private View rootView;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the RequestQueue.
        String url = "https://pokeapi.co/api/v2/pokemon/";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray values;
                        ArrayList arrayList = new ArrayList();

                        try {
                            values = response.getJSONArray("results");
                            for (int i = 0; i < values.length(); i++) {
                                JSONObject object = values.getJSONObject(i);
                                String pokemon = object.getString("name");
                                arrayList.add(pokemon);
                            }
                            pokemonNames = arrayList;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() == null) {
                            Toast.makeText(getContext(), "Timeout error :(", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Timeout error :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        queue.add(jsObjRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        tv = rootView.findViewById(R.id.tvSelected);
        tv.setText("Adding: ");

        rootView.findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SimpleSearchDialogCompat(getContext(), "Search...", "What are you looking for...",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        baseSearchDialogCompat.dismiss();
                        name = searchable.getTitle();
                        tv = rootView.findViewById(R.id.tvSelected);
                        String adding = "Adding: " + name;
                        tv.setText(adding);

                        detailsRequest();
                    }
                }).show();
            }
        });

        return rootView;
    }

    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();

        for (int i = 0; i < pokemonNames.size(); i++) {
            items.add(new SearchModel(pokemonNames.get(i).toString()));
        }

        return items;
    }

    public void detailsRequest() {
        String url = "https://pokeapi.co/api/v2/pokemon/"+name+"/";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final Gson gson = new Gson();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Pokemon pokemon = gson.fromJson(response.toString(), Pokemon.class);

                        ArrayList<String> moves = new ArrayList<String>();
                        for (MoveItem moveItem : pokemon.getMoves()) {
                            moves.add(moveItem.getMove().getName());
                        }

                        Spinner move1 = rootView.findViewById(R.id.move1);
                        Spinner move2 = rootView.findViewById(R.id.move2);
                        Spinner move3 = rootView.findViewById(R.id.move3);
                        Spinner move4 = rootView.findViewById(R.id.move4);

                        //Creating the ArrayAdapter instance having the country list
                        ArrayAdapter arrayAdapter = new ArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, moves);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        move1.setAdapter(arrayAdapter);
                        move2.setAdapter(arrayAdapter);
                        move3.setAdapter(arrayAdapter);
                        move4.setAdapter(arrayAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() == null) {
                            Toast.makeText(getContext(), "Timeout error :(", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Timeout error :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        queue.add(jsObjRequest);
    }
}
