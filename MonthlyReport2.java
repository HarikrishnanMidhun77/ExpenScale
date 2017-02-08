package com.mti.expensemanager;

import java.util.Calendar;

import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;
import android.widget.AdapterView.OnItemSelectedListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.text.GetChars;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.mti.expensemanager.SimpleGestureFilter;
public class MonthlyReport2 extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	String[] months1;
	 String s;
	String YrStr;
	String type;
	String Udate;
	String Cmonth;
	String theDay;
	String month1;
	String year1;
	int yr,mnth,day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monthly_report2);
		 detector=new SimpleGestureFilter(this, this);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.shopping_end);
		 EditText editMonth=(EditText)findViewById(R.id.txtPMonth);
		editMonth.setFocusable(false);
		months1=getResources().getStringArray(R.array.Spnmonths);
		Spinner spinner=(Spinner)findViewById(R.id.spnRptMonth);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,months1);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index=arg0.getSelectedItemPosition();
				if(index==0)
				{
					EditText editMonth=(EditText)findViewById(R.id.txtPMonth);
					EditText editYear=(EditText)findViewById(R.id.txtPYear);
					
					Calendar today=Calendar.getInstance();
					yr=today.get(Calendar.YEAR);
					mnth=today.get(Calendar.MONTH);
					day=today.get(Calendar.DAY_OF_MONTH);
					theDay=day+"/"+(mnth+1)+"/"+yr;
					mnth=mnth+1;
					 editMonth.setText(String.valueOf(mnth));
					editYear.setText(String.valueOf(yr));
				}
				else
				{
					EditText editText5=(EditText)findViewById(R.id.txtPMonth);
				    editText5.setText(String.valueOf(index));
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				EditText editMonth=(EditText)findViewById(R.id.txtPMonth);
				EditText editYear=(EditText)findViewById(R.id.txtPYear);
				
				Calendar today=Calendar.getInstance();
				yr=today.get(Calendar.YEAR);
				mnth=today.get(Calendar.MONTH);
				day=today.get(Calendar.DAY_OF_MONTH);
				theDay=day+"/"+(mnth+1)+"/"+yr;
				mnth=mnth+1;
				 editMonth.setText(String.valueOf(mnth));
				editYear.setText(String.valueOf(yr));
			}
		});
	 
	
		
		
		
		Button btnMshow=(Button)findViewById(R.id.btnMRShow);

		btnMshow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.start();
				EditText editMonth=(EditText)findViewById(R.id.txtPMonth);
				EditText editYear=(EditText)findViewById(R.id.txtPYear);
				month1=editMonth.getText().toString();
				year1=editYear.getText().toString();
				Intent myIntent= new Intent(MonthlyReport2.this,MonthlyReportDialog.class);
				myIntent.putExtra("month", month1);
				myIntent.putExtra("year", year1);
				startActivity(myIntent);
				
				
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
			Intent intent=new Intent(MonthlyReport2.this,ReportMenu.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(MonthlyReport2.this,Home1Activity.class);
		startActivity(intent);
	}
}
