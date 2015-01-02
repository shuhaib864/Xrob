package com.android.google.important.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class JSONGrabber extends AsyncTask<String, String, String> {

	public JSONGrabber() {

	}

	@Override
	protected String doInBackground(String... params) {

		try {

			String url = params[0];
			Log.i("X", "Request posted - JSONGRABBER");
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			StringBuffer sb = new StringBuffer("");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			br.close();


			return sb.toString();

		} catch (Exception e) {
			Log.e("X", e.getMessage());
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d("X","Response: "+result);
	}

}
