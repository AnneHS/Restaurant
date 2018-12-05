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

    //
    public void getItems(MenuRequest.Callback activity){
        // attempt to retrieve the items from the API
        // notify activity that instantiated the request that it is done through callback = activity
        JsonObjectRequest jsonObjectRequest;
        // save activity as instance variable
        callbackActivity = activity;

        // Create a net RequestQueue
        RequestQueue queue = Volley.newRequestQueue(instance);
        String url = String.format("https://resto.mprog.nl/menu?category=%s", category);

        Log.d("URL", url);
        jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

    }

    // callback method --> JSONObjectRequest = success
    @Override
    public void onResponse(JSONObject response) {
        Log.d("MenuRequest.java", "onResponse");

        // ArrayList <String> categories = new ArrayList<String>();
        JSONArray jsonItems;
        ArrayList <MenuItem> items = new ArrayList<MenuItem>();
        String name, description,  imageUrl, category;
        float price;
        int id;

        try {
            // { "items:" [{a:x, b:y, c:z},{},{}]}

            // Get array of menu items: https://stackoverflow.com/questions/38574925/retrieving-nested-arrays-values-with-json-java
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
                    id = Integer.parseInt(menuItem.get("id").toString()); // https://www.mkyong.com/java/java-convert-string-to-int/
                    name = menuItem.get("name").toString();

                    // construct MenuItem
                    MenuItem item = new MenuItem(name, description, imageUrl, price, category);

                    // add item to list of MenuItems
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


        try {
            // pass menuItems back to MenuActivity
            callbackActivity.gotItems(items);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // callback method --> JSONObjectRequest = error --> report to CategoriesActivity
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("CategoriesRequest.java", "ErrorResponse");
        callbackActivity.gotItemsError(error.getMessage());
    }


}

