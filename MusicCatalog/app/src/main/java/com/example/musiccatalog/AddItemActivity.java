package com.example.musiccatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class AddItemActivity extends AppCompatActivity {
    Button addbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        addbutton = findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText = findViewById(R.id.editname);
                EditText labelnameText = findViewById(R.id.editlabelname);
                EditText yearText = findViewById(R.id.edityear);
                String name = nameText.getText().toString();
                String labelname = labelnameText.getText().toString();
                String year = yearText.getText().toString();
                RecyclerItem item = new RecyclerItem(name, labelname, year);
                Intent intent = new Intent(AddItemActivity.this, AlbumCollectionActivity.class);
                intent.putExtra("item", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}
