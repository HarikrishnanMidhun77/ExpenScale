package com.mti.expensemanager;

import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DailyReportDialog extends Activity {
	DBclassExp db;
	String[] s1;
	String[] s2;
	String DupDate1;
	String DupDate2;
	String price;
	int count=0,yrsum=0,Umnth,Uday;
	double est;
	String estimate;
	String type;
	String Udate;
	String Cmonth;
	String theDay;
	String selDate;
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
	Button btnFood;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_report_dialog);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		db=new DBclassExp(this);
		
		final Bundle bundle=getIntent().getExtras();
		Intent myIntent=getIntent();
		myIntent.getStringExtra("date");
		selDate=bundle.getString("date");
		
		EditText Texp=(EditText)findViewById(R.id.txtTDailyExpense);
		EditText Test=(EditText)findViewById(R.id.txtEstMonthlyInc);
		EditText editFood=(EditText)findViewById(R.id.txtPfood);
		EditText editTravel=(EditText)findViewById(R.id.txtPtravel);
		EditText editEdu=(EditText)findViewById(R.id.txtPedu);
		EditText editEnter=(EditText)findViewById(R.id.txtPenter);
		EditText editStat=(EditText)findViewById(R.id.txtPstat);
		EditText editCos=(EditText)findViewById(R.id.txtPcos);
		EditText editHeal=(EditText)findViewById(R.id.txtPheal);
		EditText editOth=(EditText)findViewById(R.id.txtPoth22);
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
			//Cmonth= Udate.substring(3, 4);
			//Toast.makeText(getApplicationContext(), "Split month : "+Cmonth+"This month:"+mnth,Toast.LENGTH_LONG).show();
			if(selDate.equals(Udate))
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
			DupDate1=Udate;
			DupDate2=selDate;
			s1=DupDate1.split("/");
			s2=DupDate2.split("/");
			Toast.makeText(getApplicationContext(), s1[2]+" , "+s2[2],4000).show();
			if(s1[2].equals(s2[2]))
			{
				yrsum+=Integer.valueOf(price);
			}
			 } 
			 }
		
		DupDate2=selDate;
		s2=DupDate2.split("/");
		Umnth=Integer.valueOf(s2[1]);
		Uday=(Umnth-1)*30+Integer.valueOf(s2[0]);
		est=Double.valueOf((((yrsum/Uday)*30)+(fsum*30))/2);
		est=Math.floor(est);
		
			 foodSumPer=(int) ((foodSum/fsum)*100);
			 travelSumPer=(int) ((travelSum/fsum)*100);
			 eduSumPer=(int) ((eduSum/fsum)*100);
			 enterSumPer=(int) ((enterSum/fsum)*100);
			 statSumPer=(int) ((statSum/fsum)*100);
			 cosSumPer=(int) ((cosSum/fsum)*100);
			 healSumPer=(int) ((healSum/fsum)*100);
			 othSumPer=(int) ((othSum/fsum)*100);
			 Texp.setText(String.valueOf(fsum));
			 Test.setText(String.valueOf(est));
			 editFood.setText(foodSum+" ("+foodSumPer+"% )");
			 editTravel.setText(travelSum+" ("+travelSumPer+"% )");
			 editEdu.setText(eduSum+" ("+eduSumPer+"% )");
			 editEnter.setText(enterSum+" ("+enterSumPer+"% )");
			 editStat.setText(statSum+" ("+statSumPer+"% )");
			 editCos.setText(cosSum+" ("+cosSumPer+"% )");
			 editHeal.setText(healSum+" ("+healSumPer+"% )");
			 editOth.setText(othSum+" ("+othSumPer+"% )");
			 Texp.setFocusable(false);
			 Test.setFocusable(false);
			 editFood.setFocusable(false);
				editTravel.setFocusable(false);
				editEdu.setFocusable(false);
				editEnter.setFocusable(false);
				editStat.setFocusable(false);
				editCos.setFocusable(false);
				editHeal.setFocusable(false);
				editOth.setFocusable(false);
		
				
		db.close();
		 }catch(Exception e)
			{
				
			}
		Button button=(Button)findViewById(R.id.btnPok);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				Intent intent=new Intent(DailyReportDialog.this,Home1Activity.class);
				startActivity(intent);
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daily_report_dialog, menu);
		return true;
	}

}
