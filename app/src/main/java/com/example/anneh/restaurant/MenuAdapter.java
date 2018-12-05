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
    ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

    // constructor
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

        TextView dish = (TextView) convertView.findViewById(R.id.dish);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        //
        dish.setText(currentItem.getName());
        price.setText(Float.toString(currentItem.getPrice())); // consider String # format
        Picasso.get().load(currentItem.getImageUrl()).into(image);

        // return super.getView(position, convertView, parent);
        return convertView;
    }
}
