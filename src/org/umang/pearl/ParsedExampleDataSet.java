package org.umang.pearl;

import java.util.*;
import java.util.regex.Pattern;

import android.util.Log;



public class ParsedExampleDataSet {
    private String extractedString = null;
    List<String> title=new ArrayList<String>();
    private static final String REGEX = "\\d";
    private static final String TAG = "Umang";
    Pattern p;
    //SQLiteDatabase db;
    
    //ArrayAdapter<String> adapter=null;
    private DBhelper d;
    
    ParsedExampleDataSet()
    {
    	//this.pa=pa;
    	p = Pattern.compile(REGEX);
    	d=new DBhelper();
    	//pearl=new PearlActivity();
    //adapter=new ArrayAdapter<String>(new PearlActivity(),android.R.layout.simple_list_item_1,title);
    }

    public String getExtractedString() {
            return extractedString;
    }
    public void setExtractedString(String extractedString) {
            this.extractedString = extractedString;
            String [] items= p.split(this.extractedString);
            if(items[0].length()>5)
            {
            	title.add(items[0]);//temporary
            	Log.v("TAG","Entering in parsedexample");
            	d.inserting(items[0]);//insert into database
            	Log.v("TAG","exiting from parsedexample");
            	
            }
            //adapter.add(extractedString);
    }
    
    public List<String> answer()
    {
    	return title;
    }

      
   /* public ArrayAdapter adapt(){
            return adapter;
    }*/
}