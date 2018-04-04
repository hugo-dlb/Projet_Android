package com.example.i345881.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class BeerDetails extends AppCompatActivity {

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

        TextView nameTextView = (TextView) this.findViewById(R.id.textViewName);
        TextView nameTextDescription = (TextView) this.findViewById(R.id.textViewDescription);
        TextView nameTextVolume = (TextView) this.findViewById(R.id.textViewVolume);

        nameTextView.setText(getIntent().getStringExtra("beer_name"));
        nameTextDescription.setText(getIntent().getStringExtra("beer_description"));
        nameTextVolume.setText(getIntent().getStringExtra("beer_volume"));
    }
}
