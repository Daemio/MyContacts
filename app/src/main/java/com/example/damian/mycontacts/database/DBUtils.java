package com.example.damian.mycontacts.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.damian.mycontacts.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09.10.2015.
 */
public class DBUtils {
    //db constants
    public static  final String DB_NAME = "ContactDB";
    public  static final int DB_VERISION = 1;

    //table
   public static final String TABLE_NAME = "CONTACTS";
    //fields
    public  static final String ID = "id";
    public static final String DESCRIPTION = "DESCRIPTION";
    public  static final String IMAGE_PATH = "IMAGE_PATH";
    public  static final String FAVORITE = "FAVORITE";

    //query
    public static final String CREATE = " CREATE TABLE IF NOT EXISTS ";
    public static final String SELECT_ALL = "SELECT * FROM ";

    public static ContentValues getCV(UserData userData){
        ContentValues cv = new ContentValues();
        cv.put(DESCRIPTION,userData.getDescription());
        cv.put(IMAGE_PATH,userData.getImagePath());
        cv.put(FAVORITE,userData.isFavorite()?1:0);
        return cv;
    }

    public static UserData parseCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(DBUtils.ID));
        String description = cursor.getString(cursor.getColumnIndex(DBUtils.DESCRIPTION));;
        String imagePath = cursor.getString(cursor.getColumnIndex(DBUtils.IMAGE_PATH));;
        Boolean isFavorite = cursor.getShort(cursor.getColumnIndex(DBUtils.FAVORITE)) == 1;;
        UserData data = new UserData(description,imagePath,isFavorite);
        data.setId(id);
        return  data;
    }


}
