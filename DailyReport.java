package com.mti.expensemanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.mti.expensemanager.SimpleGestureFilter;
public class DailyReport extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
	DBclassExp db;
	String price;
	
	String type;
	String Udate;
	String Cmonth;
	String theDay;
	String selDate;
	int yr,mnth,day;
	
	
	int hour,minute;
	TimePicker timePicker;
	DatePicker datePicker;
	 
	static final int TIME_DIALOG_ID=0;
	static final int DATE_DIALOG_ID=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_report);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.shopping_end);
		 detector=new SimpleGestureFilter(this, this);
		EditText editDate=(EditText)findViewById(R.id.txtDailyRDate);
		editDate.setFocusable(false);
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		theDay=day+"/"+(mnth+1)+"/"+yr;
		editDate.setText(theDay);
		
		Button btnShow=(Button)findViewById(R.id.btnDailyRShow);
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				EditText editDate=(EditText)findViewById(R.id.txtDailyRDate);
				selDate=editDate.getText().toString();
				Intent myIntent= new Intent(DailyReport.this,DailyReportDialog.class);
				myIntent.putExtra("date", selDate);
				startActivity(myIntent);
			}
		});
		
		Button btnDate1 =(Button)findViewById(R.id.btnDailyRDate);
		btnDate1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Dialog d1=onCreateDialog(1);
				d1.show();
		
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daily_report, menu);
		return true;
	}
	protected Dialog onCreateDialog(int id)
	{
		switch(id){
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hour, minute, false);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,mDateSetListener,yr,mnth,day);
			
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener=new DatePickerDialog.OnDateSetListener()
	{
		public void onDateSet(
				DatePicker view, int year,int monthOfYear,int dayOfMonth)
		{
		
			yr=year;
			mnth=monthOfYear;
			day=dayOfMonth;
			Toast.makeText(getBaseContext(), "You have selected : "+(mnth+1)+"/"+day+"/"+year, Toast.LENGTH_LONG).show();
			
			EditText editDate=(EditText)findViewById(R.id.txtDailyRDate);
			editDate.setText((mnth+1)+"/"+day+"/"+year);
			
		}
	};
   
	private TimePickerDialog.OnTimeSetListener mTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			// TODO Auto-generated method stub
			hour=hourOfDay;
			minute=minuteOfHour;
			
			SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm aa");
			Date date=new Date(0,0,0, hour,minute);
			String strDate=timeFormat.format(date);
			
			Toast.makeText(getBaseContext(), "You have selected"+strDate, Toast.LENGTH_LONG).show();
		}
	};
	
	public void onClick(View view)
	{
		Toast.makeText(getBaseContext(), "Date selected:"+ (datePicker.getMonth()+1)
				+"/"+datePicker.getDayOfMonth()+"/n"+datePicker.getYear()+"/n"+"Time Selected:"+timePicker.getCurrentHour()+
				":"+timePicker.getCurrentMinute(),Toast.LENGTH_LONG).show();
		
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent me)
	{
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		// TODO Auto-generated method stub
		final MediaPlayer mpSwipe=MediaPlayer.create(this,R.raw.swipe);
		mpSwipe.start();
		if(direction==SimpleGestureFilter.SWIPE_RIGHT)
		{
			Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(DailyReport.this,ReportMenu.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(DailyReport.this,Home1Activity.class);
		startActivity(intent);
	}

}
