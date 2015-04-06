package com.example.hop;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserDetails extends Activity
  implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
  private static String logtag = "UserDetails Page";
  SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
  ParseObject UserDataStore = new ParseObject("UserDataStore");
  Button buttonSubmit;
  Button logOut;
  Button backToMain;
  private String date_data;
  private String end_time;
  private String location;
  private Spinner spinner1;
  private String start_time;
  private TextView textview1;
  private TextView textview2;
  private TextView textview3;
  private TextView textview4;
  private String user_data;
  
  /*
   * Change to type CustomAutoCompleteView instead of AutoCompleteTextView 
   * since we are extending to customize the view and disable filter
   * The same with the XML view, type will be CustomAutoCompleteView
   */
  CustomAutoCompleteView myAutoComplete1;
   
  // adapter for auto-complete
  ArrayAdapter<String> myAdapter;
   
  // just to add some initial value
  String[] item = new String[] {"Please search..."};
  
  ParseQuery<ParseObject> query;
  static ArrayList<MyObject> usersParsed = new ArrayList<MyObject>();

  public void addListenerOnButtonClick()
  {
	  buttonSubmit = (Button)findViewById(R.id.buttonSubmit); 
    buttonSubmit.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(UserDetails.this.getBaseContext(), UserSelections.class);
        localIntent.putExtra("user", UserDetails.this.user_data);
        localIntent.putExtra("start_time", UserDetails.this.start_time);
        localIntent.putExtra("end_time", UserDetails.this.end_time);
        localIntent.putExtra("date", UserDetails.this.date_data);
        localIntent.putExtra("location", UserDetails.this.location);
        UserDetails.this.startActivityForResult(localIntent, 0);
      }
    });
    
    logOut = (Button)findViewById(R.id.log_out); 
    
    backToMain = (Button)findViewById(R.id.backToMain); 
  }
  
  
/*
  public void addListenerOnSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
	        	user_data = spinner1.getSelectedItem().toString();
	    		UserDataStore.put("user", user_data);
	    		UserDataStore.saveInBackground();

	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }       

	    });
	}
  */
  public void addListenerOnAutoCompleteViewSelection_User()
	{
		try{
          
          myAutoComplete1 = (CustomAutoCompleteView) findViewById(R.id.myautocomplete1);
           
          // add the listener so it will tries to suggest while the user types
          myAutoComplete1.addTextChangedListener(new UserCustomAutoCompleteTextChangedListener(this));
           
          // set our adapter
          myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
          myAutoComplete1.setAdapter(myAdapter);
          
          myAutoComplete1.setOnItemClickListener(new AdapterView.OnItemClickListener() 
			
  		{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					user_data = myAutoComplete1.getText().toString();
					UserDataStore.put("user", UserDetails.this.user_data);
					UserDataStore.saveInBackground();
					
				}

				
          	
  		});
       
      } catch (NullPointerException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
	}
	
//this function is used in CustomAutoCompleteTextChangedListener.java
  public String[] getItemsFromDb_User(String searchTerm){
       
  	searchAllTitlesUsers(searchTerm);
      // add items on the array dynamically
      int rowCount = usersParsed.size();
       
      String[] item = new String[rowCount];
      int x = 0;
       
      for (MyObject record : usersParsed) {
           
          item[x] = record.objectName;
          x++;
      }
       
      return item;
  }

  public static void saveParsedUserData(ArrayList<MyObject> parsed_movers){
		
	  usersParsed.clear();
		for(int i=0;i<parsed_movers.size();i++){
			
			usersParsed.add(i,parsed_movers.get(i));
			
		} 
		System.out.println("Return moversParsed, size : " + usersParsed.size());
		
	}
	
	public void searchAllTitlesUsers(String searchTerm) {
		 
		final ArrayList<MyObject> movers = new ArrayList<MyObject>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("job_titles");
		query.whereContains("Title", searchTerm);
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
				saveParsedUserData(movers);
			}
		});
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
				startActivityForResult(intent, 1);
			}
		});

	}

  protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed  here it is 2
		if(requestCode==1)
		{
			// fetch the location String
			location = data.getStringExtra("address"); 
			textview4.setText(location);
			UserDataStore.put("location", location); 
			UserDataStore.saveInBackground();
		}

	}

  public void onBackPressed()
  {
	  Intent intent = new Intent();
	    setResult(RESULT_OK, intent);
	    finish();
  }

  public void onClick(View paramView)
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_user);
    addListenerOnAutoCompleteViewSelection_User();
    //addListenerOnSpinnerItemSelection();
    addListenerOnTextView();
    addListenerOnButtonClick();
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

  public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

		private OnDateSetListener mDateSetListener;

		public DatePickerDialogFragment() {
			// nothing to see here, move along
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
			UserDataStore.put("date", date_data);
			UserDataStore.saveInBackground();
		}

	}


  
    public class TimePickerFragmentEnd extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {

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
			UserDataStore.put("end_time", end_time);
			UserDataStore.saveInBackground();
		}

	}

    public class TimePickerFragmentStart extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {

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
			UserDataStore.put("start_time", start_time);
			UserDataStore.saveInBackground();
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
		     
		    Intent intent = new Intent(this, MoveMain.class);
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    startActivity(intent);
		    finish();
	  }

}