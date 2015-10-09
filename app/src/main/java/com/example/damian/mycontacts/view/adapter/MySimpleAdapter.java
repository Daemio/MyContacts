package com.example.damian.mycontacts.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter {

    public MySimpleAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource,
                           String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewText(TextView v, String text) {
        // метод супер-класса, который вставляет текст
        super.setViewText(v, text);
        // если нужный нам TextView, то разрисовываем
    }

    @Override
    public void setViewImage(ImageView v, int value) {
        // метод супер-класса
        super.setViewImage(v, value);
    }
}