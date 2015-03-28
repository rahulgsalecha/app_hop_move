package com.example.hop;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MoveMain extends Activity
{
  private static String logtag = "Main App";
  Button buttonMover;
  Button buttonUser;

  public void addListenerOnButton() {
  	
  	Button buttonMover = (Button)findViewById(R.id.buttonMover);        
      Button buttonUser = (Button)findViewById(R.id.buttonUser);  
      
      buttonMover.setOnClickListener(new OnClickListener() {
      	public void onClick(View v){
      		Intent intent = new Intent(getBaseContext(), MoverDetails.class);
              //startActivity(intent);
              startActivityForResult(intent, 0);
      	}
      });
      
      buttonUser.setOnClickListener(new OnClickListener() {
      	public void onClick(View v){
      		Intent intent = new Intent(getBaseContext(), UserDetails.class);
              //startActivity(intent);   
              startActivityForResult(intent, 0);
      	}
      });
      
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      switch (requestCode) {
      case 0:
      case 1:
          if(resultCode == RESULT_OK) {
          	addListenerOnButton();
          } 
          if (resultCode == RESULT_CANCELED) {    
              //Do nothing
          }
       break;
      }
      }


  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    addListenerOnButton();
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
	Log.d(logtag, "onRestart() called");
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
