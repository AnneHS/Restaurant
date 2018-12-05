package com.example.anneh.restaurant;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    ArrayList <String> categoryList = new ArrayList<String>();

    // constructor
    public ListAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        mContext = context;
        categoryList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       //  View listItem = convertView;
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row, parent,false);

        String currentItem = categoryList.get(position);

        TextView item = (TextView) convertView.findViewById(R.id.list_item);
        item.setText(currentItem);

        // return super.getView(position, convertView, parent);
        return convertView;
    }
}
