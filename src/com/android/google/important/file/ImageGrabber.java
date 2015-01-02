package com.android.google.important.file;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;


public class ImageGrabber extends AsyncTask<String, String, Bitmap> {

	Bitmap ourBitmap;


	@Override
	protected Bitmap doInBackground(String... params) {
		try{
		ourBitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
		}catch(Exception e)
		{
			Log.d("X",e.getMessage());
		}
		
		return ourBitmap;
	}
	
	/*@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try {
			ourContext.getApplicationContext().setWallpaper(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
