package com.mti.expensemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.mti.Income_Savings_DB.DBclassExp2;
import com.mti.expensemanager.SimpleGestureFilter.SimpleGestureListener;
import com.mti.expensemanagerDB.DBclassExp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import com.mti.expensemanager.SimpleGestureFilter;

public class ListViewsInc extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;

	LinearLayout layout;
	 private ListView list_lv;
	    private EditText col1_ed;
	    private EditText col2_ed;
	    private Button sub_btn;
	    private Button ref_btn;
	    DBclassExp2 db2;
	    private ArrayList<String> collist_1;
	    private ArrayList<String> collist_2;
	    private ArrayList<String> collist_3;
	    String lmdate;
		String income;
		String savings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_views_inc);
		 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
		 detector=new SimpleGestureFilter(this, this);
		 final MediaPlayer mp2=MediaPlayer.create(this,R.raw.alert);
		 collist_1 = new ArrayList<String>();
	        collist_2 = new ArrayList<String>();
	        collist_3 = new ArrayList<String>();
		    items();
	        getData();
	        db2=new DBclassExp2(this);
	        Button dlt =(Button)findViewById(R.id.btnDeleteInc);
	        dlt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					mp2.start();
					Dialog d1=onCreateDialogDelAll(0);
					d1.show();
				}
			});
	     
	    }
	 
	    private void items() {
	    	 final MediaPlayer mp=MediaPlayer.create(this,R.raw.every_move);
	        ref_btn = (Button) findViewById(R.id.btnRefresh2);
	      
	        list_lv = (ListView) findViewById(R.id.dblist2);
	 
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
	        db2 = new DBclassExp2(this);
	        try {
	            db2.open();
	            Cursor cur = db2.getAllTitles();
	            if(cur.moveToLast())
	            {
	            	do{
	         
	           // while (cur.moveToNext()) {
	            	
	                String valueofcol1 = cur.getString(1);
	                String valueofcol2 ="Income:			Rs."+ cur.getString(2);
	                String valueofcol3="Savings:			Rs."+cur.getString(3);
              collist_1.add(valueofcol1);
	                collist_2.add(valueofcol2);
	                collist_3.add(valueofcol3);
	            }while(cur.moveToPrevious());
	            	   }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            db2.close();
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
	        list_lv.setAdapter(adapter);
	    }
	    
	    
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_views_inc, menu);
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
			Intent intent=new Intent(ListViewsInc.this,Edit_Activity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		final MediaPlayer mpDT=MediaPlayer.create(this,R.raw.double_tap);
		mpDT.start();
		Toast.makeText(this, "Going Home...", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(ListViewsInc.this,Home1Activity.class);
		startActivity(intent);
	}
	protected Dialog onCreateDialogDelAll(int id)
	{
		switch(id){
		case 0:
			Builder builder=new AlertDialog.Builder(this);
			//builder.setIcon(R.drawable.face);
			builder.setTitle("Do you want to DELETE ALL ENTRIES?");
			builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int whichButton) {
					// TODO Auto-generated method stub
				db2.open();
				if(db2.delete())
				{
					Toast.makeText(getApplicationContext(), "All entries deleted", Toast.LENGTH_LONG).show();
					long c1=db2.insertmaster("00/00/0000", "0", "0");
					getData();
				}
				db2.close();
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

