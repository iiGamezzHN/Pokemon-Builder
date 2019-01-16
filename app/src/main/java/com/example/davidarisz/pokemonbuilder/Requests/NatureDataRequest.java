package com.example.davidarisz.pokemonbuilder.Requests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.models.Item;
import com.example.davidarisz.pokemonbuilder.models.Nature;
import com.google.gson.Gson;

import org.json.JSONObject;

public class NatureDataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private NatureDataRequest.Callback activity;
    private Gson gson = new Gson();
    private String name;

    public interface Callback {
        void gotNatureData(Nature nature);
    }

    public NatureDataRequest(Context context, String name) {
        this.context = context;
        this.name = name.substring(0,1).toLowerCase() + name.substring(1);
    }

    // Makes a request to the api
    public void getNatureData(NatureDataRequest.Callback activity) {
        String url = "https://pokeapi.co/api/v2/nature/"+name+"/";
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
        Nature nature = gson.fromJson(response.toString(), Nature.class);

        for(Nature.DecreasedStat decreasedStat : nature.getDecreased_stat()) {
            Log.d("decreasedTag", decreasedStat.getName());
        }

        for(Nature.IncreasedStat increasedStat : nature.getIncreased_stat()) {
            Log.d("decreasedTag", increasedStat.getName());
        }

        String name = nature.getName();

        activity.gotNatureData(nature);
    }
}