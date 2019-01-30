/*
Author: David Arisz

This Request asks for specific data om items from the api
 */

package com.example.davidarisz.pokemonbuilder.Requests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.models.Item;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ItemDataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private ItemDataRequest.Callback activity;
    private Gson gson = new Gson();
    private String name;


    public interface Callback {
        void gotItemData(ItemData itemData);
    }


    // Constructor for the class
    public ItemDataRequest(Context context, String name) {
        this.context = context;
        this.name = name.substring(0,1).toLowerCase() + name.substring(1);;
    }

    // Makes a request to the api
    public void getItemData(ItemDataRequest.Callback activity) {
        String url = "https://pokeapi.co/api/v2/item/"+name+"/";
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
        Item item = gson.fromJson(response.toString(), Item.class);
        String effect = "";

        // Gets the effect of the item
        for(Item.EffectEntries effectEntries : item.getEffect_entries()) {
            effect = effectEntries.getShort_effect();
        }

        String name = item.getName();
        String sprite = item.getSprite();
        ItemData itemData = new ItemData(name, effect, sprite);

        activity.gotItemData(itemData);
    }
}