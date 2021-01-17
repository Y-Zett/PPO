package com.example.musiccatalog;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlbumCollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private List<RecyclerItem> listItems;
    public SQLiteDatabase database;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        listItems = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_ALBUMS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int labelIndex = cursor.getColumnIndex(DBHelper.KEY_LABEL);
            int yearIndex = cursor.getColumnIndex(DBHelper.KEY_YEAR);
            int uriIndex = cursor.getColumnIndex(DBHelper.KEY_URI);
            do {
                listItems.add(new RecyclerItem(cursor.getString(nameIndex), cursor.getString(labelIndex), cursor.getString(yearIndex), cursor.getString(uriIndex)));
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", label = " + cursor.getString(labelIndex) + ", year " + cursor.getString(yearIndex) + ", uri " + cursor.getString(uriIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (listItems.isEmpty()) {
            Drawable image1 = getDrawable(R.drawable.nirvana_nevermind);
            Bitmap bit1 = ((BitmapDrawable) image1).getBitmap();
            listItems.add(new RecyclerItem("Nevermind", "Nirvana", "1991", bit1));
        }
        adapter = new ListAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStop() {
        database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_ALBUMS, null, null);

        int counter = 0;
        for (RecyclerItem item :
                listItems) {
            if (item.getUri() != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.KEY_NAME, item.getTitle());
                contentValues.put(DBHelper.KEY_ID, Integer.toString(counter));
                contentValues.put(DBHelper.KEY_LABEL, item.getDescription());
                contentValues.put(DBHelper.KEY_YEAR, item.getYear());
                contentValues.put(DBHelper.KEY_URI, item.getStringUri());
                database.insert(DBHelper.TABLE_ALBUMS, null, contentValues);
                counter++;
            }
        }

        dbHelper.close();
        super.onStop();
    }
}
