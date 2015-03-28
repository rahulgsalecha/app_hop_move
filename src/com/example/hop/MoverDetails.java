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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Application;
import com.parse.ParseObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MoverDetails extends Activity
implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
	private static String logtag = "MoverDetails Page";
	SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	ParseObject MoverDataStore_1 = new ParseObject("MoverDataStore_1");
	private String date_data;
	private String end_time;
	private String location;
	private String mover_data;
	private Spinner spinner1;
	private String start_time;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;

	public void addListenerOnSpinnerItemSelection()
	{
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
			{
				mover_data = MoverDetails.this.spinner1.getSelectedItem().toString();
				MoverDataStore_1.put("mover", MoverDetails.this.mover_data);
				MoverDataStore_1.saveInBackground();
			}

			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
			{
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
			textview4.setText(location);
			MoverDataStore_1.put("location", location);
			MoverDataStore_1.saveInBackground();
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

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_mover);
		addListenerOnSpinnerItemSelection();
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
		}
	}
}
