package com.example.hop;

import com.parse.Parse;

public class Application extends android.app.Application
{
	public void onCreate()
	{
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "wvBWFi9PMR1pKcabL2qCfhADBYbI2mJynatHo1WD", "bkC9E1XGGyq2GTDl9f7nkYCngAGvMVbxx2yppCKa");
		
		
	}
}
