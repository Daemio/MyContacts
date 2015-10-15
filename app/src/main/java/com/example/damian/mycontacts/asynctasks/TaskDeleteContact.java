package com.example.damian.mycontacts.asynctasks;

import android.os.AsyncTask;

import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.model.UserData;

/**
 * Created by Admin on 15.10.2015.
 */
public class TaskDeleteContact extends AsyncTask<Void,Void,Void> {
    UserData data;

    public TaskDeleteContact(UserData data) {
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DBGateWay.deleteContact(data);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
