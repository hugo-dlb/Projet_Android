package com.example.i345881.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.i345881.myapplication.Entities.Beer;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter(BEERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BeerUpdate(),intentFilter);

        final Button button = findViewById(R.id.get_beers_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GetBeersService.startActionGetBeers(v.getContext());
            }
        });

        recyclerView = findViewById(R.id.beer_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BeersAdapter(new Beer[0]);
        recyclerView.setAdapter(adapter);
    }

    public static final String BEERS_UPDATE = "BEERS_UPDATE";

    public class BeerUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BEERS_UPDATE)) {
                String data = (String) intent.getExtras().get("data");

                final Gson gson = new Gson();
                Beer[] beers = gson.fromJson(data, Beer[].class);

                ((BeersAdapter) adapter).setBeers(beers);
            }
        }
    }
}