package com.example.i345881.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.i345881.myapplication.Entities.Beer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private RecyclerView recyclerView;
    private int position;
    private Beer beer;
    private ImageView imageView;
    private HashMap photoThumbnails;

    public ImageLoadTask(RecyclerView recyclerView, int position, Beer beer, ImageView imageView, HashMap photoThumbnails) {
        this.recyclerView = recyclerView;
        this.position = position;
        this.beer = beer;
        this.imageView = imageView;
        this.photoThumbnails = photoThumbnails;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(this.beer.getImageUrl());
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        this.photoThumbnails.put(beer.getId(), result);

        // checking if the beer is still visible
        BeersAdapter.ViewHolder viewHolder = (BeersAdapter.ViewHolder) this.recyclerView.findViewHolderForAdapterPosition(this.position);

        if (viewHolder != null) {
            imageView.setImageBitmap(result);
        }
    }

}
