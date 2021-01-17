package com.example.musiccatalog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "albumDb";
    public static final String TABLE_ALBUMS = "albums";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LABEL = "label";
    public static final String KEY_YEAR = "year";
    public static final String KEY_URI = "uri";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ALBUMS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_LABEL + " text," + KEY_YEAR + " text," + KEY_URI + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ALBUMS);
        onCreate(db);
    }
}
