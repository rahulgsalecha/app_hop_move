package com.example.hop;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

public class UserCustomAutoCompleteTextChangedListener implements TextWatcher{
	 
    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;
     
    public UserCustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }
     
    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
 
        // if you want to see in the logcat what the user types
        Log.e(TAG, "User input: " + userInput);
 
        UserDetails userActivity = ((UserDetails) context);
         
        // query the database based on the user input
        userActivity.item = userActivity.getItemsFromDb_User(userInput.toString());
         
        // update the adapter
        userActivity.myAdapter.notifyDataSetChanged();
        userActivity.myAdapter = new ArrayAdapter<String>(userActivity, android.R.layout.simple_dropdown_item_1line, userActivity.item);
        userActivity.myAutoComplete1.setAdapter(userActivity.myAdapter);
         
    }
 
}