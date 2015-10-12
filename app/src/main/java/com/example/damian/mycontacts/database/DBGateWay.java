package com.example.damian.mycontacts.database;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.damian.mycontacts.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09.10.2015.
 */
public class DBGateWay {
    private static SQLiteDatabase db = MyDBHelper.getInstance().getWritableDatabase();


    public static void addContact(UserData data){
        ContentValues cv = DBUtils.getCV(data);
        db.insert(DBUtils.TABLE_NAME, null, cv);
    }

    public static void editContact(UserData data){
        ContentValues cv = DBUtils.getCV(data);
        db.update(DBUtils.TABLE_NAME, cv, "ID=" + data.getId(), null);
    }

    public static void deleteAllContacts(){
        db.delete(DBUtils.TABLE_NAME, null, null);
    }
    public static  void deleteContact(UserData data){
        db = MyDBHelper.getInstance().getWritableDatabase();
        ContentValues cv = DBUtils.getCV(data);
        db.delete(DBUtils.TABLE_NAME, "ID=" + data.getId(), null);
    }

    public static List<UserData> getAllContacts(){
        return getContacts(false);
    }

    public static List<UserData> getFavoriteContacts(){
        return getContacts(true);
    }

    private static List<UserData> getContacts(boolean isFavoriteList) {
        List<UserData> datas = new ArrayList<UserData>();
        Cursor cursor;
        if (isFavoriteList){
            cursor = db.rawQuery(DBUtils.SELECT_ALL + DBUtils.TABLE_NAME + " WHERE " + DBUtils.FAVORITE +"=1",null);
        }else {
            cursor = db.rawQuery(DBUtils.SELECT_ALL + DBUtils.TABLE_NAME,null);
        }
        if(!cursor.moveToFirst()){
            return datas;
        }
        do {
            datas.add(DBUtils.parseCursor(cursor));
            cursor.moveToNext();
        }while (!cursor.isAfterLast());
        cursor.close();
        return datas;
    }

    public static UserData getContact(int id){
        UserData data;
        Cursor cursor = db.rawQuery("SELECT * FROM "+DBUtils.TABLE_NAME + " WHERE ID="+id,null);
        if(!cursor.moveToFirst()){
            return null;
        }
        data = DBUtils.parseCursor(cursor);
        cursor.close();
        return data;
    }
}
