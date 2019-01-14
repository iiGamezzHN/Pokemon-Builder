package com.example.davidarisz.pokemonbuilder;

import android.content.Context;
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

public class ItemNamesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        void gotItemNames(ArrayList<String> pokemon);
    }

    public ItemNamesRequest(Context context) {
        this.context = context;
    }

    // Makes a request to the api
    public void getItemNames(Callback activity) {
        String url = "https://pokeapi.co/api/v2/item-category/3/";
        String url2 = "https://pokeapi.co/api/v2/item-category/36/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(url2, null, this, this);
        queue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest2);

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
            values = response.getJSONArray("items");

            for (int i = 0; i < values.length(); i++) {
                JSONObject object = values.getJSONObject(i);
                String item = object.getString("name");
                String item2 = item.substring(0,1).toUpperCase() + item.substring(1);

                arrayList.add(item2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        activity.gotItemNames(arrayList);
    }
}
