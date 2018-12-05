package com.example.anneh.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        Intent intent = getIntent();
        menuItem = (MenuItem) intent.getSerializableExtra("dish");

        TextView dish = (TextView) findViewById(R.id.dish);
        TextView price = (TextView) findViewById(R.id.price);
        TextView description = (TextView) findViewById(R.id.description);
        ImageView image = (ImageView) findViewById(R.id.dishIMG);



        //
        dish.setText(menuItem.getName());
        description.setText(menuItem.getDescription());
        price.setText(Float.toString(menuItem.getPrice())); // consider String # format
        Picasso.get().load(menuItem.getImageUrl()).into(image);
    }
}
