package com.example.damian.mycontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.damian.mycontacts.adapter.MySimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final String ATTRIBUTE_NUMBER = "name";
    final String ATTRIBUTE_NAME = "description";
    final String ATTRIBUTE_FAVORITE = "favorite";
    final String ATTRIBUTE_IMAGE = "image";

    ListView lvMain;
    TextView tvNnumberOfContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.listView);
        tvNnumberOfContacts = (TextView) findViewById(R.id.tvContactNumber);

        // массив данных
        int[] numbers = { 1, 3, 7, 17, 19, 36, 56, 57,58,59,78,80,78,80,78,80,78,80,78,80,78,80 };
        String[] names = {"John", "Rob", "Mary", "Alex", "Ann", "Andrew", "Alex", "Ann", "Andrew", "Alex", "Ann", "Andrew", "Alex", "Ann", "Andrew"};
        int[] images = new int[names.length];
        for(int i=0;i<names.length;i++){
            images[i] = R.drawable.simple;
        }



        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                names.length);
        Map<String, Object> m;

        for (int i = 0; i < names.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NUMBER, numbers[i]);
            m.put(ATTRIBUTE_NAME, names[i]);
            m.put(ATTRIBUTE_IMAGE, images[i]);
            data.add(m);
        }
        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NUMBER, ATTRIBUTE_NAME,
                ATTRIBUTE_IMAGE};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvNumber, R.id.tvName, R.id.imageView};

        // создаем адаптер
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data,
                R.layout.item, from, to);

        // определяем список и присваиваем ему адаптер
        lvMain.setAdapter(sAdapter);
        tvNnumberOfContacts.setText("Contacts("+sAdapter.getCount()+")");
    }
}
