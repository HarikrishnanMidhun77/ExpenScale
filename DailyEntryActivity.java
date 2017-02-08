package com.mti.expensemanager;

import com.mti.Income_Savings_DB.DBclassExp2;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.*;
//import java.sql.Date;
import java.util.Calendar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.net.MailTo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import android.widget.AdapterView.OnItemLongClickListener;

public class DailyEntryActivity extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	String[] itemTypes_array;
	String EntryDate;
	String UserDate;
	String ItemType;
	String ItemPrice;
	String ItemPlace;
	String ItemDescrip;
	String theDay;
	String row1;
	String incSav;
	String incDate;
	String incomeAvail;
	int yr,mnth,day;
	int hour,minute;
	DBclassExp db;
	DBclassExp2 db2;
	TimePicker timePicker;
	DatePicker datePicker;
	 
	static final int TIME_DIALOG_ID=0;
	static final int DATE_DIALOG_ID=1;
	SharedPreferences settings;
	 MediaPlayer mp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_entry);
		 mp=MediaPlayer.create(this,R.raw.every_move);
		 detector=new SimpleGestureFilter(this, this);
		 
		 PreferenceManager.setDefaultValues(this, R.xml.my_settings,false);     
		 settings=PreferenceManager.getDefaultSharedPreferences(this);
		itemTypes_array=getResources().getStringArray(R.array.expence_types);
		Spinner spinner=(Spinner)findViewById(R.id.spnType);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemTypes_array );
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index=arg0.getSelectedItemPosition();
				EditText editText5=(EditText)findViewById(R.id.txtTypeEntry);
			    editText5.setText(itemTypes_array[index]);
			    editText5.setFocusable(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				}
		});
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		
		theDay=day+"/"+(mnth+1)+"/"+yr;//day+"/"+(mnth+1)+"/"+yr
		EditText editdate=(EditText)findViewById(R.id.txtDateEntry);
		editdate.setFocusable(false);
		editdate.setText(theDay);
	
		 db = new DBclassExp(this);
		db2=new DBclassExp2(this);
	
	Button sub_btn=(Button)findViewById(R.id.btnEntrySubmit) ;
	sub_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			mp.start();
			EntryDate=theDay;
			EditText editText4=(EditText)findViewById(R.id.txtDateEntry);
			UserDate=editText4.getText().toString();
			EditText editText5=(EditText)findViewById(R.id.txtTypeEntry);
			ItemType=editText5.getText().toString();
			EditText editText1=(EditText)findViewById(R.id.txtPrice);
			ItemPrice=editText1.getText().toString();
		EditText editText2=(EditText)findViewById(R.id.txtPlace);
			ItemPlace=editText2.getText().toString();
			EditText editText3=(EditText)findViewById(R.id.txtDesc);
			ItemDescrip=editText3.getText().toString();
			//expenseMangerDb.createReminder(EntryDate,UserDate, ItemType, ItemPrice, ItemPlace, ItemDescrip);
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
			 if(Double.valueOf(ItemPrice)>Double.valueOf(incomeAvail))
			 {
						Dialog d1=onCreateDialog2(1);
						d1.show();
					}
			 else{
				 incomeAvail=String.valueOf(Double.valueOf(incomeAvail)-Double.valueOf(ItemPrice));
				 db2.updateRow(Long.valueOf(row1), incDate, incomeAvail, incSav);
				 Toast.makeText(getApplicationContext(), "Money in hand decremented",Toast.LENGTH_LONG).show();
			 }
			 db.open();
			 long num;
	            num = db.insertmaster(EntryDate,UserDate, ItemType, ItemPrice, ItemPlace, ItemDescrip);
	            db.close();
	            if (num > 0)
	                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
	            Toast.makeText(getApplicationContext(), "Row number: " + UserDate, 2000).show();
			}
			else{
				
				 db.open();
				 long num;
		            num = db.insertmaster(EntryDate,UserDate, ItemType, ItemPrice, ItemPlace, ItemDescrip);
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
		            Toast.makeText(getApplicationContext(), "Row number: " + UserDate, 2000).show();
			}
			editText1.setText("");
			editText2.setText("");
			editText3.setText("");
		}
		
	});
	
	Button btnDate =(Button)findViewById(R.id.btnDateEntry);
	btnDate.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Dialog d1=onCreateDialog(1);
			d1.show();
	
		}
	});
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
			
			EditText editDate=(EditText)findViewById(R.id.txtDateEntry);
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
			Intent intent=new Intent(DailyEntryActivity.this,Home1Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(DailyEntryActivity.this,Home1Activity.class);
		startActivity(intent);
	}
	protected Dialog onCreateDialog2(int id)
	{
		db=new DBclassExp(this);
		switch(id){
		case 1:
			Builder builder=new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.face);
			builder.setTitle("Available money is insufficient !!!!");
			builder.setPositiveButton("Allow -ve balance",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
					
					//mp.start();
					/*EntryDate=theDay;
					EditText editText4=(EditText)findViewById(R.id.txtDateEntry);
					UserDate=editText4.getText().toString();
					EditText editText5=(EditText)findViewById(R.id.txtTypeEntry);
					ItemType=editText5.getText().toString();
					EditText editText1=(EditText)findViewById(R.id.txtPrice);
					ItemPrice=editText1.getText().toString();
					EditText editText2=(EditText)findViewById(R.id.txtPlace);
					ItemPlace=editText2.getText().toString();
					EditText editText3=(EditText)findViewById(R.id.txtDesc);
					ItemDescrip=editText3.getText().toString();*/
					// Toast.makeText(getApplicationContext(), "here",Toast.LENGTH_LONG).show();
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
							Toast.makeText(getApplicationContext(), ItemPrice +"'"+incomeAvail,Toast.LENGTH_LONG).show();
							// Toast.makeText(this,"id: "+c.getString(0) + "\n" +"EntryDate: "+c.getString(1) + "\n" +"UserDate: "+c.getString(2) + "\n" +"place: "+c.getString(3) + "\n"+"Fund: " +c.getString(4)+"\n"+"Spent Money:"+c.getString(5) ,Toast.LENGTH_LONG).show();
						 }while(c.moveToNext());
					 }
					 incomeAvail=String.valueOf(Double.valueOf(incomeAvail)-Double.valueOf(ItemPrice));
					 db2.updateRow(Long.valueOf(row1), incDate, incomeAvail, incSav);
					 Toast.makeText(getApplicationContext(), "Money in hand became negative!!",Toast.LENGTH_LONG).show();
					 db2.close();
				
				}
			
			});
			builder.setNegativeButton("Quit and Change Settings", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(DailyEntryActivity.this,SettingsActivity.class);
					startActivity(intent);
				}
			});
			return builder.create();
			
		}return null;
	}

}
