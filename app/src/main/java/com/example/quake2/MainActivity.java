/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

package com.example.quake2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.quake2.models.EarthQuakeModel;
import com.example.quake2.myservices.MyService;
import com.example.quake2.utilities.NetworkHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.*;

public class MainActivity extends AppCompatActivity  {

        public static final String TAG = "MyService";
        private static final String XML_URL = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

        private boolean networkOk;

        GoogleMap myMap;
        // TextView output;
        Button start;
        ArrayList <EarthQuakeModel> earthquakes;
        RecyclerView myRecyclerView;
        RecyclerView.LayoutManager myLayoutManager;
        RecyclerView.Adapter myAdapter;
        Context context;
        Switch mySwitch;
        Switch northSwitch;
        Switch depthSwitch;


    private BroadcastReceiver mBroadcastReceiver;

    {
        mBroadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra(MyService.MY_SERVICE_PAYLOAD)) {
                    EarthQuakeModel[] dataItems = (EarthQuakeModel[]) intent
                            .getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD);
                    earthquakes = new ArrayList<>();

                    // gets the data and puts into view
                    for (EarthQuakeModel dataItem : dataItems) {

                        earthquakes.add(dataItem);
                        Log.i(TAG, "item added to earthquake array");
                        //  output.append("\n");
                        //  output.append("magnitude is "+ dataItem.getMagnitude() + "\n");
                        // output.append("depth is "+ dataItem.getDepth() + "\n");
                        // output.append("location is "+ dataItem.getLocation() + "\n");
                        //  output.append("\n");
                        //  output.append(dataItem.getDescription() + "\n");
                        //  output.append("\n");
                        Toast.makeText(context, "retrieving data...", Toast.LENGTH_SHORT).show();
                    }
                    //sorting by magnitude
                    Boolean toggleCheckMag = mySwitch.isChecked();
                    Boolean toggleCheckNorth = northSwitch.isChecked();
                    Boolean toggleCheckDepth = depthSwitch.isChecked();

                    if (toggleCheckDepth == true){
                        mySwitch = findViewById(R.id.myMagSwitch);
                        mySwitch.setChecked(false);
                        northSwitch = findViewById(R.id.myNorthSwitch);
                        northSwitch.setChecked(false);
                    }


                    if (toggleCheckNorth == true){
                        mySwitch = findViewById(R.id.myMagSwitch);
                        mySwitch.setChecked(false);
                        depthSwitch.setChecked(false);
                    }
                    if (toggleCheckMag == true) {
                        northSwitch = findViewById(R.id.myNorthSwitch);
                        northSwitch.setChecked(false);
                        depthSwitch.setChecked(false);
                    }

                    Log.i(TAG, "onReceive: "+ Thread.currentThread());
                     Log.i(TAG, "onReceive: "+ toggleCheckMag);
                    if (toggleCheckMag == true)

                        sort(earthquakes, new Comparator<EarthQuakeModel>() {
                            @Override
                            public int compare(EarthQuakeModel o1, EarthQuakeModel o2) {
                                return o2.getSeverity().compareTo(o1.getSeverity());
                            }
                        });
                    if (toggleCheckNorth == true)
                        sort(earthquakes, new Comparator<EarthQuakeModel>() {
                            @Override
                            public int compare(EarthQuakeModel o1, EarthQuakeModel o2) {
                                int result = Float.compare(o2.getLatNumber(),o1.getLatNumber());
                                return result;
                            }
                        });
                    if (toggleCheckDepth == true)

                        sort(earthquakes, new Comparator<EarthQuakeModel>() {
                            @Override
                            public int compare(EarthQuakeModel o1, EarthQuakeModel o2) {
                                int result = Float.compare(o2.getDepthNumber(),o1.getDepthNumber());
                                return result;
                            }
                        });

                    myRecyclerView.setHasFixedSize(true);

                    //below allows horizontal scrolling of recycler view
                    //if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                      //  myLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                   // } else
                   //  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                        myLayoutManager = new LinearLayoutManager(context);                //  }
                    //

                    DividerItemDecoration itemDecoration = new DividerItemDecoration(context, ((LinearLayoutManager) myLayoutManager).getOrientation());
                    itemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.recyclerborder));
                    myRecyclerView.setLayoutManager(myLayoutManager);
                    myRecyclerView.addItemDecoration(itemDecoration);
                    myRecyclerView.setBackgroundColor(getResources().getColor(R.color.recBackground));
                    myAdapter = new MainAdapter(context, earthquakes);
                    myRecyclerView.setAdapter(myAdapter);

                } else if (intent.hasExtra(MyService.MY_SERVICE_EXCEPTION)) {
                    String message = intent.getStringExtra(MyService.MY_SERVICE_EXCEPTION);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }


            }
        };
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        Log.i(TAG, "current thread "+ Thread.currentThread());


            mySwitch = findViewById(R.id.myMagSwitch);
            mySwitch.setChecked(false);
        northSwitch = findViewById(R.id.myNorthSwitch);
        northSwitch.setChecked(false);
        depthSwitch = findViewById(R.id.depthSwitch);
        depthSwitch.setChecked(false);
/*
            Spinner spinner= findViewById(R.id.myspinner);

                ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sorting));
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAdapter);
                spinner.setOnItemClickListener(this);
*/
            context = this;

            //output = findViewById(R.id.output);
            start = findViewById(R.id.run_button);
            myRecyclerView = findViewById(R.id.recyclerView);

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(mBroadcastReceiver,
                            new IntentFilter(MyService.MY_SERVICE_MESSAGE));
        Log.i(TAG, "current thread "+ Thread.currentThread());
            networkOk = NetworkHelper.hasNetworkAccess(this);
            if (networkOk){
                Toast.makeText(context, "network connected", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "current thread "+ Thread.currentThread());
            } else { Toast.makeText(context, "no network!!!!", Toast.LENGTH_SHORT).show();
            }

            // output.append("connection made " + networkOk);


        }

        //click handler for get by magnitude
    /*
        public void runMagnitude(View view) {

            if (networkOk) {
                Intent intent = new Intent(this, MyService.class);
                intent.setData(Uri.parse(XML_URL));
                startService(intent);


            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
        }*/
        public void runClickHandler(View view) {
            Log.i(TAG, "current thread "+ Thread.currentThread());
            if (networkOk) {
                Intent intent = new Intent(this, MyService.class);
                intent.setData(Uri.parse(XML_URL));
                startService(intent);
            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(mBroadcastReceiver);
        }



/*
        private boolean initMap() {
        if (myMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            myMap = mapFragment.getMapAsync(context);
        }
        return (myMap != null);
        }
*/


    }