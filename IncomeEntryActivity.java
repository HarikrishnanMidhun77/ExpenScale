package com.mti.expensemanager;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mti.expensemanager.SimpleGestureFilter;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.Income_Savings_DB.*;
import com.mti.expensemanagerDB.DBclassExp;
public class IncomeEntryActivity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
String lmdate;
String AvailInc;
String NewInc;
float nInc1,nInc2;
String income;
String savings;
String availSav; 
DBclassExp2 db;
int yr,mnth,day;
String theDay;
public void  DisplayAllItems(Cursor c)
{
	Toast.makeText(this,"id: "+c.getString(0) + "\n" +"Date: "+c.getString(1) + "\n" +"Income: "+c.getString(2) + "\n" +"Savings: "+c.getString(3) + "\n"  , Toast.LENGTH_LONG).show();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income_entry);
		 detector=new SimpleGestureFilter(this, this);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 final MediaPlayer mpSave=MediaPlayer.create(this,R.raw.savings);
		 final MediaPlayer mpAddInc=MediaPlayer.create(this,R.raw.income_increment);
		 final MediaPlayer mpSubInc=MediaPlayer.create(this,R.raw.income_decrement);
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		
		//showDialog(DATE_DIALOG_ID);
		
		theDay=day+"/"+(mnth+1)+"/"+yr;
		
		db = new DBclassExp2(this);
		 db.open();
		 Cursor c=db.getAllTitles();
		 if(c.moveToLast())
		 {
			 do
			 {
				 DisplayAllItems(c);
				 income=c.getString(2);
				 savings=c.getString(3);
			 }while(c.moveToNext());
		 }
		 db.close();
		 
		 //code for if input = null
		 if (income==null)
				 {
			 income="0";
				 }
		 if (savings==null)
		 {
			 savings="0";
		 }
		 EditText editSav=(EditText)findViewById(R.id.txtSav);
		 editSav.setFocusable(false);
		 EditText editText=(EditText)findViewById(R.id.txtMavail);
		 editText.setText(income);
		 editText.setFocusable(false);
		 EditText editText2=(EditText)findViewById(R.id.txtSav);
		 editText2.setText(savings);
		 EditText editNewInc=(EditText)findViewById(R.id.txtNewInc);
			editNewInc.setText("0");
		Button btnAddInc=(Button)findViewById(R.id.btnAddInc);
		btnAddInc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mpAddInc.start();
				 lmdate=theDay;
				 EditText editText=(EditText)findViewById(R.id.txtMavail);
				 AvailInc=editText.getText().toString();
				 EditText editText2=(EditText)findViewById(R.id.txtNewInc);
				 NewInc= editText2.getText().toString();
				 nInc1=Float.valueOf(AvailInc);
				 nInc2=Float.valueOf(NewInc);
				 nInc1=nInc1+nInc2;
				 String s=String.valueOf(nInc1);
				 editText.setText(s);
				 editText2.setText("0");
				 EditText editText3=(EditText)findViewById(R.id.txtSav);
				 availSav=editText3.getText().toString();
				//incSavDb.createReminder(lmdate,s,availSav);
				 db.open();
				 long num;
		            num = db.insertmaster(lmdate.toString(),s.toString(),availSav.toString());
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
			}
		});
		Button btnSubInc =(Button)findViewById(R.id.BtnSubInc);
		btnSubInc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mpSubInc.start();
				 lmdate=theDay;
				 EditText editText=(EditText)findViewById(R.id.txtMavail);
				 AvailInc=editText.getText().toString();
				 EditText editText2=(EditText)findViewById(R.id.txtNewInc);
				 NewInc= editText2.getText().toString();
				 nInc1=Float.valueOf(AvailInc);
				 nInc2=Float.valueOf(NewInc);
				 nInc1=nInc1-nInc2;
				 String s=String.valueOf(nInc1);
				 editText.setText(s);
				 editText2.setText("0");
				 EditText editText3=(EditText)findViewById(R.id.txtSav);
				 availSav=editText3.getText().toString();
				//incSavDb.createReminder(lmdate,s,availSav);
				 db.open();
				 long num;
		            num = db.insertmaster(lmdate.toString(),s.toString(),availSav.toString());
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
			}
		});
		
	
		Button btnIS=(Button)findViewById(R.id.btnIncSav);
		btnIS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mpSave.start();
				lmdate=theDay;
				EditText editText=(EditText)findViewById(R.id.txtMavail);
				AvailInc=editText.getText().toString();
				EditText editText2=(EditText)findViewById(R.id.txtSav);
				availSav=editText2.getText().toString();
				nInc1=Float.valueOf(availSav) ;
				nInc2=Float.valueOf(AvailInc);
				nInc1=nInc1+nInc2;
				String s=String.valueOf(nInc1);
				editText2.setText(s);
				editText.setText("0");
				 String str1="0";
				 
				 db.open();
				 long num;
		            num = db.insertmaster(lmdate.toString(),str1.toString(),s.toString());
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
			}
		});
		Button btnDone=(Button)findViewById(R.id.btnIncomeDone);
		btnDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(IncomeEntryActivity.this,Home1Activity.class);
				startActivity(intent);
			}
		});
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
			Intent intent=new Intent(IncomeEntryActivity.this,Home1Activity.class);
			startActivity(intent);
		}
	}
	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(IncomeEntryActivity.this,Home1Activity.class);
		startActivity(intent);
	}

}
