package com.mti.expensemanager;

import java.util.Calendar;

import com.mti.Income_Savings_DB.DBclassExp2;
import com.mti.SM_DB.DBclassExp3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.mti.expensemanager.SimpleGestureFilter;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

public class SM_1Activity extends Activity implements SimpleGestureListener  {
	private SimpleGestureFilter detector;
String Endate;
String UserDate;
String smPlace;
String smFund;
String Mspent;
int yr,mnth,day;
int hour,minute;
String theDay;
String row1;
String incSav;
String incDate;
String incomeAvail;
DBclassExp3 db;
DBclassExp2 db2;
TimePicker timePicker;
DatePicker datePicker;
SharedPreferences settings;
static final int TIME_DIALOG_ID=0;
static final int DATE_DIALOG_ID=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_1);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.shopping_start);
		 detector=new SimpleGestureFilter(this, this);
		db = new DBclassExp3(this);
		db2=new DBclassExp2(this);
		 PreferenceManager.setDefaultValues(this, R.xml.my_settings,false);     
		 settings=PreferenceManager.getDefaultSharedPreferences(this);
		datePicker=(DatePicker)findViewById(R.id.datePicker);
		
		EditText editText=(EditText)findViewById(R.id.txtsmdate);
		editText.setFocusable(false);
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		theDay=(day+"/"+(mnth+1)+"/"+yr);
		editText.setText(theDay);
		//editText.setText( String.valueOf(date.getDate()));
		Button btnsmdate=(Button)findViewById(R.id.btnsmdate);
		btnsmdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Dialog d1=onCreateDialog(1);
				d1.show();
			}
		});
		Button btnStart=(Button)findViewById(R.id.btnsmstart);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				 Endate=theDay;
				 EditText editText=(EditText)findViewById(R.id.txtsmdate);
				 UserDate=editText.getText().toString();
				 EditText editText2=(EditText)findViewById(R.id.txtsmplace);
				 smPlace=editText2.getText().toString();
				 EditText editText3=(EditText)findViewById(R.id.txtsmfund);
				 smFund=editText3.getText().toString();
				 Mspent="0";
				 if(settings.getBoolean("IncomeDecrement", false))
					{
					 db2.open();
					 Cursor c=db2.getAllTitles();
					 if(c.moveToLast())
					 {
						 do
						 {
							 row1=c.getString(0);
							 incDate=c.getString(1);
							incomeAvail=c.getString(2);
							incSav=c.getString(3);
						
							// Toast.makeText(this,"id: "+c.getString(0) + "\n" +"EntryDate: "+c.getString(1) + "\n" +"UserDate: "+c.getString(2) + "\n" +"place: "+c.getString(3) + "\n"+"Fund: " +c.getString(4)+"\n"+"Spent Money:"+c.getString(5) ,Toast.LENGTH_LONG).show();
						 }while(c.moveToNext());
					 }
					 db2.close();
					 if(Double.valueOf(smFund)>Double.valueOf(incomeAvail))
					 {
						 
						 
								Dialog d1=onCreateDialog2(1);
								d1.show();
							}
					 else{
				 db.open();
				 long num;
		            num = db.insertmaster(Endate.toString(),UserDate.toString(),smPlace.toString(),smFund.toString(),Mspent.toString());
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
				 
				Intent intent=new Intent(SM_1Activity.this,SM_2Activity.class);
				startActivity(intent);
					 }
			}
				 else{
					 db.open();
					 long num;
			            num = db.insertmaster(Endate.toString(),UserDate.toString(),smPlace.toString(),smFund.toString(),Mspent.toString());
			            db.close();
			            if (num > 0)
			                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
					 
					Intent intent=new Intent(SM_1Activity.this,SM_2Activity.class);
					startActivity(intent);
				 }
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm_1, menu);
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
			Toast.makeText(getBaseContext(), "You have selected : "+day+"/"+(mnth+1)+"/"+year, Toast.LENGTH_LONG).show();
			
			EditText editDate=(EditText)findViewById(R.id.txtsmdate);
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
			Intent intent=new Intent(SM_1Activity.this,Home1Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(SM_1Activity.this,Home1Activity.class);
		startActivity(intent);
	}
	protected Dialog onCreateDialog2(int id)
	{
		//db=new DBclassExp3(this);
		switch(id){
		case 1:
			Builder builder=new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.face);
			builder.setTitle("Available money is insufficient !!!!");
			builder.setPositiveButton("Change Fund amount",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
					
					
				}
			
			});
			builder.setNegativeButton("Quit and Change Settings", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(SM_1Activity.this,SettingsActivity.class);
					startActivity(intent);
				}
			});
			return builder.create();
			
		}return null;
	}
}
