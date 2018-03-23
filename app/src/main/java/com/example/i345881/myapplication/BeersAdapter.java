package com.example.i345881.myapplication;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.i345881.myapplication.Entities.Beer;

import java.util.HashMap;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private Beer[] beers;
    private HashMap<Integer, Bitmap> photoThumbnails = new HashMap<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView label;
        private ViewHolder(View v) {
            super(v);
            icon = v.findViewById(R.id.icon);
            label = v.findViewById(R.id.label);
        }
    }

    public BeersAdapter(Beer[] beers, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.beers = beers;
    }

    @Override
    public BeersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_recycler_view_row, null, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Beer beer = beers[position];

        // attempt to load image from cache
        Bitmap thumbnail = photoThumbnails.get(beer.getId());

        if (thumbnail == null) {
            holder.icon.setImageResource(android.R.color.transparent);
            try {
                new ImageLoadTask(this.recyclerView, position, beer, holder.icon, photoThumbnails).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.icon.setImageBitmap(thumbnail);
        }
        holder.label.setText(beer.getName());
    }

    @Override
    public int getItemCount() {
        return this.beers.length;
    }

    public void setBeers(Beer[] beers) {
        this.beers = beers;
        notifyDataSetChanged();
    }
}