package com.example.damian.mycontacts;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.damian.mycontacts.database.DBGateWay;
import com.example.damian.mycontacts.database.DBUtils;
import com.example.damian.mycontacts.database.MyDBHelper;
import com.example.damian.mycontacts.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09.10.2015.
 */
public class TestDB extends ApplicationTest {
    public void testCheckCorrectData() {
        MyDBHelper.getInstance().onUpgrade(MyDBHelper.getInstance().getWritableDatabase(),1,2);
        DBGateWay.deleteAllContacts();
        UserData contact1 = new UserData("Sam", "path", true);
        DBGateWay.addContact(contact1);
        UserData contact2 = DBGateWay.getContact(1);
        assertEquals(contact1, contact2);

        contact2.setDescription("Tom");
        DBGateWay.editContact(contact2);
        assertTrue(!contact1.equals(contact2));
        UserData contact3 = new UserData("Ben", "path", true);
        DBGateWay.addContact(contact3);
        DBGateWay.editContact(contact3); //ben is in base
        //Log.d("log", DBGateWay.getContact(contact1.getId()).toString());
        DBGateWay.addContact(contact1);//ben and tom
        DBGateWay.addContact(contact2);//ben tom sam


        List<UserData> list;
        list = DBGateWay.getAllContacts();
        for (UserData item:list){
            Log.d("mylog",item.toString());
        }

    }

    public void testCheckCorrectData1() {

        DBGateWay.deleteAllContacts();
        UserData contact1 = new UserData("Sam", "path", true);
        UserData contact2 = new UserData("Tom", "path", false);
        UserData contact3 = new UserData("Ann", "path", true);
        DBGateWay.addContact(contact1);
        DBGateWay.addContact(contact2);
        DBGateWay.addContact(contact3);

        List<UserData> list;
        list = DBGateWay.getAllContacts();
        for (int i=0;i<list.size();i++){
            Log.d("mylog", list.get(i).toString());
        }

    }


}
