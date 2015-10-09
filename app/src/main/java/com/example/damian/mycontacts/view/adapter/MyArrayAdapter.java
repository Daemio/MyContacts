package com.example.damian.mycontacts.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.model.UserData;

/**
 * Created by Admin on 02.10.2015.
 */
public class MyArrayAdapter extends ArrayAdapter<UserData> {
    public MyArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserData data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
        }
        //then fill it

        ((CheckBox) convertView.findViewById(R.id.checkBoxFavorite)).setChecked(data.isFavorite());
        ((TextView) convertView.findViewById(R.id.tvNumber)).setText(""+data.getId());
        ((TextView) convertView.findViewById(R.id.tvDescription)).setText(data.getDescription());
        ((ImageView) convertView.findViewById(R.id.imageView)).setBackgroundResource(R.drawable.simple); //toFix

        return convertView;
    }
}
