package com.example.quake2.utilities;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkHelper {

    private static final String TAG = "myService";



    public static boolean hasNetworkAccess (Context context) {
        Log.i(TAG, "current thread "+ Thread.currentThread());
        ConnectivityManager myConnectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetwork = myConnectionManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
