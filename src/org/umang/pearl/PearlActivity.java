package org.umang.pearl;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import java.net.URL;
import android.view.LayoutInflater;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.app.AlarmManager;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.TextView;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.app.PendingIntent;
import java.util.Calendar;
import android.os.Handler;
import android.app.ProgressDialog;
import java.io.File;
import java.io.FileInputStream;

import 	android.os.Environment;
import java.net.URI;

public class PearlActivity extends Activity {
    /** Called when the activity is first created. */
	//ArrayAdapter<String> adapter=null;
	AlarmManager am;
	ParsedExampleDataSet parsedExampleDataSet;
	public static int check=0,hello=0;
	ProgressDialog progressbar;
	//SQLiteDatabase db;
	private Handler handler = new Handler();
	DBhelper database;
	public static PearlActivity instance=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        database=new DBhelper();
        database.initialize(this);
        
        //check=0;
        if(isNetworkAvailable())
        {
        
        progressbar = ProgressDialog.show(this,
                "Pearl Event Updates ", "Please Wait....",
                true);
        
        //for ProgressBar
        new Thread(new Runnable() 
        {
            public void run() 
            {
                //---do some work here---
                   	parsingActivity();
                    //setOneTimeAlarm();
                
 
                //---hides the progress bar---
                handler.post(new Runnable() 
                {
                    public void run() 
                    {
                        //---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                    	
                    	progressbar.dismiss();
                    	displayActivity();
                        
                    }
                });
            }    
 
            }).start(); 
        }
        else
        {
        	Toast toast= Toast.makeText(this, "Sorry! Network is not available", Toast.LENGTH_LONG);
        	toast.show();
        }
  	   }
    
    public void displayActivity()
    {
    	 ListView list=(ListView)findViewById(R.id.feedlist);
    	 list.setAdapter(new DesignAdapter());
	}
    
    //for parsing
    public void parsingActivity()
    {
    	
    	try {
    			/* Create a URL we want to load some xml-data from. */
    			if(isNetworkAvailable())
    			{
    				
    				
    			//File file=new File("/sdcard/test.xml");
    			URL url = new URL("http://static.espncricinfo.com/rss/livescores.xml");
    			//FileInputStream file=new FileInputStream("/sdcard/test.xml");
    			

    			/* Get a SAXParser from the SAXPArserFactory. */
    			SAXParserFactory spf = SAXParserFactory.newInstance();
    			SAXParser sp = spf.newSAXParser();

    			/* Get the XMLReader of the SAXParser we created. */
    			XMLReader xr = sp.getXMLReader();
    			/* Create a new ContentHandler and apply it to the XML-Reader*/ 
    			ExampleHandler myExampleHandler = new ExampleHandler();
    			xr.setContentHandler(myExampleHandler);
    			
    			/* Parse the xml-data from our URL. */
    			xr.parse(new InputSource(url.openStream()));
    			//xr.parse(new InputSource(file));
    			/* Parsing has finished. */
    			//check=1;
    			hello=1;
    			/* Our ExampleHandler now provides the parsed data to us. */
    			parsedExampleDataSet = myExampleHandler.getParsedData();
    			
    			
    			}
    			else
    			{
    				Toast toast= Toast.makeText(this, "Sorry! Network is not available", Toast.LENGTH_LONG);
    	        	toast.show();
    			}
    			
    			
    			    			
    		} catch (Exception e) {
    			//Toast toast= Toast.makeText(this, "Sorry! Network is not available", Toast.LENGTH_LONG);
            	//toast.show();
    		}
    	  }
    
    public void setOneTimeAlarm() {
    	// get a Calendar object with current time
    	 Calendar cal = Calendar.getInstance();
    	 // add 5 minutes to the calendar object
    	 cal.add(Calendar.MINUTE, 2);
    		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	  Intent intent = new Intent(this, TimeAlarm.class);
    	  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
    	    intent, PendingIntent.FLAG_ONE_SHOT);
    	  am.set(AlarmManager.RTC_WAKEUP,
    			  cal.getTimeInMillis(), pendingIntent);
    	  //check=3;
    	 }
    
    private boolean isNetworkAvailable()
    {
    	ConnectivityManager cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    	NetworkInfo info=cm.getActiveNetworkInfo();
    	return(info!=null);
    }
    
    class DesignAdapter extends ArrayAdapter<String> {
  	  DesignAdapter() {
  	    super(PearlActivity.this,
  	          android.R.layout.simple_list_item_1,
  	          parsedExampleDataSet.answer());
  	  }
  	  
  	public View getView(int position, View convertView,ViewGroup parent)
  	{
  			LayoutInflater inflater=getLayoutInflater();
  			View row=inflater.inflate(R.layout.row, parent, false);
  			TextView label=(TextView)row.findViewById(R.id.label);
  			TextView time=(TextView)row.findViewById(R.id.time);
  			TextView room=(TextView)row.findViewById(R.id.room);
  			//Design des=d.title.get(position);
  			label.setText(parsedExampleDataSet.answer().get(position));
  			time.setText("Event Time: 10:30-11:30 am");//temporary
  			room.setText("Room: F101");//temporary
  			displayNotification("Pearl Event Alert ");
  			return (row);
    }
  	
  	public void displayNotification(String msg)
  	{
  		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  		Notification notification = new Notification(R.drawable.calendar, msg, System.currentTimeMillis());
  		Context context = getApplicationContext();
  		CharSequence contentTitle = "Pearl notification";
  		CharSequence contentText = "Check out the Event updates!"+check;
  		Intent notificationIntent = new Intent(PearlActivity.this, PearlActivity.class);
  		PendingIntent contentIntent = PendingIntent.getActivity(PearlActivity.this, 0, notificationIntent, 0);

  		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
  		final int HELLO_ID = 1;
  		notification.defaults |= Notification.DEFAULT_SOUND;
  		notification.flags |= Notification.FLAG_AUTO_CANCEL;
  		manager.notify(HELLO_ID, notification);
  		//PearlActivity.check=0;
  				
  	}
    }
   
}