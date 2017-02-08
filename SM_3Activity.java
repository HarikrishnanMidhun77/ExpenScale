package com.mti.expensemanager;

import com.mti.SM_DB.DBclassExp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.mti.expensemanager.SimpleGestureFilter;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;

public class SM_3Activity extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
float fund;
float spent;
float diff;
String smFund3;
String  Mspent3;
DBclassExp3 db3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_3);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 detector=new SimpleGestureFilter(this, this);
		db3 = new DBclassExp3(this);
		 db3.open();
		 Cursor c=db3.getAllTitles();
		 if(c.moveToLast())
		 {
			 do
			 {
				// id2=c.getString(0);
				// Endate2=c.getString(1);
				// UserDate2=c.getString(2);
				// smPlace2=c.getString(3);
				 smFund3=c.getString(4);
				 Mspent3=c.getString(5);
				 
			 }while(c.moveToNext());
		 }
		 db3.close();
		
		EditText editText=(EditText)findViewById(R.id.txtsm3fund);
		editText.setText(smFund3);
		editText.setFocusable(false);
		fund=Float.valueOf(smFund3);
		EditText editText2=(EditText)findViewById(R.id.txtsm3spent);
		editText2.setText(Mspent3);
		editText2.setFocusable(false);
		spent=Float.valueOf(Mspent3);
		diff=fund-spent;
		EditText editText3=(EditText)findViewById(R.id.txtsm3diff);
		editText3.setFocusable(false);
		editText3.setText(String.valueOf(diff));
		if(diff>0)
			Toast.makeText(getApplicationContext(), "Congrats.. You have an excellent self-control!!", 2000).show();
		else if(diff<0)
			Toast.makeText(getApplicationContext(), "oops..! Try to be carefull next time..!", 2000).show();
		else
			Toast.makeText(getApplicationContext(), "Excellent!! Perfect planning!!", 2000).show();
		
		Button btnok=(Button)findViewById(R.id.btnsm3ok);
		btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(SM_3Activity.this,Home1Activity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm_3, menu);
		return true;
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
			Intent intent=new Intent(SM_3Activity.this,SM_2Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(SM_3Activity.this,Home1Activity.class);
		startActivity(intent);
	}

}
