package com.mti.expensemanager;

import com.mti.Income_Savings_DB.DBclassExp2;
import com.mti.SM_DB.DBclassExp3;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import com.mti.expensemanager.SimpleGestureFilter;

public class SM_2Activity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
	String[] itemTypes_array_sm;
	float Tmoney;
	float bal;
	String smPrice;
	String smType;
	String smName;
	String id2;
	String Endate2;
	String UserDate2;
	String smPlace2;
	String smFund2;
	String Mspent2;
	String row1;
	String incSav;
	String incDate;
	String incomeAvail;
	DBclassExp db;
	DBclassExp3 db3;
	DBclassExp2 db2;
	SharedPreferences settings;
	
	public void  DisplayAllItems(Cursor c)
	{
		Toast.makeText(this,"id: "+c.getString(0) + "\n" +"Date: "+c.getString(1) + "\n" +"Income: "+c.getString(2) + "\n" +"Savings: "+c.getString(3) + "\n"  ,Toast.LENGTH_LONG).show();
	}
	protected Dialog onCreateDialog(int id)
	{
		switch(id){
		case 0:
			Builder builder=new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.face);
			builder.setTitle("You've exceeded your fund!!");
			builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "You can Continue or Exit shopping" , Toast.LENGTH_SHORT).show();
					
				}
			});
			return builder.create();
			
		}return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_2);
		 final MediaPlayer mpEnter=MediaPlayer.create(this,R.raw.income_decrement);
		 final MediaPlayer mpExit=MediaPlayer.create(this,R.raw.shopping_end);
		 detector=new SimpleGestureFilter(this, this);
		 PreferenceManager.setDefaultValues(this, R.xml.my_settings,false);     
		 settings=PreferenceManager.getDefaultSharedPreferences(this);
		db=new DBclassExp(this);
		db2=new DBclassExp2(this);
		db3 = new DBclassExp3(this);
		EditText editIname=(EditText)findViewById(R.id.txtsmitem);
		editIname.setText("shopped item");
		
		EditText editText5=(EditText)findViewById(R.id.txtsmtype);
		editText5.setFocusable(false);
	    editText5.setText("shopped item");
		itemTypes_array_sm=getResources().getStringArray(R.array.expence_types);
		Spinner spinner=(Spinner)findViewById(R.id.spnTypeSm);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemTypes_array_sm );
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index=arg0.getSelectedItemPosition();
				EditText editText5=(EditText)findViewById(R.id.txtsmtype);
			    editText5.setText(itemTypes_array_sm[index]);
			}
		
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				}
		});
		
		
	    
		 db3.open();
		 Cursor c=db3.getAllTitles();
		 
		 if(c.moveToLast())
		 {
			 do
			 {
				 id2=c.getString(0);
				 Endate2=c.getString(1);
				 UserDate2=c.getString(2);
				 smPlace2=c.getString(3);
				 smFund2=c.getString(4);
				 Mspent2=c.getString(5);
				 Toast.makeText(this,"id: "+c.getString(0) + "\n" +"EntryDate: "+c.getString(1) + "\n" +"UserDate: "+c.getString(2) + "\n" +"place: "+c.getString(3) + "\n"+"Fund: " +c.getString(4)+"\n"+"Spent Money:"+c.getString(5) ,Toast.LENGTH_LONG).show();
			 }while(c.moveToNext());
		 }
		 db3.close();
		 
		 EditText balFund=(EditText)findViewById(R.id.txtsmbal);
		 balFund.setText(smFund2);
		 bal=Float.valueOf(smFund2);
		Tmoney=0;
		 EditText spentAmount=(EditText)findViewById(R.id.txtsmspent);
		 spentAmount.setText(String.valueOf(Tmoney));
		
		 Button btnEnter=(Button)findViewById(R.id.btnsmenter);
		 btnEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 mpEnter.start();
				
				EditText editText=(EditText)findViewById(R.id.txtsmtype);
				editText.setFocusable(false);
				smType=editText.getText().toString();
				
				EditText editText2=(EditText)findViewById(R.id.txtsmprice);
				smPrice=editText2.getText().toString();
				editText2.setText("");
				EditText editText3=(EditText)findViewById(R.id.txtsmitem);
				smName=editText3.getText().toString();
				
				
				Tmoney=Tmoney+(Float.valueOf(smPrice));
				 EditText spentAmount=(EditText)findViewById(R.id.txtsmspent);
				 spentAmount.setText(String.valueOf(Tmoney));
		           
				 bal=bal-(Float.valueOf(smPrice));
		         EditText balFund=(EditText)findViewById(R.id.txtsmbal);
		         balFund.setText(String.valueOf(bal));
				
		if(bal<0){
			 Dialog d1=onCreateDialog(0);
				d1.show();
		}
		        
		
		         
				 db.open();
				 long num;
		            num = db.insertmaster(Endate2,UserDate2, smType, smPrice, smPlace2, smName);
		            db.close();
		            if (num > 0)
		                Toast.makeText(getApplicationContext(), "Row number: " + num, 2000).show();
		            
			}
		});
		 
		 
		 Button btnUn=(Button)findViewById(R.id.btnsmUenter);
		 btnUn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mpEnter.start();
				 db.open();
				 Cursor c=db.getAllTitles();
				 if(c.moveToLast())
				 {
					 do
					 {
						 id2=c.getString(0);
					 }while(c.moveToNext());
				 }
				if( db.deleteRow(Long.valueOf(id2)))
				{
					Toast.makeText(getApplicationContext(), "Last entry Undone!", Toast.LENGTH_LONG).show();
					Tmoney=Tmoney-(Float.valueOf(smPrice));
					 EditText spentAmount=(EditText)findViewById(R.id.txtsmspent);
					 spentAmount.setText(String.valueOf(Tmoney));
					 
					 bal=bal+(Float.valueOf(smPrice));
			         EditText balFund=(EditText)findViewById(R.id.txtsmbal);
			         balFund.setText(String.valueOf(bal));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Undo failed", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		 
		Button button=(Button)findViewById(R.id.btnsmexit);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db2.open();
				if(settings.getBoolean("IncomeDecrement", false))
				{
					
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
				
					 incomeAvail=String.valueOf(Float.valueOf(incomeAvail)-Tmoney);
					 Toast.makeText(getApplicationContext(),"Available income="+incomeAvail,Toast.LENGTH_LONG).show();
					 db2.updateRow(Long.valueOf(row1), incDate, incomeAvail, incSav);
					 Toast.makeText(getApplicationContext(), "Rs."+Tmoney+"decremented from Money in hand ",Toast.LENGTH_LONG).show();
					 db2.close();
				}
				db3.open();
				// Cursor c2 = null;
				db3.updateRow(Long.valueOf(id2), Endate2.toString(),UserDate2.toString(),smPlace2.toString(),smFund2.toString(),String.valueOf(Tmoney).toString());
				 db3.close();
				Toast.makeText(getApplicationContext(),"Database Updated" , Toast.LENGTH_LONG).show();
				
				
				mpExit.start();
				Intent intent= new Intent(SM_2Activity.this,SM_3Activity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm_2, menu);
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
			Intent intent=new Intent(SM_2Activity.this,SM_1Activity.class);
			startActivity(intent);
		}
	}
	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(SM_2Activity.this,Home1Activity.class);
		startActivity(intent);
	}

}
