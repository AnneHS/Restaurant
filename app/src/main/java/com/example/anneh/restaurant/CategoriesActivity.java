package com.example.anneh.restaurant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback{
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Start request for categories
        CategoriesRequest request = new CategoriesRequest(getApplicationContext());
        request.getCategories(CategoriesActivity.this);
    }

    @Override
    public void gotCategories(ArrayList<String> categories) {
        Toast.makeText(this, categories.get(0), Toast.LENGTH_LONG).show();

        // Create listview with array adapter: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this, categories);
        listView.setAdapter(adapter);

        // Set listview listener
        listView.setOnItemClickListener(new ListViewClickListener());
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Get selected category & open MenuActivity (+ pass category)
    private class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            // Fires an Intent to the third activity that shows the entry details
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);

            // Get selected category
            String categoryClicked = (String) adapterView.getItemAtPosition(position);
            intent.putExtra("category", categoryClicked);
            startActivity(intent);
        }
    }


}
