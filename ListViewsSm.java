package com.mti.expensemanager;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mti.SM_DB.DBclassExp3;
import com.mti.android.domain.Domain2;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;
import android.view.MotionEvent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.mti.expensemanager.SimpleGestureFilter;



public class ListViewsSm extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	Domain2 dom1;
	 private ListView list_lv1;
	    private EditText col1_ed;
	    private EditText col2_ed;
	    private Button sub_btn;
	    private Button ref_btn;
	    DBclassExp3 db3;
	    String id1;
	    long min;
	    String UserDate;
	   
	    String place;
	    String fund;
	    String spent;
	    private ArrayList<String> collist_1;
	    private ArrayList<String> collist_2;
	    private ArrayList<String> collist_3;
	    
	    
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_views_sm);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 final MediaPlayer mp2=MediaPlayer.create(this,R.raw.alert);
		 detector=new SimpleGestureFilter(this, this);
		
		 collist_1 = new ArrayList<String>();
	        collist_2 = new ArrayList<String>();
	        collist_3 = new ArrayList<String>();
	        items();
	        getData();
	        
	        
	        db3=new DBclassExp3(this);
	        Button dlt =(Button)findViewById(R.id.btnDeleteSm);
	        dlt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mp2.start();
					Dialog d1=onCreateDialogDelAll(0);
					d1.show();
				}
			});
	       // list_lv1=(ListView)findViewById(R.id.dblist3);
	          list_lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					mp.start();
					dom1=new Domain2();
					long ids=list_lv1.getItemIdAtPosition(position);
					ids=ids+1;
					
					
					db3.open();
					
					Cursor c3=db3.getAllTitles();

					 if(c3.moveToFirst())//not entering to if loop
					 {
						 id1=c3.getString(0);
						 min=Long.valueOf(id1);
						 Toast.makeText(getApplicationContext(), String.valueOf(min), Toast.LENGTH_SHORT).show();
						
						 ids=ids+(min-1);
						 Toast.makeText(getApplicationContext(), String.valueOf(ids), Toast.LENGTH_SHORT).show();
					 }
					 Cursor c2=db3.getAllTitles();
					if(c2.moveToFirst())
					 {
						  do
						 {
							  id1=c2.getString(0);
							
							  if(Long.valueOf(id1)==ids)
							  {
								  Cursor c=db3.getColsById(ids);//how to get the id of clicked?
									 
									while(c.moveToNext())
									 {
										
										 UserDate=c.getString(1);
										 place=c.getString(2);
										 fund=c.getString(3);
										 spent=c.getString(4);

										//Toast.makeText(getApplicationContext(), "Success"+ids, Toast.LENGTH_LONG).show();
									 }
								  
								  if(c.moveToFirst())//not entering to if 
									 {
										 /* do
										 { */
										 UserDate=c.getString(1);
										 place=c.getString(2);
										 fund=c.getString(3);
										 spent=c.getString(4);

											 
											// Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
											 
											 dom1.setIdMain2(ids);
												
												dom1.setUserDate(UserDate);
												dom1.setFund(fund);
												dom1.setPlace(place);
												dom1.setSpent(spent);

								  
								  
								
										
									// }while(c.moveToNext());
									
										 
									 }
							  }
							  }while(c2.moveToNext());
									 db3.close();
									//Toast.makeText(getApplicationContext(), String.valueOf(ids), Toast.LENGTH_SHORT).show();
					 }
					
					 Intent intent=new Intent(ListViewsSm.this,ShowSM.class);
						startActivity(intent);
						
					return true;
				}
			});
	       
	    }
	 
	    private void items() {
	    	 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
	        ref_btn = (Button) findViewById(R.id.btnRefresh3);
	      
	        list_lv1 = (ListView) findViewById(R.id.dblist3);
	 
	        ref_btn.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	            	mp.start();
	                getData();
	            }
	        });
	 
	       
	    }
	 
	     
	    public void getData() {
	        collist_1.clear();
	        collist_2.clear();
	        collist_3.clear();
	        db3 = new DBclassExp3(this);
	        try {
	            db3.open();
	            Cursor cur = db3.getAllTitles();
	            if(cur.moveToLast())
	            {
	            	do
	            	{
	            		
	            	
	           // while (cur.moveToNext()) {
	            	
	                String valueofcol1 = cur.getString(2);
	                String valueofcol2 = "Funt:			Rs."+cur.getString(4);
	                String valueofcol3="Spent:			Rs:"+cur.getString(5);

	 
	                collist_1.add(valueofcol1);
	                collist_2.add(valueofcol2);
	                collist_3.add(valueofcol3);
	            
	            }while(cur.moveToPrevious());
            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            db3.close();
	        }
	        printList();
	        setDataIntoList();
	    }
	 
	    private void printList() {
	        for (int i = 0; i < collist_1.size(); i++) {
	            Log.e("***************",
	                    collist_1.get(i) + " --- " + collist_2.get(i) + " --- " + collist_3.get(i));
	        }
	    }
	 
	    private void setDataIntoList() {
	 
	        // create the list item mapping
	        String[] from = new String[] { "col_1", "col_2","col_3" };
	        int[] to = new int[] { R.id.col1tv, R.id.col2tv,R.id.col3tv };
	 
	        // prepare the list of all records
	        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	        for (int i = 0; i < collist_1.size(); i++) {
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("col_1", collist_1.get(i));
	            map.put("col_2", collist_2.get(i));
	            map.put("col_3", collist_3.get(i));
	            fillMaps.add(map);
	        }
	 
	        // fill in the grid_item layout
	        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps,
	                R.layout.custom, from, to);
	        list_lv1.setAdapter(adapter);
	    }
	    
	
@Override
public boolean dispatchTouchEvent(MotionEvent me)
{
	this.detector.onTouchEvent(me);
	return super.dispatchTouchEvent(me);
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_views_sm, menu);
		return true;
	}

	@Override
	public void onSwipe(int direction) {
		// TODO Auto-generated method stub
		final MediaPlayer mpSwipe=MediaPlayer.create(this,R.raw.swipe);
		mpSwipe.start();
		if(direction==SimpleGestureFilter.SWIPE_RIGHT)
		{
			Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(ListViewsSm.this,Edit_Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(ListViewsSm.this,Home1Activity.class);
		startActivity(intent);
	}
	protected Dialog onCreateDialogDelAll(int id)
	{
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		switch(id){
		case 0:
			Builder builder=new AlertDialog.Builder(this);
			//builder.setIcon(R.drawable.face);
			builder.setTitle("Do you want to DELETE ALL ENTRIES?");
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
					mp.start();
				db3.open();
				if(db3.delete())
				{
					Toast.makeText(getApplicationContext(), "All entries deleted", Toast.LENGTH_LONG).show();
					long c2=db3.insertmaster("00/00/0000", "0/00/0000","abc","0.00","0.00");
	                getData();
				}
				db3.close();
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
}
