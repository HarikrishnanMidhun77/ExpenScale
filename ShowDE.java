package com.mti.expensemanager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import com.mti.android.domain.Domain1;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mti.expensemanager.SimpleGestureFilter;
public class ShowDE extends Activity implements SimpleGestureListener{
private SimpleGestureFilter detector;
String[] itemTypes_array;
EditText editText,place,date;
String Id;
Domain1 dom2;
String shTypeDe;
String shPriceDe;
String shPlaceDe;
String shDescripDe;
String ShDateDe;
int yr,day,mnth;
String theDay;
//Domain domain=new Domain();
Button update,dlt;
StringBuffer stringBuffer,stringBuffer1,stringBuffer2,stringBuffer3,stringBuffer4,stringBufferId;
DBclassExp db;



	
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
				db.open();
				db.deleteRow(dom2.getIdMain());//Long.valueOf(Id)
				db.close();
				
				Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
				EditText editText1=(EditText)findViewById(R.id.txtshDateDe);
			editText1.setText(" ");
				EditText editText2=(EditText)findViewById(R.id.txtshTypeDe);
				editText2.setText(" ");
				EditText editText3=(EditText)findViewById(R.id.txtshPriceDe);
				editText3.setText(" ");
				EditText editText4=(EditText)findViewById(R.id.txtshPlaceDe);
				editText4.setText(" ");
				EditText editText5=(EditText)findViewById(R.id.txtshDescripDe);
				editText5.setText(" ");
				Intent intent=new Intent(ShowDE.this,ListViewsDE.class);
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
				EditText editText1=(EditText)findViewById(R.id.txtshDateDe);
				ShDateDe=editText1.getText().toString();
				EditText editText2=(EditText)findViewById(R.id.txtshTypeDe);
				shTypeDe=editText2.getText().toString();
				EditText editText3=(EditText)findViewById(R.id.txtshPriceDe);
				shPriceDe=editText3.getText().toString();
				EditText editText4=(EditText)findViewById(R.id.txtshPlaceDe);
				shPlaceDe=editText4.getText().toString();
				EditText editText5=(EditText)findViewById(R.id.txtshDescripDe);
				shDescripDe=editText5.getText().toString();
				
				
				
				
				try
				{
					
					db.open();
					db.updateRow(dom2.getIdMain(),theDay, ShDateDe, shTypeDe, shPriceDe, shPlaceDe, shDescripDe);//Long.valueOf(Id)
					db.close();
					Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(ShowDE.this,ListViewsDE.class);
					startActivity(intent);
				}catch(Exception e){
					e.printStackTrace();
				}
				Intent intent=new Intent(ShowDE.this,ListViewsDE.class);
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
		setContentView(R.layout.activity_show_de);
		 final MediaPlayer mpAlert=MediaPlayer.create(this,R.raw.alert);
		 detector=new SimpleGestureFilter(this, this);
		db=new DBclassExp(this);
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mnth=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		
		//showDialog(DATE_DIALOG_ID);
		dom2=new Domain1();
		theDay=day+"/"+(mnth+1)+"/"+yr;
		EditText editText1=(EditText)findViewById(R.id.txtshDateDe);
		EditText editText2=(EditText)findViewById(R.id.txtshTypeDe);
		//editText2.setFocusable(false);
		EditText editText3=(EditText)findViewById(R.id.txtshPriceDe);
		EditText editText4=(EditText)findViewById(R.id.txtshPlaceDe);
		EditText editText5=(EditText)findViewById(R.id.txtshDescripDe);
		editText1.setText(dom2.getUserDateMain());
		editText2.setText(dom2.getItemTypeMain());
		editText3.setText(dom2.getItemPriceMain());
		editText4.setText(dom2.getItemPlaceMain());
		editText5.setText(dom2.getItemDescripMain());
		
		itemTypes_array=getResources().getStringArray(R.array.expence_types);
		Spinner spinner=(Spinner)findViewById(R.id.spnTypeShowDe);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemTypes_array );
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index=arg0.getSelectedItemPosition();
				EditText editType=(EditText)findViewById(R.id.txtshTypeDe);
			    editType.setText(itemTypes_array[index]);
			    editType.setFocusable(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				}
		});

		
		Button btnUpdate=(Button)findViewById(R.id.btnshUpdateDe);
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mpAlert.start();
				Dialog d1=onCreateDialogUp(0);
				d1.show();
				
			}
		});
		Button btnDlt=(Button)findViewById(R.id.btnshDeleteDe);
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
		getMenuInflater().inflate(R.menu.show_de, menu);
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
			Intent intent=new Intent(ShowDE.this,ListViewsDE.class);
			startActivity(intent);
		}
	}
	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(ShowDE.this,Home1Activity.class);
		startActivity(intent);
	}

}
