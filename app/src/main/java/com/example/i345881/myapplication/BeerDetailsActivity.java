package com.example.i345881.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class BeerDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = (ImageView) this.findViewById(R.id.beerDetailsImage);
        TextView nameTextView = (TextView) this.findViewById(R.id.textViewName);
        TextView nameTextDescription = (TextView) this.findViewById(R.id.textViewDescription);
        TextView nameTextVolume = (TextView) this.findViewById(R.id.textViewVolume);

        String image = getIntent().getStringExtra("beer_image");
        nameTextView.setText(getIntent().getStringExtra("beer_name"));
        nameTextDescription.setText(getIntent().getStringExtra("beer_description"));
        nameTextVolume.setText(getIntent().getStringExtra("beer_volume"));

        try {
            Picasso.get().load(image).into(imageView);
        } catch (Exception e) {
            System.out.println(e);
            Context ctx = getApplicationContext();
            CharSequence text = getString(R.string.error);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(ctx, text, duration);
            toast.show();
        }
    }
}
