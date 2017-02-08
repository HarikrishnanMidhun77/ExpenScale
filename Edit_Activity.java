package com.mti.expensemanager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mti.expensemanager.SimpleGestureFilter;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
public class Edit_Activity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 detector=new SimpleGestureFilter(this, this);
		Button btnDE =(Button)findViewById(R.id.btneditdaily);
		btnDE.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Edit_Activity.this,ListViewsDE.class);
				startActivity(intent);
			}
		});
		Button btnInc=(Button)findViewById(R.id.btnEditInc);
		btnInc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Edit_Activity.this,ListViewsInc.class);
				startActivity(intent);
			}
		});
		Button btnSM=(Button)findViewById(R.id.btnEditSm);
		btnSM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(Edit_Activity.this,ListViewsSm.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_, menu);
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
			Intent intent=new Intent(Edit_Activity.this,Home1Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(Edit_Activity.this,Home1Activity.class);
		startActivity(intent);
	}

}
