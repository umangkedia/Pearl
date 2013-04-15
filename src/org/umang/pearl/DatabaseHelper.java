package org.umang.pearl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	  private static final String DATABASE_NAME="db";
	  static final String TITLE="title";
	  static final String VALUE="value";
	  static final String TABLE_NAME="constants";
	  
	  public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, 1);
	  }
	  
	  @Override
	  public void onCreate(SQLiteDatabase db) {
	    db.execSQL("CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT UNIQUE);");
	    Log.v(DATABASE_NAME, "Cannot Create Database");
	  }
	  
	  /*public void insert(String msg)
	  {
		  ContentValues cv=new ContentValues();
		  
		  
			cv.put(TITLE, msg);
			if(getWritableDatabase().insert("constants",TITLE,cv)>=0) PearlActivity.check+=1;
		 
		  
	  }*/
	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //db.execSQL("DROP TABLE IF EXISTS constants");
	    //onCreate(db);
	  }
}