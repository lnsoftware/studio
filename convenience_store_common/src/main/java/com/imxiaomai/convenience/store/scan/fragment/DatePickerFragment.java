package com.imxiaomai.convenience.store.scan.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	private DateListener dateListener;
	
	public static DatePickerFragment newInstance() {
		DatePickerFragment datePickerFragment = new DatePickerFragment();
		return datePickerFragment;
	}
	
	public void setDateListener(DateListener dateListener) {
		this.dateListener = dateListener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		dateListener.setDate(calendar.getTime());
	}
	
	public interface DateListener {
		void setDate(Date date); 
	}
}