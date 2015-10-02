package com.example.damian.mycontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.damian.mycontacts.adapter.MyArrayAdapter;
import com.example.damian.mycontacts.model.UserData;

import java.util.ArrayList;


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
        ArrayList data = new ArrayList<UserData>();
        data.add(new UserData("Ann", "1", true, null));
        data.add(new UserData("Andrew", "1", true, null));
        data.add(new UserData("Damian", "1", false, null));
        data.add(new UserData("Den", "1", true, null));
        data.add(new UserData("Sam", "1", false, null));
        data.add(new UserData("Bill", "1", true, null));
        data.add(new UserData("Tom", "1", true, null));
        data.add(new UserData("Caren", "1", false, null));
        data.add(new UserData("Michael", "1", false, null));
        data.add(new UserData("Stew", "1", false, null));
        data.add(new UserData("Rob", "1", true, null));
        data.add(new UserData("Jay", "1", true, null));
        data.add(new UserData("Helen", "1", false, null));
        data.add(new UserData("Michael", "1", false, null));

        // создаем адаптер
        MyArrayAdapter sAdapter = new MyArrayAdapter(this, R.layout.item);
        sAdapter.addAll(data); //добавим элементы из нашего контейнера

        // присваиваем адаптер
        lvMain.setAdapter(sAdapter);
        tvNnumberOfContacts.setText("Contacts(" + sAdapter.getCount() + ")");
    }
}
