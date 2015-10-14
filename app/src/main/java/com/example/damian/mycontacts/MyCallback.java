package com.example.damian.mycontacts;

import com.example.damian.mycontacts.model.UserData;

/**
 * Created by Admin on 12.10.2015.
 */
public interface MyCallback {
    void callBackItemDeleted(UserData data);
    void callBackItemFavoriteStateChanged(UserData data);
    void callBackListItemSelected(UserData data);
}
