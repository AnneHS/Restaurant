package com.example.anneh.restaurant;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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


public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context instance;
    Callback callbackActivity;

    // Interface
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // Constructor
    public CategoriesRequest(Context context) {

        this.instance = context;
    }

    // Attempt to retrieve the categories from the API
    // notify activity that instantiated the request that it is done through callback = activity
    public void getCategories(Callback activity){

        callbackActivity = activity;

        // Create a new RequestQueue
        RequestQueue queue = Volley.newRequestQueue(instance);

        // Create JsonObjectRequest (date from API = JSON object) (url, data send, listeners that will handle te response (this class))
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/categories", null, this, this);
        queue.add(jsonObjectRequest);

    }

    // Callback method: JSONObjectRequest = success -> report to CategoriesActivity
    @Override
    public void onResponse(JSONObject response) {

        ArrayList <String> categories = new ArrayList<String>();
        String category;
        JSONArray jsonCategories;

        try {
            // Get jsonArray
            // https://processing.org/reference/JSONObject_getJSONArray_.html
            jsonCategories = response.getJSONArray("categories");

            // Add category to categories array
            for (int i = 0; i < jsonCategories.length(); i++) {

                try {
                    category = jsonCategories.getString(i);
                    categories.add(category);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Restaurant", "unexpected JSON exception (getString())", e);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace(); // verschillende namen voor verschillende exceptions?
        }


        try {
            // Pass categories back CategoriesActivity
            callbackActivity.gotCategories(categories);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Callback method : JSONObjectRequest = error -> report to CategoriesActivity
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("CategoriesRequest.java", "ErrorResponse");
        callbackActivity.gotCategoriesError(error.getMessage());
    }


}
