package com.example.quake2.myservices;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.example.quake2.models.EarthQuakeModel;
import com.example.quake2.parsers.XMLParser;
import com.example.quake2.utilities.MyHttpHelper;

import java.io.IOException;

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    public static final String MY_SERVICE_EXCEPTION = "myServiceException";

    public MyService() {
        super("MyService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uri = intent.getData();
        Log.i(TAG, "onHandleIntent: " + uri.toString());
        Log.i(TAG, "current thread "+ Thread.currentThread());

        String response;
        try {
            response = MyHttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();

            Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
            messageIntent.putExtra(MY_SERVICE_EXCEPTION, e.getMessage());
            LocalBroadcastManager manager =
                    LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
            return;
        }


        EarthQuakeModel[] dataItems = XMLParser.parseFeed(response);
        Log.i(TAG, "onCreate"+ dataItems.length );
        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
        Log.i(TAG, "onCreate" );
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);
        LocalBroadcastManager manager =

                LocalBroadcastManager.getInstance(getApplicationContext());
        Log.i(TAG, "onCreate" );
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        Log.i(TAG, "current thread "+ Thread.currentThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        Log.i(TAG, "current thread "+ Thread.currentThread());
    }

}