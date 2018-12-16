package com.example.anneh.restaurant;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context instance;
    private MenuRequest.Callback callbackActivity;
    private String category;


    // Interface
    public interface Callback {
        void gotItems(ArrayList<MenuItem> items);
        void gotItemsError(String message);
    }

    // Constructor
    public MenuRequest(Context context, String category) {

        this.instance = context;
        this.category = category;
    }

    // Attempt to retrieve menu items from API
    public void getItems(MenuRequest.Callback activity){

        callbackActivity = activity;
        JsonObjectRequest jsonObjectRequest;

        // Create a net RequestQueue
        RequestQueue queue = Volley.newRequestQueue(instance);
        String url = String.format("https://resto.mprog.nl/menu?category=%s", category);
        jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

    }

    // Callback method: JSONObjectRequest = success
    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonItems;
        ArrayList <MenuItem> items = new ArrayList<MenuItem>();
        String name, description,  imageUrl, category;
        float price;

        try {

            // Get array of menu items
            // https://stackoverflow.com/questions/38574925/retrieving-nested-arrays-values-with-json-java
            jsonItems =  (JSONArray) response.get("items");
            for (int i = 0; i < jsonItems.length(); i++) {

                try {
                    // Get menu Item
                    JSONObject menuItem = (JSONObject) jsonItems.get(i);

                    // Get values from menu item
                    category = menuItem.get("category").toString();
                    description = menuItem.get("description").toString();
                    price = Float.parseFloat(menuItem.get("price").toString()); // parseFloat: https://stackoverflow.com/questions/10735679/how-to-convert-string-into-float-value-in-android
                    imageUrl = menuItem.get("image_url").toString();
                    name = menuItem.get("name").toString();

                    // Construct MenuItem
                    MenuItem item = new MenuItem(name, description, imageUrl, price, category);

                    // Add item to list of MenuItems
                    items.add(item);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MenuRequest.java", "Can't get item values");
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.e("MenuRequest.java", "Can't get jsonItems");
        }

        // Pass menuItems back to MenuActivity
        try {
            callbackActivity.gotItems(items);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Callback method: JSONObjectRequest = error
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("CategoriesRequest.java", "ErrorResponse");
        callbackActivity.gotItemsError(error.getMessage());
    }


}

