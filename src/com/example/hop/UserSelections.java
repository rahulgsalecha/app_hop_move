package com.example.hop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UserSelections extends Activity
{
	static ArrayList<MoverItems> moversParsed = new ArrayList<MoverItems>();
	private static String logtag = "User Selections Page";
	static String user_data;
	static String user_start_time;
	static String user_end_time;
	static String user_date;
	static String user_location;
	static LatLng user_latlong;
	MoverAdapter adapter;



	public void onBackPressed()
	{
		setResult(-1, new Intent());
		finish();
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_list);
		Intent localIntent = getIntent();

		user_data = localIntent.getStringExtra("user");
		user_start_time = localIntent.getStringExtra("start_time");
		user_end_time = localIntent.getStringExtra("end_time");
		user_date = localIntent.getStringExtra("date");
		user_location = localIntent.getStringExtra("location");
		user_latlong = getLocationFromAddress(user_location);

		populateMoversList();
	}

	private void populateMoversList() {

		// Construct the data source
		
		//ArrayList<MoverItems> arrayOfMovers = moversParsed;
		// Create the adapter to convert the array to views
		adapter = new MoverAdapter(this, moversParsed);
		parseAllMovers();
		// Attach the adapter to a ListView
		final ListView listView = (ListView) findViewById(R.id.lvUsers);
		listView.setAdapter(adapter);
	}

	public static void saveParsedMoverData(ArrayList<MoverItems> parsed_movers){
		moversParsed.clear();
		for(int i=0;i<parsed_movers.size();i++){
			moversParsed.add(i,parsed_movers.get(i));
			
		} 
		System.out.println("Return moversParsed, size : " + moversParsed.size());
		
	}

	public void parseAllMovers() {
		ParseGeoPoint user_point = new ParseGeoPoint(user_latlong.latitude, user_latlong.longitude);  
		final ArrayList<MoverItems> movers = new ArrayList<MoverItems>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MoverDataStore_1");
		query.whereEqualTo("mover", user_data);
		query.whereEqualTo("date", user_date);
		//query.whereGreaterThanOrEqualTo("start_time", user_start_time);
		//query.whereLessThanOrEqualTo("end_time", user_end_time);
		query.whereWithinMiles("mover_point", user_point, 25);
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> moverList, ParseException e) {
				if (e == null && moverList != null)
				{
					if(!(moverList.isEmpty()))
					{
						int size = moverList.size();
						int i=0;
						while (i < size) 
						{	        		
							movers.add(new MoverItems(moverList.get(i).getString("mover"),
									moverList.get(i).getString("start_time"),
									moverList.get(i).getString("end_time"),
									moverList.get(i).getString("date"),
									moverList.get(i).getString("location"),
									moverList.get(i).getParseGeoPoint("mover_point"),
									moverList.get(i).getString("mover_name"),
									moverList.get(i).getString("mover_phone"),
									moverList.get(i).getString("mover_email")));
							i++;
							
						}
						adapter.notifyDataSetChanged();
					}
					
				}
				System.out.println("Movers, size : " + movers.size());
				saveParsedMoverData(movers);
			}
		});
	}
	
	public LatLng getLocationFromAddress(String strAddress) {

	    Geocoder coder = new Geocoder(this);
	    List<Address> address;
	    LatLng p1 = null;

	    try {
	        address = coder.getFromLocationName(strAddress, 5);
	        if (address == null) {
	            return null;
	        }
	        Address location = address.get(0);
	        location.getLatitude();
	        location.getLongitude();

	        p1 = new LatLng(location.getLatitude(), location.getLongitude() );

	    } catch (Exception ex) {

	        ex.printStackTrace();
	    }

	    return p1;
	}
	
	protected void onDestroy()
	{
		Log.d(logtag, "onDestroy() called");
		super.onDestroy();
	}

	protected void onPause()
	{
		Log.d(logtag, "onPause() called");
		super.onPause();
	}

	protected void onRestart()
	{
		super.onRestart();
	}

	protected void onResume()
	{
		Log.d(logtag, "onResume() called");
		super.onResume();
	}

	protected void onStart()
	{
		Log.d(logtag, "onStart() called");
		super.onStart();
	}

	protected void onStop()
	{
		Log.d(logtag, "onStop() called");
		super.onStop();
	}
}
