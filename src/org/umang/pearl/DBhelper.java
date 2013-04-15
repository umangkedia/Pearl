package org.umang.pearl;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBhelper {
	private DatabaseHelper d;
	private SQLiteDatabase db;
	public static final String TAG="umang";
	
		
	public void initialize(Context context)
	{
		d=new DatabaseHelper(context);
		Log.v(TAG, " Create Database");
		open();
		//d.onCreate(db);
	}
	
	public void open() throws SQLException
	{
		db=d.getWritableDatabase();
	}
	
	public void inserting(String msg)
	{
		
		ContentValues cv=new ContentValues();
		  		  
			cv.put(DatabaseHelper.TITLE, msg);
			Log.v(TAG, " I am in insert");
			long insertid=db.insert(DatabaseHelper.TABLE_NAME,null,cv);
		Log.v(TAG, " Exiting insert");
	}

}
