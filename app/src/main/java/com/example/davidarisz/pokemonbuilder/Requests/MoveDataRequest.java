package com.example.davidarisz.pokemonbuilder.Requests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.davidarisz.pokemonbuilder.Classes.ItemData;
import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.models.Item;
import com.example.davidarisz.pokemonbuilder.models.Move;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MoveDataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private MoveDataRequest.Callback activity;
    private Gson gson = new Gson();
    private String name;

    public interface Callback {
        void gotMoveData(MoveData moveData);
    }

    public MoveDataRequest(Context context, String name) {
        this.context = context;
        this.name = name.substring(0,1).toLowerCase() + name.substring(1);;
    }

    // Makes a request to the api
    public void getMoveData(MoveDataRequest.Callback activity) {
        String url = "https://pokeapi.co/api/v2/move/"+name+"/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

        this.activity = activity;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.getMessage() == null) {
            Toast.makeText(context, "Null error :(", Toast.LENGTH_SHORT).show();
            Log.d("errorTag", "Null error");
        } else {
            Toast.makeText(context, "Else error :(", Toast.LENGTH_SHORT).show();
            Log.d("errorTag", "Else error");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Move move = gson.fromJson(response.toString(), Move.class);
        String name = move.getName();
        int power = move.getPower();
        int accuracy = move.getAccuracy();
        int pp = move.getPp();

        MoveData moveData = new MoveData(name, power, accuracy, pp);

        activity.gotMoveData(moveData);
    }
}