package com.example.hop;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MoverItems {

	public String mover;
	public String start_time;
	public String end_time;
	public String date;
	public String location;

	public MoverItems(String mover, String start_time,  String end_time,
			String date, String location) {
		super();
		this.mover = mover;
		this.start_time = start_time;
		this.end_time = end_time;
		this.date = date;
		this.location = location;
	}

	public String getMover() {
		return mover;
	}

	public void setMover(String mover) {
		this.mover = mover;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	
}
