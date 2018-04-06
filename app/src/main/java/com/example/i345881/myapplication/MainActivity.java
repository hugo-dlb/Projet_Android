package com.example.i345881.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.i345881.myapplication.Entities.Beer;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter(BEERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BeerUpdate(),intentFilter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.DKGRAY);

        final Button button = findViewById(R.id.get_beers_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                ProgressDialog pDialog = new ProgressDialog(v.getContext());
                pDialog.setMessage(getString(R.string.beers_progress));
                pDialog.show();
                progressDialog = pDialog;

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

        adapter = new BeersAdapter(new Beer[0], this.recyclerView);
        recyclerView.setAdapter(adapter);

        new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_info)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.health_notification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
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

                Context ctx = getApplicationContext();
                CharSequence text = getString(R.string.beers_retrieved);
                int duration = Toast.LENGTH_LONG;

                progressDialog.dismiss();

                Toast toast = Toast.makeText(ctx, text, duration);
                toast.show();
            }
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_contact:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://brewdog1million.com/"));
                startActivity(browserIntent);
                return true;
            default:
                return false;
        }
    }
}