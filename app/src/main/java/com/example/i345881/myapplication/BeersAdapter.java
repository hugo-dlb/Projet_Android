package com.example.i345881.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.i345881.myapplication.Entities.Beer;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.ViewHolder> {
    private Beer[] beers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icon;
        public TextView label;
        private ViewHolder(View v) {
            super(v);
            icon = v.findViewById(R.id.icon);
            label = v.findViewById(R.id.label);
        }
    }

    public BeersAdapter(Beer[] beers) {
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
        new ImageLoadTask(this.beers[position].getImageUrl(), holder.icon).execute();
        holder.label.setText(this.beers[position].getName());
    }

    @Override
    public int getItemCount() {
        return this.beers.length;
    }

    public void setBeers(Beer[] beers) {
        System.out.println("Added " + beers.length + " beers to the adapter.");
        this.beers = beers;
        notifyDataSetChanged();
    }
}