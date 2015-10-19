package com.example.damian.mycontacts.asynctasks;

import com.example.damian.mycontacts.model.UserData;

import java.util.List;

/**
 * Created by Admin on 16.10.2015.
 */
public interface IDataBaseCallback {
    void onDataCame(List<UserData> userData);
}
