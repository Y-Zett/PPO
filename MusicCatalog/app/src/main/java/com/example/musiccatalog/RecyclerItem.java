package com.example.musiccatalog;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class RecyclerItem implements Serializable{
    private String title;
    private String description;
    private String year;
    private transient Bitmap img;
    private transient Uri uri;
    private String stringUri;

    public RecyclerItem(String title, String description, String year, Bitmap img) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.img = img;
    }
    public RecyclerItem(String title, String description, String year, Bitmap img, String uri)
    {
        this.title = title;
        this.description = description;
        this.year = year;
        this.img = img;
        this.uri = Uri.parse(uri);
        this.stringUri = uri;
    }
    public void setData(String name, String description, String year)
    {
        this.title = name;
        this.year = year;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = Uri.parse(uri);
    }

    public String getStringUri() {
        return stringUri;
    }

    public void setStringUri(String stringUri) {
        this.stringUri = stringUri;
    }
}
