package com.example.androidtestshared;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends Activity{
	static {
		try {
			System.loadLibrary("testlibrary");
			Log.i("TestActivity", "testlibrary  loaded");
		} catch (Exception e) {
			Log.e("TestActivity", "Failed loading testlibrary", e);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		Log.i("TestActivity", "Launching new thread to run the request");

		new Thread(){
			public void run(){
				String url = "https://www.google.com";
				Log.i("TestActivity", "Requesting URL to download: " + url);

				byte[] content = downloadUrl(url);
				if (content == null) {
					Log.e("TestActivity", "No content downloaded");
				} else if (content.length == 0) {
					Log.w("TestActivity", "Empty content downloaded");
				} else if (content.length < 50) {
					Log.i("TestActivity", String.format("%d bytes downloaded all:[%s]", content.length, new String(content)));
				} else {
					Log.i("TestActivity", String.format("%d bytes downloaded 50 chars:[%s]", content.length, new String(content).substring(0,50)));
				}

			}
		}.start();

	}

	public native byte[] downloadUrl(String url);
}; 
