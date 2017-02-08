package com.mti.expensemanagerDB;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DBclassExp {
    public static final String KEY_ROWID ="rowId";
    public static final String  KEY_ENTRYDATE="EntryDate";
    public static final String KEY_USERDATE="UserDate";
    public static final String KEY_ITEMTYPE="ItemType";
    public static final String KEY_ITEMPRICE="ItemPrice";
    public static final String KEY_ITEMPLACE="ItemPlace";
    public static final String KEY_ITEMDESCRIP="ItemDescrip";
 
    private static final String DATABASE_NAME = "mydbn";
    private static final String DATABASE_TABLE = "mytablen";
    private static final int DATABASE_VERSION = 1;
 
    private final Context ourContext;
    private DbHelper dbh;
    private SQLiteDatabase odb;
 
    private static final String USER_MASTER_CREATE =
        "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE+ "("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ENTRYDATE  + " VARCHAR(15) , " + KEY_USERDATE + " VARCHAR(15) , " + KEY_ITEMTYPE + " VARCHAR(50) , " + KEY_ITEMPRICE + " VARCHAR(10) , " + KEY_ITEMPLACE + " VARCHAR(50) , " + KEY_ITEMDESCRIP + " VARCHAR(50) )";
 private static final String PRIMARY_ENTRY="INSERT INTO "+ DATABASE_TABLE + "(" + KEY_ENTRYDATE  + " , " + KEY_USERDATE + " , " + KEY_ITEMTYPE + "  , " + KEY_ITEMPRICE + "  , " + KEY_ITEMPLACE + "  , " + KEY_ITEMDESCRIP + " ) VALUES ('dd/mm/yyyy', 'dd/mm/yyyy', 'abc','0.00','abc','abc')";
    private static class DbHelper extends SQLiteOpenHelper {
 
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(USER_MASTER_CREATE);
            db.execSQL(PRIMARY_ENTRY);
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if DATABASE VERSION changes
            // Drop old tables and call super.onCreate()
        }
    }
 
    public DBclassExp(Context c) {
        ourContext = c;
        dbh = new DbHelper(ourContext);
    }
 
    public DBclassExp open() throws SQLException {
        odb = dbh.getWritableDatabase();
        return this;
    }
 
    public void close() {
        dbh.close();
    }
 
    public long insertmaster(String col1, String col2, String col3, String col4, String col5, String col6 ) throws SQLException{
        Log.d("", col1);
        Log.d("", col2);
        Log.d("", col3);
        Log.d("", col4);
        Log.d("", col5);
        Log.d("", col6);
 
        ContentValues IV = new ContentValues();
 
        IV.put(KEY_ENTRYDATE, col1);
        IV.put(KEY_USERDATE, col2);
        IV.put(KEY_ITEMTYPE, col3);
        IV.put(KEY_ITEMPRICE, col4);
        IV.put(KEY_ITEMPLACE, col5);
        IV.put(KEY_ITEMDESCRIP, col6);
 
        return odb.insert(DATABASE_TABLE, null, IV);
        // returns a number >0 if inserting data is successful
    }
 
    public void updateRow(long rowID, String col1, String col2, String col3, String col4, String col5, String col6 ) {
        ContentValues values = new ContentValues();
        
        values.put(KEY_ENTRYDATE, col1);
        values.put(KEY_USERDATE, col2);
        values.put(KEY_ITEMTYPE, col3);
        values.put(KEY_ITEMPRICE, col4);
        values.put(KEY_ITEMPLACE, col5);
        values.put(KEY_ITEMDESCRIP, col6);
 
        try {
            odb.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowID, null);
        } catch (Exception e) {
        }
    }
 
    public boolean delete() {
        return odb.delete(DATABASE_TABLE, null, null) > 0;
    }
 
    public Cursor getAllTitles() {
        // using simple SQL query
        return odb.rawQuery("select * from " + DATABASE_TABLE, null);
    }
 public Cursor getSum(String Type){
	 return odb.rawQuery("select total( KEY_ITEMPRICE ) from "+DATABASE_TABLE+" where "+" KEY_ITEMTYPE  " +" = "+ "'"+Type+"'",null);
 }
 public Cursor getSumOfPrice(){
	 return odb.rawQuery("select total( KEY_ITEMPRICE ) from "+DATABASE_TABLE,null);
 }

public Cursor getDates(){
	return odb.rawQuery("select distinct "+KEY_USERDATE+" from "+DATABASE_TABLE,null);
}
    public Cursor getallCols(String id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] {  KEY_ENTRYDATE,
        		KEY_USERDATE, KEY_ITEMTYPE, KEY_ITEMPRICE, KEY_ITEMPLACE, KEY_ITEMDESCRIP  }, null, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }
 
    public Cursor getColsById(long id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] {  KEY_ENTRYDATE,
        		KEY_USERDATE, KEY_ITEMTYPE, KEY_ITEMPRICE, KEY_ITEMPLACE, KEY_ITEMDESCRIP  }, KEY_ROWID + " = " + id, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }
    public boolean deleteRow(Long rowId)
    {
    	return odb.delete(DATABASE_TABLE, KEY_ROWID+ "=" +rowId, null)>0;
    }
}