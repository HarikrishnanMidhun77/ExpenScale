package com.mti.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import com.mti.expensemanager.SimpleGestureFilter;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;

public class Home1Activity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        detector=new SimpleGestureFilter(this, this);
       final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
      Button button4=(Button)findViewById(R.id.btnEntry);
        button4.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
				mp.start();
				Intent intent=new Intent(Home1Activity.this,DailyEntryActivity.class);
				startActivity(intent);
			}
		});
    
        Button button=(Button)findViewById(R.id.btnIncome);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Home1Activity.this,IncomeEntryActivity.class);
				startActivity(intent);
			}
		});
        Button btnSm=(Button)findViewById(R.id.btnShopping);
        btnSm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Home1Activity.this,SM_1Activity.class);
				startActivity(intent);
			}
		});
        Button btnEd=(Button)findViewById(R.id.btnEdit);
        btnEd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Home1Activity.this,Edit_Activity.class);
				startActivity(intent);
			}
		});
        Button btnRpt=(Button)findViewById(R.id.btnRpt);
        btnRpt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Home1Activity.this,ReportMenu.class);
				startActivity(intent);
				
			}
		});
        Button btnSet=(Button)findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Home1Activity.this,SettingsActivity.class);
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
			Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
			finish();
		}
	}


	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
	
		
	}


}
