package com.mti.expensemanager;

import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mti.expensemanager.SimpleGestureFilter;
public class MonthlyReportDialog extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	DBclassExp db;
	String st[];
	String price;
	String MnthStr;

	 String s;
	String YrStr;
	String type;
	String Udate;
	String Cmonth;
	String theDay;
	
	int yr,mnth,day;
	float fsum=0;
	float foodSum=0;
	int foodSumPer;
	float travelSum=0;
	int travelSumPer;
	float eduSum=0;
	int eduSumPer;
	float enterSum=0;
	int enterSumPer;
	float statSum=0;
	int statSumPer;
	float cosSum=0;
	int cosSumPer;
	float healSum=0;
	int healSumPer;
	float othSum=0;
	int othSumPer;
	
	String month1;
	String year1;
	
	EditText editMonth;
	EditText editYear;
	 EditText Texp;
	 EditText editFood;
	 EditText editTravel;
		EditText editEdu;
		EditText editEnter;
		EditText editStat;
		EditText editHeal;
		EditText editCos;
		EditText editOth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monthly_report_dialog);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 detector=new SimpleGestureFilter(this, this);
		db=new DBclassExp(this);
		final Bundle bundle=getIntent().getExtras();
		Intent myIntent=getIntent();
		myIntent.getStringExtra("month");
		month1=bundle.getString("month");
		myIntent.getStringExtra("year");
		year1=bundle.getString("year");
		
		 Texp=(EditText)findViewById(R.id.txtTMonthlyExpense);
		 editFood=(EditText)findViewById(R.id.txtPfood2);
			 editTravel=(EditText)findViewById(R.id.txtPtravel2);
			 editEdu=(EditText)findViewById(R.id.txtPedu2);
			 editEnter=(EditText)findViewById(R.id.txtPenter2);
		 editStat=(EditText)findViewById(R.id.txtPstat2);
			 editCos=(EditText)findViewById(R.id.txtPcos2);
		 editHeal=(EditText)findViewById(R.id.txtPheal2);
			 editOth=(EditText)findViewById(R.id.txtPoth2);
			
			 
			 
				try
				{
				db.open();
		 Cursor c=db.getAllTitles();
				 
				if(c.moveToFirst())
				 {
					 while(c.moveToNext()){
						 Udate=c.getString(2);
						 type=c.getString(3);
						 price=c.getString(4);
						 
						s=Udate;
						
						 st=s.split("/");
						 MnthStr=st[1];
						 YrStr=st[2];
						 System.out.print(MnthStr+" "+YrStr);
					
					//if(MnthStr.equals(editMonth.getText().toString()) && editYear.getText().toString().equals(String.valueOf(YrStr)))
					if(MnthStr.equals(month1) && YrStr.equals(year1))
					{
					
						 fsum=fsum+Float.valueOf(price);
						 if(type.equalsIgnoreCase("food"))
						 {
							 foodSum=foodSum+Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("travel"))
						 {
							 travelSum=travelSum+Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("education"))
						 {
							eduSum+=Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("entertainment"))
						 {
							enterSum+=Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("shopped item"))
						 {
							statSum+=Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("costumes and cosmetics"))
						 {
							cosSum+=Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("health"))
						 {
							healSum+=Float.valueOf(price);
						 }
						 if(type.equalsIgnoreCase("other"))
						 {
							othSum+=Float.valueOf(price);
						 }
						 
						 
						 
						 
						 System.out.println(fsum);
					 } 
					 }
				
					 foodSumPer=(int) ((foodSum/fsum)*100);
					 travelSumPer=(int) ((travelSum/fsum)*100);
					 eduSumPer=(int) ((eduSum/fsum)*100);
					 enterSumPer=(int) ((enterSum/fsum)*100);
					 statSumPer=(int) ((statSum/fsum)*100);
					 cosSumPer=(int) ((cosSum/fsum)*100);
					 healSumPer=(int) ((healSum/fsum)*100);
					 othSumPer=(int) ((othSum/fsum)*100);
					 Texp.setText(String.valueOf(fsum));
					 editFood.setText(foodSum+" ("+foodSumPer+"% )");
					 editTravel.setText(travelSum+" ("+travelSumPer+"% )");
					 editEdu.setText(eduSum+" ("+eduSumPer+"% )");
					 editEnter.setText(enterSum+" ("+enterSumPer+"% )");
					 editStat.setText(statSum+" ("+statSumPer+"% )");
					 editCos.setText(cosSum+" ("+cosSumPer+"% )");
					 editHeal.setText(healSum+" ("+healSumPer+"% )");
					 editOth.setText(othSum+" ("+othSumPer+"% )");
					 Texp.setFocusable(false);
					editFood.setFocusable(false);
						editTravel.setFocusable(false);
						editEdu.setFocusable(false);
						editEnter.setFocusable(false);
						editStat.setFocusable(false);
						editCos.setFocusable(false);
						editHeal.setFocusable(false);
						editOth.setFocusable(false);
						
				 }
			
				db.close();
				editMonth.setText("");
				editYear.setText("");
				 }catch(Exception e)
					{
						
					}
			
		
		Button button=(Button)findViewById(R.id.btnPok2);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(MonthlyReportDialog.this,Home1Activity.class);
				startActivity(intent);
			}
		});
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.monthly_report_dialog, menu);
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
			Intent intent=new Intent(MonthlyReportDialog.this,ReportMenu.class);
			startActivity(intent);
		}
	}




	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(MonthlyReportDialog.this,Home1Activity.class);
		startActivity(intent);
	}

}
