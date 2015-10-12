package com.example.damian.mycontacts.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.damian.mycontacts.CameraUtils;
import com.example.damian.mycontacts.MyCallback;
import com.example.damian.mycontacts.R;
import com.example.damian.mycontacts.TheApplication;
import com.example.damian.mycontacts.model.UserData;

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UserData data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
        }
        //then fill it

        CheckBox cbFavorite = ((CheckBox) convertView.findViewById(R.id.checkBoxFavorite));
        cbFavorite.setChecked(data.isFavorite());
        ((TextView) convertView.findViewById(R.id.tvNumber)).setText("" + data.getId());
        ((TextView) convertView.findViewById(R.id.tvDescription)).setText(data.getDescription());
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        CameraUtils.setPic(image,data.getImagePath(), TheApplication.getInstance().getApplicationContext());//set image


        //listeners and callbacks

        ((ImageButton) convertView.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData data = getItem(position);
                if (myCallback!= null){
                    myCallback.callBackItemDeleted(data);
                }

            }
        });

        cbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserData data = getItem(position);
                data.setFavorite(isChecked);
                if (myCallback!= null){
                    myCallback.callBackItemFavoriteStateChanged(data);
                }
            }
        });
        return convertView;
    }
}
