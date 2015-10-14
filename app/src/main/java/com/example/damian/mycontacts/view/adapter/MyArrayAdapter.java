package com.example.damian.mycontacts.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.damian.mycontacts.MyCallback;
import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.TheApplication;
import com.example.damian.mycontacts.model.UserData;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 02.10.2015.
 */
public class MyArrayAdapter extends ArrayAdapter<UserData> {
    public MyArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    private MyCallback myCallback;

    public void setMyCallback(MyCallback myCallback) {
        this.myCallback = myCallback;
    }

    static  class ViewHolder{
        TextView tvNumber;
        TextView tvDescription;
        ImageView imageView;
        CheckBox cbFavorite;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        ;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.tvNumber = (TextView) v.findViewById(R.id.tvNumber);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            holder.imageView = (ImageView) v.findViewById(R.id.imageView);
            holder.cbFavorite = (CheckBox) v.findViewById(R.id.checkBoxFavorite);
            v.setTag(holder);

        }else{
            //recycled so no neeed to inflate and find views by id
            holder = (ViewHolder) v.getTag();
        }

        //then fill it

        UserData data = getItem(position);
        holder.cbFavorite.setChecked(data.isFavorite());
        holder.tvNumber.setText("" + data.getId());
        holder.tvDescription.setText(data.getDescription());
        Picasso.with(TheApplication.getInstance().getApplicationContext()).load(data.getImagePath()).into(holder.imageView);

        //listeners and callbacks

        ((ImageButton) v.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData data = getItem(position);
                if (myCallback != null) {
                    myCallback.callBackItemDeleted(data);
                }

            }
        });


        holder.cbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData data = getItem(position);
                CheckBox cb = (CheckBox) v;
                data.setFavorite(cb.isChecked());
                if (myCallback != null) {
                    myCallback.callBackItemFavoriteStateChanged(data);
                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData data = getItem(position);
                if (myCallback != null) {
                    myCallback.callBackListItemSelected(data);
                }
            }
        });


        return v;
    }
}
