package com.example.davidarisz.pokemonbuilder;

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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private DataRequest.Callback activity;
    private Gson gson = new Gson();

    public interface Callback {
        void gotData();
    }

    public DataRequest(Context context) {
        this.context = context;
    }

    // Makes a request to the api
    public void getData(DataRequest.Callback activity) {
        String name = SearchActivity.name;
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

        for (AbilityItem abilityItem : pokemon.getAbilities()) {
            System.out.println(
                    String.format("DAVID IS HOMO. Ohja %s heeft deze ability: %s", pokemon.getName(), abilityItem.getAbility().getName())
                    );
        }

        activity.gotData();
    }
}