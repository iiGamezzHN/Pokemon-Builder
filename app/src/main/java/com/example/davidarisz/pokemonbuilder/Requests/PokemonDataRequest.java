package com.example.davidarisz.pokemonbuilder.Requests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.models.AbilityItem;
import com.example.davidarisz.pokemonbuilder.models.MoveItem;
import com.example.davidarisz.pokemonbuilder.models.Pokemon;
import com.example.davidarisz.pokemonbuilder.models.Sprites;
import com.example.davidarisz.pokemonbuilder.models.StatsItem;
import com.google.gson.Gson;

import org.json.JSONObject;

public class PokemonDataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private PokemonDataRequest.Callback activity;
    private Gson gson = new Gson();
    private String name;

    public interface Callback {
        void gotPokemonData(Pokemon pokemon);
    }

    public PokemonDataRequest(Context context, String name) {
        this.context = context;
        this.name = name.substring(0,1).toLowerCase() + name.substring(1);;
    }

    // Makes a request to the api
    public void getPokemonData(PokemonDataRequest.Callback activity) {
        String url = "https://pokeapi.co/api/v2/pokemon/"+name+"/";
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
        Pokemon pokemon = gson.fromJson(response.toString(), Pokemon.class);

        for (StatsItem statsItem : pokemon.getStats()) {
            Log.d("statTag", ""+statsItem.getBase_stat());
        }

        activity.gotPokemonData(pokemon);
    }
}