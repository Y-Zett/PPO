package com.example.musiccatalog;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        Drawable image1 = getDrawable(R.drawable.nirvana_nevermind);
        Bitmap bit1 = ((BitmapDrawable)image1).getBitmap();
        listItems.add(new RecyclerItem("Nevermind", "Nirvana","1991", bit1 ));

        //Set adapter
        adapter = new ListAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        int counter = 0;
        for (RecyclerItem item :
                listItems) {
            if (item.getUri() != null)
            {
                counter++;
                String key = "object" + counter;
                savedInstanceState.putSerializable(key,item);

            }

        }
        savedInstanceState.putInt("counter", counter);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int counter = savedInstanceState.getInt("counter");
        for (int i = 1; i <= counter; i++) {
            String key = "object" + i;
            RecyclerItem item = (RecyclerItem)savedInstanceState.getSerializable(key);
            if (item != null)
            {
                item.setUri(item.getStringUri());
                listItems.add(item);
            }
        }

    }
}
