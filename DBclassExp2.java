package com.mti.Income_Savings_DB;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DBclassExp2 {
 
	public static final String KEY_ROWID = "rowId";
	public static final String KEY_LMDATE  = "lmdate";
    public static final String KEY_INCOME = "income";                         
    public static final String KEY_SAVINGS = "savings";
 
    private static final String DATABASE_NAME = "mydbn2";
    private static final String DATABASE_TABLE = "mytablen";
    private static final int DATABASE_VERSION = 1;
 
    private final Context ourContext;
    private DbHelper dbh;
    private SQLiteDatabase odb;
 
    private static final String USER_MASTER_CREATE =
        "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE+ "("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_LMDATE  + " VARCHAR(50) , " + KEY_INCOME + " VARCHAR(50) , " + KEY_SAVINGS + " VARCHAR(50) )";
    private static final String PRIMARY_ENTRY="INSERT INTO "+ DATABASE_TABLE + "(" + KEY_LMDATE  + " , " + KEY_INCOME + " , " + KEY_SAVINGS  + " ) VALUES ('dd/mm/yyyy','0.00','0.00')";
 
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
 
    public DBclassExp2(Context c) {
        ourContext = c;
        dbh = new DbHelper(ourContext);
    }
 
    public DBclassExp2 open() throws SQLException {
        odb = dbh.getWritableDatabase();
        return this;
    }
 
    public void close() {
        dbh.close();
    }
 
    public long insertmaster(String col1, String col2, String col3) throws SQLException{
        Log.d("", col1);
        Log.d("", col2);
        Log.d("", col3);
 
        ContentValues IV = new ContentValues();
 
        IV.put(KEY_LMDATE, col1);
        IV.put(KEY_INCOME, col2);
        IV.put(KEY_SAVINGS, col3);
 
        return odb.insert(DATABASE_TABLE, null, IV);
        // returns a number >0 if inserting data is successful
    }
 
    public void updateRow(long rowID, String col1, String col2, String col3) {
        ContentValues values = new ContentValues();
        values.put(KEY_LMDATE, col1);
        values.put(KEY_INCOME, col2);
        values.put(KEY_SAVINGS, col3);
 
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
  
    public Cursor getallCols(String id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] { KEY_LMDATE,
                KEY_INCOME,KEY_SAVINGS }, null, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }
 
    public Cursor getColsById(String id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] { KEY_LMDATE,
                KEY_INCOME,KEY_SAVINGS }, KEY_ROWID + " = " + id, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }
}