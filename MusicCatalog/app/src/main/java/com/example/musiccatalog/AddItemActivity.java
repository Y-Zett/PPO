package com.example.musiccatalog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddItemActivity extends AppCompatActivity {
    static final int GALLERY_REQUEST = 1;
    static final int GET_DATA_SET = 2;
    Button button;
    Button addbutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        addbutton = findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText nameText = findViewById(R.id.editname);
                EditText labelnameText = findViewById(R.id.editlabelname);
                EditText yearText = findViewById(R.id.edityear);
                String name = nameText.getText().toString();
                String labelname = labelnameText.getText().toString();
                String year = yearText.getText().toString();
                NewListItem item = new NewListItem(name, labelname, year);
                Intent intent = new Intent(AddItemActivity.this, AlbumCollectionActivity.class);
                intent.putExtra("item", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



}
