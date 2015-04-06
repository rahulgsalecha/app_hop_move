package com.example.hop;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

	private EditText mUsernameField;
	private EditText mPasswordField;
	private EditText mNameField;
	private EditText mPhoneField;
	private EditText mEmailField;
	private TextView mErrorField;
	ParseObject UserDetails = new ParseObject("UserDetails");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mUsernameField = (EditText) findViewById(R.id.register_username);
		mPasswordField = (EditText) findViewById(R.id.register_password);
		mNameField = (EditText) findViewById(R.id.register_name);
		mPhoneField = (EditText) findViewById(R.id.register_phone);
		mEmailField = (EditText) findViewById(R.id.register_email);
		mErrorField = (TextView) findViewById(R.id.error_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register(final View v){
		if(mUsernameField.getText().length() == 0 || mPasswordField.getText().length() == 0)
			return;

		v.setEnabled(false);
		ParseUser user = new ParseUser();
		user.setUsername(mUsernameField.getText().toString());
		user.setPassword(mPasswordField.getText().toString());
		UserDetails.put("name", mNameField.getText().toString());
		UserDetails.put("phone", mPhoneField.getText().toString());
		UserDetails.put("email", mEmailField.getText().toString());
		UserDetails.put("username", mUsernameField.getText().toString());
		mErrorField.setText("");

		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					UserDetails.saveInBackground();
					Intent intent = new Intent(RegisterActivity.this, MoveMain.class);
					startActivity(intent);
					finish();
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					switch(e.getCode()){
					case ParseException.USERNAME_TAKEN:
						mErrorField.setText("Sorry, this username has already been taken.");
						break;
					case ParseException.USERNAME_MISSING:
						mErrorField.setText("Sorry, you must supply a username to register.");
						break;
					case ParseException.PASSWORD_MISSING:
						mErrorField.setText("Sorry, you must supply a password to register.");
						break;
					default:
						mErrorField.setText(e.getLocalizedMessage());
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void showLogin(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}