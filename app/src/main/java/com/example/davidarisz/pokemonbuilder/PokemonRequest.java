package com.example.davidarisz.pokemonbuilder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokemonRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        void gotPokemon(ArrayList<String> pokemon);
    }

    public PokemonRequest(Context context) {
        this.context = context;
    }

    // Makes a request to the api
    public void getPokemon(Callback activity) {
        String url = "https://pokeapi.co/api/v2/pokemon/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

        this.activity = activity;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.getMessage() == null) {
            Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray values;
        ArrayList arrayList = new ArrayList();

        try {
            values = response.getJSONArray("results");

            for (int i = 0; i < values.length(); i++) {
                JSONObject object = values.getJSONObject(i);
                String pokemon = object.getString("name");
                Log.d("pokemonTag", pokemon);

//                arrayList.add(pokemon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
