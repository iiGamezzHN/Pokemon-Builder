/*
Author: David Arisz

This Request asks for specific data om abilities from the api
 */

package com.example.davidarisz.pokemonbuilder.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.Classes.AbilityData;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.models.Ability;
import com.example.davidarisz.pokemonbuilder.models.Item;
import com.google.gson.Gson;

import org.json.JSONObject;

public class AbilityDataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private AbilityDataRequest.Callback activity;
    private Gson gson = new Gson();
    private String name;
    private boolean hidden;


    public interface Callback {
        void gotAbilityData(AbilityData abilityData);
    }


    // Constructor for the class
    public AbilityDataRequest(Context context, String name, boolean hidden) {
        this.context = context;
        this.name = name;
        this.hidden = hidden;
    }


    // Makes a request to the api
    public void getAbilityData(AbilityDataRequest.Callback activity) {
        String url = "https://pokeapi.co/api/v2/ability/"+name+"/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

        this.activity = activity;

    }


    // Catches the error if the request fails
    @Override
    public void onErrorResponse(VolleyError error) {

        if (error.getMessage() == null) {
            Toast.makeText(context, "Timeout error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Timeout error", Toast.LENGTH_SHORT).show();
        }
    }


    // Handles the response of the api request
    @Override
    public void onResponse(JSONObject response) {
        Ability ability = gson.fromJson(response.toString(), Ability.class);
        String effect = "";
        String name = ability.getName();
        String name2 = name.substring(0,1).toUpperCase() + name.substring(1);

        // Gets the effect of the ability
        for(Ability.EffectEntries effectEntries : ability.getEffect_entries()) {
            effect = effectEntries.getShort_effect();
        }

        AbilityData abilityData = new AbilityData(name2, effect, hidden);
        activity.gotAbilityData(abilityData);
    }
}