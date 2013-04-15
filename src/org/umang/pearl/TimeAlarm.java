package org.umang.pearl;

import 	android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;


public class TimeAlarm extends BroadcastReceiver {
	NotificationManager nm;
	@Override
	 public void onReceive(Context context, Intent intent) {
		PearlActivity.check=0;
		PearlActivity pearl=new PearlActivity();
		pearl.parsingActivity();
		if(PearlActivity.hello==1)
		{
		nm = (NotificationManager) context
	    .getSystemService(Context.NOTIFICATION_SERVICE);
	  CharSequence from = "Pearl Event Alert";
	  CharSequence message = "Check out the Event updates"+PearlActivity.check;
	  PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
	    new Intent(), 0);
	  Notification notif = new Notification(R.drawable.calendar,
	    "Event", System.currentTimeMillis());
	  notif.setLatestEventInfo(context, from, message, contentIntent);
	  nm.notify(1, notif);
		}
		else
		{
			nm = (NotificationManager) context
		    .getSystemService(Context.NOTIFICATION_SERVICE);
		  CharSequence from = "Pearl Event Alert";
		  CharSequence message = "No Event updates"+PearlActivity.check;
		  PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
		    new Intent(), 0);
		  Notification notif = new Notification(R.drawable.calendar,
		    "Event", System.currentTimeMillis());
		  notif.setLatestEventInfo(context, from, message, contentIntent);
		  nm.notify(1, notif);
		}
	 }
	 
}