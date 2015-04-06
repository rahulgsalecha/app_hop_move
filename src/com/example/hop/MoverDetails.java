package com.example.hop;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MoverDetails extends Activity
implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
	private static String logtag = "MoverDetails Page";
	SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	ParseObject MoverDataStore_1 = new ParseObject("MoverDataStore_1");
	ParseObject UserDetails = new ParseObject("UserDetails");
	Button buttonSubmit;
	Button logOut;
	private String date_data;
	private String end_time;
	private String location;
	private LatLng mover_latlong;
	private String mover_data;
	private String start_time;
	private String mover_name;
	private String mover_phone;
	private String mover_email;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	
	/*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView 
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */
     CustomAutoCompleteView myAutoComplete;
     
    // adapter for auto-complete
     ArrayAdapter<String> myAdapter;
     
    // just to add some initial value
     String[] item = new String[] {"Please search..."};
    
     ParseQuery<ParseObject> query;
     
     static ArrayList<MyObject> moversParsed = new ArrayList<MyObject>();
	
	public void addListenerOnAutoCompleteViewSelection()
	{
		try{
            
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);
             
            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
             
            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);
            
            myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() 
			
    		{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					mover_data = myAutoComplete.getText().toString();
					MoverDataStore_1.put("mover", MoverDetails.this.mover_data);
					MoverDataStore_1.saveInBackground();
					
				}

				
            	
    		});
         
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){
         
    	searchAllTitles(searchTerm);
        // add items on the array dynamically
        int rowCount = moversParsed.size();
         
        String[] item = new String[rowCount];
        int x = 0;
         
        for (MyObject record : moversParsed) {
             
            item[x] = record.objectName;
            x++;
        }
        return item;
    }

	public void addListenerOnTextView()
	{
		textview1 = (TextView) findViewById(R.id.startTime);
		textview2 = (TextView) findViewById(R.id.endTime);
		textview3 = (TextView) findViewById(R.id.date);
		textview4 = (TextView) findViewById(R.id.location);


		textview1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragmentStart();
				newFragment.show(getFragmentManager(), "timePicker");
			}

		});

		textview2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DialogFragment newFragment1 = new TimePickerFragmentEnd();
				newFragment1.show(getFragmentManager(), "timePicker1");
			}

		});

		textview3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerDialogFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}


		});

		textview4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), LocationDetails.class);
				intent.putExtra("type", "user");
				startActivityForResult(intent, 2);
			}
		});
	}

	protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
	{
		super.onActivityResult(paramInt1, paramInt2, paramIntent);
		if (paramInt1 == 2)
		{
			location = paramIntent.getStringExtra("address");
			MoverDataStore_1.put("location", location);
			mover_latlong = getLocationFromAddress(location);
			ParseGeoPoint mover_point = new ParseGeoPoint(mover_latlong.latitude, mover_latlong.longitude); 
			MoverDataStore_1.put("mover_point", mover_point);
			MoverDataStore_1.saveInBackground();
			textview4.setText("Location: " + location);
		}
	}

	public void onBackPressed()
	{
		setResult(RESULT_OK, new Intent());
		finish();
	}

	public void onClick(View paramView)
	{
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

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_mover);
		logOut = (Button)findViewById(R.id.log_out); 
	    
	    buttonSubmit = (Button)findViewById(R.id.Submit);
		addListenerOnAutoCompleteViewSelection();
		//addListenerOnSpinnerItemSelection();
		addListenerOnTextView();
	}

	public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
	{
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

	public void onTimeSet(TimePicker paramTimePicker, int paramInt1, int paramInt2)
	{
	}

	public class DatePickerDialogFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener
	{
		private DatePickerDialog.OnDateSetListener mDateSetListener;

		public DatePickerDialogFragment()
		{
		}

		public DatePickerDialogFragment(OnDateSetListener callback) {
			mDateSetListener = (OnDateSetListener) callback;
		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar cal = Calendar.getInstance();

			return new DatePickerDialog(getActivity(),
					this, cal.get(Calendar.YEAR), 
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		} 

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
			textview3.setText(DATE_FORMATTER.format(cal.getTime()));
			date_data = textview3.getText().toString();
			MoverDataStore_1.put("date", MoverDetails.this.date_data);
			MoverDataStore_1.saveInBackground();
			textview3.setText("Date Available: " + DATE_FORMATTER.format(cal.getTime()));
		}
	}

	public class TimePickerFragmentEnd extends DialogFragment
	implements TimePickerDialog.OnTimeSetListener
	{
		private OnTimeSetListener mTimeSetListener;

		public TimePickerFragmentEnd() {
			// nothing to see here, move along
		}

		public TimePickerFragmentEnd(OnTimeSetListener callback) {
			mTimeSetListener = (OnTimeSetListener) callback;
		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar cal = Calendar.getInstance();

			return new TimePickerDialog(getActivity(), this, 
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			textview2.setText(""+hourOfDay+":"+minute);
			end_time = textview2.getText().toString();
			MoverDataStore_1.put("end_time", MoverDetails.this.end_time);
			MoverDataStore_1.saveInBackground();
			textview2.setText("End Time is : " +""+hourOfDay+":"+minute);
		}
	}

	public class TimePickerFragmentStart extends DialogFragment
	implements TimePickerDialog.OnTimeSetListener
	{

		private OnTimeSetListener mTimeSetListener;

		public TimePickerFragmentStart() {
			// nothing to see here, move along
		}

		public TimePickerFragmentStart(OnTimeSetListener callback) {
			mTimeSetListener = (OnTimeSetListener) callback;
		}

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar cal = Calendar.getInstance();

			return new TimePickerDialog(getActivity(), this, 
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			textview1.setText(""+hourOfDay+":"+minute);
			start_time = textview1.getText().toString();
			MoverDataStore_1.put("start_time", MoverDetails.this.start_time);
			MoverDataStore_1.saveInBackground();
			textview1.setText("Start Time is : " + ""+hourOfDay+":"+minute);
		}
	}
	
	private void navigateToLogin() {
	    // Launch the login activity
		
		
	    Intent intent = new Intent(this, LoginActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    startActivity(intent);
	    finish();
	}
  
  public void logOut(final View v){
		v.setEnabled(false);
		ParseUser.logOut();
		navigateToLogin();	
  }
  
  public void navigateToMain(final View v) {
	  v.setEnabled(false);
	// Launch the main activity
	  ParseUser currentUser = ParseUser.getCurrentUser();
	  setMoverDetails(currentUser.getUsername());
	  
	    Intent intent = new Intent(this, MoveMain.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    startActivity(intent);
	    finish();
  }
	
	public static void saveParsedMoverData(ArrayList<MyObject> parsed_movers){
		
		moversParsed.clear();
		for(int i=0;i<parsed_movers.size();i++){
			
			moversParsed.add(i,parsed_movers.get(i));
			
		} 
		System.out.println("Return moversParsed, size : " + moversParsed.size());
		
	}
	
	public void searchAllTitles(String searchTerm) {
		 
		final ArrayList<MyObject> movers = new ArrayList<MyObject>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("job_titles");
		query.whereContains("Title", searchTerm);
		//query.whereStartsWith("Title", searchTerm);
		query.orderByAscending("Title");
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
							movers.add(new MyObject(moverList.get(i).getString("Title")));
							i++;
							
						}
					}
					
				}
				System.out.println("Movers, size : " + movers.size());
				saveParsedMoverData(movers);
			}
		});
	}
	
	public void setMoverDetails(String username) {
		System.out.println("setMoverDetails: username" + username);
		 
		ParseQuery<ParseObject> mover_details_query = ParseQuery.getQuery("UserDetails");
		mover_details_query.whereEqualTo("username", username);
		mover_details_query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> moverList, ParseException e) {
				if (e == null && moverList != null)
				{
					if(!(moverList.isEmpty()))
					{
						int size = moverList.size();
						System.out.println("setMoverDetails: moverList.size() :  "+ moverList.size());
						int i=0;
						while (i < size) 
						{	  
							mover_name = moverList.get(i).getString("name");
							mover_phone = moverList.get(i).getString("phone");
							mover_email = moverList.get(i).getString("email");
							
							System.out.println("setMoverDetails: mover_name :  "+ mover_name);
							System.out.println("setMoverDetails: mover_phone :  "+ mover_phone);
							System.out.println("setMoverDetails: mover_email :  "+ mover_email);
							
							 MoverDataStore_1.put("mover_name", MoverDetails.this.mover_name);
							  MoverDataStore_1.put("mover_phone", MoverDetails.this.mover_phone);
							  MoverDataStore_1.put("mover_email", MoverDetails.this.mover_email);
							  MoverDataStore_1.saveInBackground();
							  
							i++;
						}
					}
					
				}
			}
		});
	}
}
