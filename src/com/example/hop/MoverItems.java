package com.example.hop;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MoverItems {

	public String mover;
	public String start_time;
	public String end_time;
	public String date;
	public String location;
	public ParseGeoPoint mover_point;
	public String mover_name;
	public String mover_phone;
	public String mover_email;

	public MoverItems(String mover, String start_time,  String end_time,
			String date, String location, ParseGeoPoint parseGeoPoint, String mover_name, String mover_phone, String mover_email) {
		super();
		this.mover = mover;
		this.start_time = start_time;
		this.end_time = end_time;
		this.date = date;
		this.location = location;
		this.mover_point = parseGeoPoint;
		this.mover_name = mover_name;
		this.mover_phone = mover_phone;
		this.mover_email = mover_email;
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

	public ParseGeoPoint getMover_point() {
		return mover_point;
	}

	public void setMover_point(ParseGeoPoint mover_point) {
		this.mover_point = mover_point;
	}

	public String getMover_name() {
		return mover_name;
	}

	public void setMover_name(String mover_name) {
		this.mover_name = mover_name;
	}

	public String getMover_phone() {
		return mover_phone;
	}

	public void setMover_phone(String mover_phone) {
		this.mover_phone = mover_phone;
	}

	public String getMover_email() {
		return mover_email;
	}

	public void setMover_email(String mover_email) {
		this.mover_email = mover_email;
	}
	
	
	
	
}
