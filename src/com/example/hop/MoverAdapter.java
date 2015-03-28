package com.example.hop;

import java.util.ArrayList;

import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MoverAdapter extends ArrayAdapter<MoverItems> {
    public MoverAdapter(Context context, ArrayList<MoverItems> movers) {
    	
        super(context, 0, movers);
        System.out.println("MoverAdapter, size : " + movers.size());
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
    	 System.out.println("Inside getview");
        // Get the data item for this position
    	 MoverItems mover = getItem(position);    
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_select, parent, false);
        }
        // Lookup view for data population
        TextView moverData = (TextView) convertView.findViewById(R.id.mover_data);
        TextView moverStartTime = (TextView) convertView.findViewById(R.id.mover_start_time);
        TextView moverEndTime = (TextView) convertView.findViewById(R.id.mover_end_time);
        TextView moverDate = (TextView) convertView.findViewById(R.id.mover_date);
        TextView moverLocation = (TextView) convertView.findViewById(R.id.mover_location);
        // Populate the data into the template view using the data object
        moverData.setText("Mover: " + mover.mover);
        moverStartTime.setText("Start Time: " + mover.start_time);
        moverEndTime.setText("End Time: " + mover.end_time);
        moverDate.setText("Date: " + mover.date);
        moverLocation.setText("Location: " + mover.location);
        // Return the completed view to render on screen
        return convertView;
    }
}
