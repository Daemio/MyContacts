package com.example.damian.mycontacts.asynctasks;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.damian.mycontacts.database.DBGateWay;

import java.util.List;

/**
 * Created by Admin on 15.10.2015.
 */
public class TaskGetFavoriteContacts extends AsyncTask<Void,Void,Void>{
    List data;
    ArrayAdapter adapter;
    TextView tvNumberOfContacts;

    public TaskGetFavoriteContacts(List data, ArrayAdapter adapter, TextView tvNumberOfContacts) {
        this.data = data;
        this.adapter = adapter;
        this.tvNumberOfContacts = tvNumberOfContacts;
    }

    @Override
    protected Void doInBackground(Void... params) {
        data = DBGateWay.getFavoriteContacts();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.clear();
        adapter.addAll(data);
        tvNumberOfContacts.setText("Favorite("+adapter.getCount()+")");
        super.onPostExecute(aVoid);
    }
}
