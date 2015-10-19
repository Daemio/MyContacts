package com.example.damian.mycontacts.asynctasks;

import android.os.AsyncTask;

import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.model.UserData;

import java.util.List;

/**
 * Created by Admin on 15.10.2015.
 */
public class TaskGetAllContacts extends AsyncTask<Void, List<UserData>, List<UserData>> {

    private IDataBaseCallback baseCallback;

    public TaskGetAllContacts(IDataBaseCallback baseCallback) {
        this.baseCallback = baseCallback;
    }

    @Override
    protected List<UserData> doInBackground(Void... params) {
        return DBGateWay.getAllContacts();
    }

    @Override
    protected void onPostExecute(List<UserData> data) {
        if (baseCallback != null) {
            baseCallback.onDataCame(data);
        }
    }
}
