package com.mti.expensemanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import com.mti.android.domain.Domain2;
import com.mti.SM_DB.DBclassExp3;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mti.expensemanager.SimpleGestureFilter;
public class ShowSM extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	Domain2 dom2;
DBclassExp3 db3;
String theDay;
String Id;
String ShDateSm;
String ShPlaceSm;
String ShFundSm1;
String ShSpentSm;
int yr,day,mnth;
Button update,dlt;
StringBuffer stringBuffer,stringBuffer1,stringBuffer2,stringBuffer3,stringBuffer4,stringBufferId;

protected Dialog onCreateDialog(int id)
{
	switch(id){
	case 0:
		Builder builder=new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.face);
		builder.setTitle("Do you want to Delete this content?");
		builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				db3.open();
				db3.deleteRow(dom2.getIdMain2());
				db3.close();
			
				Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
				
				EditText editText1=(EditText)findViewById(R.id.txtShFundSm1);
			editText1.setText(" ");
				EditText editText2=(EditText)findViewById(R.id.txtShDateSm);
				editText2.setText(" ");
				EditText editText3=(EditText)findViewById(R.id.txtShPlaceSm);
				editText3.setText(" ");
				EditText editText4=(EditText)findViewById(R.id.txtShSpentSm);
				editText4.setText(" ");
				Intent intent=new Intent(ShowSM.this,ListViewsSm.class);
				startActivity(intent);
				
				
			}
		});
builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		return builder.create();
		
	}return null;
}

protected Dialog onCreateDialogUp(int id)
{
	switch(id){
	case 0:
		Builder builder=new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.face);
		builder.setTitle("Do you want to Update this content?");
		builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				EditText editText1=(EditText)findViewById(R.id.txtShDateSm);
				ShDateSm=editText1.getText().toString();
				EditText editText2=(EditText)findViewById(R.id.txtShPlaceSm);
				ShPlaceSm=editText2.getText().toString();
				EditText editText3=(EditText)findViewById(R.id.txtShFundSm1);
				ShFundSm1=editText3.getText().toString();
				EditText editText4=(EditText)findViewById(R.id.txtShSpentSm);
				ShSpentSm=editText4.getText().toString();
				try
				{
					
					db3.open();
					db3.updateRow(dom2.getIdMain2(),theDay, ShDateSm, ShPlaceSm, ShFundSm1, ShSpentSm);
					Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_LONG).show();
					db3.close();
					Intent intent=new Intent(ShowSM.this,ListViewsSm.class);
					startActivity(intent);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				Intent intent=new Intent(ShowSM.this,ListViewsSm.class);
				startActivity(intent);
				
				
			}
		});
builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		return builder.create();
		
	}return null;
}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_sm);
		final MediaPlayer mpAlert=MediaPlayer.create(this,R.raw.alert);
		 detector=new SimpleGestureFilter(this, this);
		dom2=new Domain2();
		db3=new DBclassExp3(this);
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		
		//showDialog(DATE_DIALOG_ID);
		
		theDay=day+"/"+(mnth+1)+"/"+yr;
		EditText editText1=(EditText)findViewById(R.id.txtShDateSm);
		EditText editText2=(EditText)findViewById(R.id.txtShPlaceSm);
		EditText editText3=(EditText)findViewById(R.id.txtShFundSm1);
		EditText editText4=(EditText)findViewById(R.id.txtShSpentSm);
		editText1.setText(dom2.getUserDate());
		editText2.setText(dom2.getPlace());
		editText3.setText(dom2.getFund());
		editText4.setText(dom2.getSpent());
		
		Button btnUpdate=(Button)findViewById(R.id.btnShUpdateSm);
		btnUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mpAlert.start();
				Dialog d1=onCreateDialogUp(0);
				d1.show();
			}
		});
		Button btnDlt=(Button)findViewById(R.id.btnShDltSm);
				btnDlt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mpAlert.start();
						 Dialog d1=onCreateDialog(0);
							d1.show();
					
					}
				});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_sm, menu);
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
			Intent intent=new Intent(ShowSM.this,ListViewsSm.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(ShowSM.this,Home1Activity.class);
		startActivity(intent);
	}

}
