package com.example.musiccatalog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Delayed;

import static android.app.Activity.RESULT_OK;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<RecyclerItem> listItems;
    private Context mContext;
    static final int GALLERY_REQUEST = 1;
    static final int GET_DATA_SET = 2;
    static final int EDIT_DATA_SET = 3;
    public int index = 0;
    Bitmap bitmap = null;
    Uri selectedImage = Uri.parse("");
    Activity origin;
    public ListAdapter(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());
        if(itemList.getUri() != null){
            holder.imageView.setImageURI(itemList.getUri());
        }
        else {
            holder.imageView.setImageBitmap(itemList.getImg());
        }
        holder.txtYear.setText(itemList.getYear());
        holder.imageView.setLongClickable(true);
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                origin = (Activity) mContext;
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                origin.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                index = position;
                return true;
            }
        });
        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option option_menu
                PopupMenu popupMenu = new PopupMenu(mContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.mnu_item_save:
                                origin = (Activity) mContext;
                                Intent dataIntent = new Intent(origin, AddItemActivity.class);
                                origin.startActivityForResult(dataIntent, GET_DATA_SET);

                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                origin.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                                break;
                            case R.id.mnu_item_delete:
                                //Delete item
                                if (listItems.size() > 1) {
                                    listItems.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(mContext, "Add one more element before deleting", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.mnu_item_edit:
                                origin = (Activity) mContext;
                                Intent datIntent = new Intent(origin, AddItemActivity.class);
                                origin.startActivityForResult(datIntent, EDIT_DATA_SET);
                                index = position;
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    public void setListItems(List<RecyclerItem> listItems)
    {
        this.listItems = listItems;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    selectedImage = returnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(index != 0)
                    {
                        listItems.get(index).setImg(bitmap);
                        listItems.get(index).setUri(selectedImage.toString());
                        listItems.get(index).setStringUri(selectedImage.toString());
                        notifyDataSetChanged();
                    }
                }
                break;
            case GET_DATA_SET:
                if(resultCode == RESULT_OK) {
                Bundle bundle = returnedIntent.getExtras();
                if (bundle != null && bitmap != null) {
                    NewListItem item = (NewListItem)bundle.getSerializable("item");
                    RecyclerItem temp;
                    if (selectedImage!= Uri.parse("")) {
                        temp = new RecyclerItem(item.getTitle(), item.getDescription(), item.getYear(), bitmap, selectedImage.toString());
                    }
                    else
                    {
                        temp = new RecyclerItem(item.getTitle(), item.getDescription(), item.getYear(), bitmap);
                    }
                    listItems.add(temp);
                    notifyDataSetChanged();
                    bitmap = null;
                }

            }
                break;
            case EDIT_DATA_SET:
            {
                try {
                    Bundle bundle = returnedIntent.getExtras();
                    if (bundle != null) {
                        NewListItem item = (NewListItem)bundle.getSerializable("item");
                        listItems.get(index).setData(item.getTitle(), item.getDescription(), item.getYear());
                        index = 0;
                        notifyDataSetChanged();
                    }
                }
                catch (RuntimeException ex)
                {
                    break;
                }

            }
        }
    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtOptionDigit;
        public TextView txtYear;
        public ImageView imageView;
        public ListViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit);
            txtYear = (TextView) itemView.findViewById(R.id.yearDigit);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
