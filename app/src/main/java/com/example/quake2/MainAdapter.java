package com.example.quake2;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quake2.models.EarthQuakeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    public static final String TAG = "MyService";
    public static final String QUAKE_ID_KEY = "quake_id_key";

    private Context context;

    public MainAdapter(Context ctxt, ArrayList<EarthQuakeModel> earthquakes) {
        this.context = ctxt;
        this.earthquakes = earthquakes;
    }


    List<EarthQuakeModel> earthquakes;

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowofdata, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder viewHolder, int i) {

        final EarthQuakeModel earthquake = earthquakes.get(i);

        Double checkSeverity;
        Integer checkDepth;

        checkDepth = earthquake.getDepthNumber();

        checkSeverity = earthquake.getSeverity();

        if (checkDepth < 5) {

            viewHolder.showDepth.setBackgroundResource(R.color.shallow);
            //viewHolder.severity.setBackgroundColor(Color.BLUE);
        }

        if (checkDepth >= 5 && checkDepth <=10) {

            viewHolder.showDepth.setBackgroundResource(R.color.intermediate);
            //viewHolder.severity.setBackgroundColor(Color.BLUE);
        }
        if (checkDepth > 10) {

            viewHolder.showDepth.setBackgroundResource(R.color.deep);
            viewHolder.showDepth.setTextColor(context.getResources().getColor(R.color.lightText));
            //viewHolder.severity.setBackgroundColor(Color.BLUE);
        }


        if (checkSeverity <= 1) {
            viewHolder.depth.setText("Notes - Small reading");
          viewHolder.magnitude.setBackgroundResource(R.color.lowSeverity);
            //viewHolder.severity.setBackgroundColor(Color.BLUE);
        }
        if (checkSeverity > 1 && checkSeverity <=2) {
            viewHolder.depth.setText("Notes - Mild Rumble");
            viewHolder.magnitude.setBackgroundResource(R.color.medSeverity);
        }
        if (checkSeverity > 2 ) {
            viewHolder.depth.setText("Notes - Significant quake");
            viewHolder.magnitude.setBackgroundResource(R.color.highSeverity);
        }
        Log.i(TAG, "onReceive: "+ Thread.currentThread());
        viewHolder.magnitude.setText(earthquake.getMagnitude()+" Magnitude recorded");

        viewHolder.title.setText("Location - "+earthquake.getLocation());
        viewHolder.showDepth.setText("Depth of quake "+earthquake.getDepthNumber()+"km");

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuakeDetail.class);
                Log.d("check intent working", earthquake.getDepth());
                intent.putExtra("QUAKE_KEY",earthquake);
                viewHolder.view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

               return earthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView magnitude;
        TextView depth;
        TextView severity;
        TextView showDepth;

        View view;
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rTitle);
            showDepth = itemView.findViewById(R.id.showDepth);
            magnitude = itemView.findViewById(R.id.rMagnitude);
            depth = itemView.findViewById(R.id.rDepth);
            severity = itemView.findViewById(R.id.severityIndicator);
            Log.i(TAG, "onReceive: "+ Thread.currentThread());
            view = itemView;
        }
    }

    }

