package com.example.anneh.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuRequest.Callback {
    private MenuAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String category = (String) intent.getStringExtra("category");
        MenuRequest request = new MenuRequest(this, category);
        request.getItems(MenuActivity.this); // url meegeven?

        ListView listView = (ListView) findViewById(R.id.menu_list);

        // Toast test
        Toast.makeText(this, "Started MenuActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotItems(ArrayList<MenuItem> items) {
        Toast.makeText(this, "gotItems" , Toast.LENGTH_LONG).show();
        // items.get(0)

        // create listview with array adapter: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
        ListView listView = (ListView) findViewById(R.id.menu_list);
        adapter = new MenuAdapter(this, items);
        listView.setAdapter(adapter);

        // Set listview listener
        listView.setOnItemClickListener(new MenuActivity.ListViewClickListener());
    }

    @Override
    public void gotItemsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Get selected category & open MenuActivity (+ pass category)
    private class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            // fires an Intent to the third activity that shows the entry details
            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);

            // Get selected category
            MenuItem dishClicked = (MenuItem) adapterView.getItemAtPosition(position);
            intent.putExtra("dish", dishClicked);
            startActivity(intent);
        }
    }
}
