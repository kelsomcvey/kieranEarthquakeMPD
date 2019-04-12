package com.example.quake2;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.quake2.models.EarthQuakeModel;
import com.example.quake2.myservices.MyService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class QuakeDetail extends AppCompatActivity {

    TextView titleDetail;
    TextView magDetail;
    TextView depthDetail;
    TextView severity;
    TextView longDetail;
    TextView latDetail;
    TextView pubDetail;
    TextView showDepthColour;

    TextView magKeyHeader;
    TextView depthKeyHeader;
    TextView depthKeyLow;
    TextView depthKeyHigh;
    TextView depthKeyMed;
    TextView magKeyHigh;
    TextView magKeyLow;
    TextView magKeyMed;



    public static final String TAG = "MyService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_detail);
        //setContentView(R.layout.activitymap);

        EarthQuakeModel item = getIntent().getExtras().getParcelable("QUAKE_KEY");
        if (item == null) {
            Log.i("wdawwd", "no individual details found");
        }
        Log.d("wdawwd1", item.getItemId());
//        Log.d("wdawwd", earthquake.getDepth());

        titleDetail = findViewById(R.id.oneTitle);
        magDetail = findViewById(R.id.oneMagnitude);
        depthDetail = findViewById(R.id.oneDepth);
        severity = findViewById(R.id.severityIndicator);
        latDetail = findViewById(R.id.oneLat);
        longDetail = findViewById(R.id.oneLong);
        pubDetail = findViewById(R.id.onePub);
        showDepthColour = findViewById(R.id.depthColour);

        magKeyHeader = findViewById(R.id.magKey);
        depthKeyHeader = findViewById(R.id.depthKey);

        depthKeyLow = findViewById(R.id.lowColour);
        depthKeyLow.setBackgroundResource(R.color.shallow);


        depthKeyHigh = findViewById(R.id.highColour);
        depthKeyHigh.setBackgroundResource(R.color.deep);
        depthKeyHigh.setTextColor(getResources().getColor(R.color.lightText));

        depthKeyMed = findViewById(R.id.medColour);
        depthKeyMed.setBackgroundResource(R.color.intermediate);

        magKeyHigh = findViewById(R.id.deepColour);
        magKeyHigh.setBackgroundResource(R.color.highSeverity);
        magKeyHigh.setTextColor(getResources().getColor(R.color.lightText));


        magKeyLow = findViewById(R.id.shallowColour);
        magKeyLow.setBackgroundResource(R.color.medSeverity);


        magKeyMed = findViewById(R.id.interColour);
        magKeyMed.setBackgroundResource(R.color.lowSeverity);



        Double checkSeverity;

        checkSeverity = item.getSeverity();
        if (checkSeverity < 1) {
            depthDetail.setText("Small reading reported");
            severity.setBackgroundResource(R.color.lowSeverity);
        }
        if (checkSeverity >= 1 && checkSeverity <=2) {
            depthDetail.setText("Mild Rumble reported");
            severity.setBackgroundResource(R.color.medSeverity);
        }
        if (checkSeverity > 2 ) {
            depthDetail.setText("Significant quake reported");
            severity.setBackgroundResource(R.color.highSeverity);
        }

        Integer checkDepth;
        checkDepth = item.getDepthNumber();

        if (checkDepth >10){
            showDepthColour.setBackgroundResource(R.color.deep);
            showDepthColour.setText("Deep");
            showDepthColour.setTextColor(getResources().getColor(R.color.lightText));
        }
        if (checkDepth >= 5 && checkDepth <=10){
            showDepthColour.setBackgroundResource(R.color.intermediate);
            showDepthColour.setText("Intermediate depth");
            //showDepthColour.setTextColor(getResources().getColor(R.color.lightText));
        }
        if (checkDepth <5){
            showDepthColour.setBackgroundResource(R.color.shallow);
            //showDepthColour.setBackgroundResource(R.drawable.roundedborder);
            showDepthColour.setText("Shallow");

        }


        Log.i(TAG, "onReceive: "+ Thread.currentThread());
        titleDetail.setText("Location "+item.getLocation());
        magDetail.setText("Magnitude reported as "+item.getMagnitude()+"M");
        pubDetail.setText("Reported- "+item.getPublishedDate());
        longDetail.setText("Longitude "+item.getLongNumber());
        latDetail.setText("Latitude "+item.getLatNumber());
        //pubDetail.setText(item.getPublishedDate());
        depthDetail.setText("Depth of earthquake "+item.getDepth()+"km");

    }
    /*
    private boolean initMap() {
        if (myMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            myMap = mapFragment.getMapAsync(context);
        }
        return (myMap != null);
    }*/
}
