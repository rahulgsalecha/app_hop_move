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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.parse.ParseObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserDetails extends Activity
  implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
  private static String logtag = "UserDetails Page";
  SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
  ParseObject UserDataStore = new ParseObject("UserDataStore");
  Button buttonSubmit;
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
  }

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
    addListenerOnSpinnerItemSelection();
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

}