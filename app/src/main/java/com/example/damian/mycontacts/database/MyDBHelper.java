package com.example.damian.mycontacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.damian.mycontacts.TheApplication;

/**
 * Created by Admin on 09.10.2015.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static MyDBHelper instance;


    public static MyDBHelper getInstance() {
        if (instance == null) {
            instance = new MyDBHelper(TheApplication.getInstance().getApplicationContext(), DBUtils.DB_NAME, null, DBUtils.DB_VERISION);
        }
        return instance;
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DB_VERISION);
        instance = this;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    private void createTable(SQLiteDatabase db, String tableName, String... columns) {
        String query = DBUtils.CREATE + tableName + "("+columns[0]+" INTEGER PRIMARY KEY, ";
        for (int i = 1; i < columns.length - 1; i++) {
            query += columns[i] + ", ";
        }
        query += columns[columns.length - 1] + ");";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, DBUtils.TABLE_NAME, DBUtils.ID, DBUtils.DESCRIPTION, DBUtils.IMAGE_PATH, DBUtils.FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBUtils.TABLE_NAME);
        onCreate(db);
    }
}
