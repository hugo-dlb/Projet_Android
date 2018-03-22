package com.example.i345881.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetBeersService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_BEERS = "com.example.i345881.myapplication.action.GET_BEERS";

    public GetBeersService() {
        super("GetBeersService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetBeers(Context context) {
        Intent intent = new Intent(context, GetBeersService.class);
        intent.setAction(ACTION_GET_BEERS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_BEERS.equals(action)) {
                handleActionGetBeers();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetBeers() {
        URL url;
        try {
            url = new URL("https://api.punkapi.com/v2/beers");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Intent broadcastIntent = new Intent(MainActivity.BEERS_UPDATE);
                broadcastIntent.putExtra("data", (this.parseJSON(url)));
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private String parseJSON(URL url) throws Exception {
         Scanner sc = new Scanner(url.openStream());
         String result = "";
         while(sc.hasNext())
         {
             result += sc.nextLine();
         }
         sc.close();

         return result;
     }
}
