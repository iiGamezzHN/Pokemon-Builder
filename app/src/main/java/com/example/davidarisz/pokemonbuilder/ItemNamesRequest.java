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
    ArrayList arrayList = new ArrayList();
    int responses = 0;

    public interface Callback {
        void gotItemNames(ArrayList<String> pokemon);
    }

    public ItemNamesRequest(Context context) {
        this.context = context;
    }

    // Makes a request to the api
    public void getItemNames(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://pokeapi.co/api/v2/item-category/3/";
        String url2 = "https://pokeapi.co/api/v2/item-category/4/";
        String url3 = "https://pokeapi.co/api/v2/item-category/5/";
        String url4 = "https://pokeapi.co/api/v2/item-category/6/";
        String url5 = "https://pokeapi.co/api/v2/item-category/7/";
        String url6 = "https://pokeapi.co/api/v2/item-category/12/";
        String url7 = "https://pokeapi.co/api/v2/item-category/13/";
        String url8 = "https://pokeapi.co/api/v2/item-category/15/";
        String url9 = "https://pokeapi.co/api/v2/item-category/16/";
        String url10 = "https://pokeapi.co/api/v2/item-category/17/";
        String url11 = "https://pokeapi.co/api/v2/item-category/18/";
        String url12 = "https://pokeapi.co/api/v2/item-category/19/";
        String url13 = "https://pokeapi.co/api/v2/item-category/36/";
        String url14 = "https://pokeapi.co/api/v2/item-category/42/";
        String url15 = "https://pokeapi.co/api/v2/item-category/44/";
        String url16 = "https://pokeapi.co/api/v2/item-category/45/";
        String url17 = "https://pokeapi.co/api/v2/item-category/46/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(url2, null, this, this);
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(url3, null, this, this);
        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(url4, null, this, this);
        JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(url5, null, this, this);
        JsonObjectRequest jsonObjectRequest6 = new JsonObjectRequest(url6, null, this, this);
        JsonObjectRequest jsonObjectRequest7 = new JsonObjectRequest(url7, null, this, this);
        JsonObjectRequest jsonObjectRequest8 = new JsonObjectRequest(url8, null, this, this);
        JsonObjectRequest jsonObjectRequest9 = new JsonObjectRequest(url9, null, this, this);
        JsonObjectRequest jsonObjectRequest10 = new JsonObjectRequest(url10, null, this, this);
        JsonObjectRequest jsonObjectRequest11 = new JsonObjectRequest(url11, null, this, this);
        JsonObjectRequest jsonObjectRequest12 = new JsonObjectRequest(url12, null, this, this);
        JsonObjectRequest jsonObjectRequest13 = new JsonObjectRequest(url13, null, this, this);
        JsonObjectRequest jsonObjectRequest14 = new JsonObjectRequest(url14, null, this, this);
        JsonObjectRequest jsonObjectRequest15 = new JsonObjectRequest(url15, null, this, this);
        JsonObjectRequest jsonObjectRequest16 = new JsonObjectRequest(url16, null, this, this);
        JsonObjectRequest jsonObjectRequest17 = new JsonObjectRequest(url17, null, this, this);

        queue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest2);
        queue.add(jsonObjectRequest3);
        queue.add(jsonObjectRequest4);
        queue.add(jsonObjectRequest5);
        queue.add(jsonObjectRequest6);
        queue.add(jsonObjectRequest7);
        queue.add(jsonObjectRequest8);
        queue.add(jsonObjectRequest9);
        queue.add(jsonObjectRequest10);
        queue.add(jsonObjectRequest11);
        queue.add(jsonObjectRequest12);
        queue.add(jsonObjectRequest13);
        queue.add(jsonObjectRequest14);
        queue.add(jsonObjectRequest15);
        queue.add(jsonObjectRequest16);
        queue.add(jsonObjectRequest17);

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
        responses += 1;
        if(responses >= 17) {
            activity.gotItemNames(arrayList);
        }
    }
}
