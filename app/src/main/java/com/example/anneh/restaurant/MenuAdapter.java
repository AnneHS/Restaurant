package com.example.anneh.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
    private Context mContext;
    ArrayList<MenuItem> menuItems;

    // Constructor
    public MenuAdapter(@NonNull Context context, ArrayList<MenuItem> items) {
        super(context, 0, items);
        mContext = context;
        menuItems = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //  View listItem = convertView;
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_row, parent,false);

        // Get Menu item for current position
        MenuItem currentItem = menuItems.get(position);

        // Get reference to views
        TextView dish = (TextView) convertView.findViewById(R.id.dish);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        // Set dish name and price for current dish
        dish.setText(currentItem.getName());
        String priceString = String.format("$ %s", Float.toString(currentItem.getPrice()));
        price.setText(priceString);

        // Load dish image into ImageView
        Picasso.get().load(currentItem.getImageUrl()).into(image);

        // Return convertView to MenuActivity
        return convertView;
    }
}
