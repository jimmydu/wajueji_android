package com.wajueji.mytest1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Handler handler = new Handler();
	String responseText = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//svn://121.41.88.28
		// StrictMode.setThreadPolicy(new
		// StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		// StrictMode.setVmPolicy(new
		// StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

		setContentView(R.layout.activity_main);
		Button btn1 = (Button) findViewById(R.id.button1);
		
		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new Thread(runnable).start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {

			StringBuffer sbuffer = new StringBuffer();
			try {

				URL url = new URL("http://192.168.1.5:1337/");
				HttpURLConnection huc = (HttpURLConnection) url
						.openConnection();
				if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader input = new BufferedReader(
							new InputStreamReader(huc.getInputStream()), 8192);
					String strLine = null;
					while ((strLine = input.readLine()) != null) {
						sbuffer.append(strLine);
					}
					input.close();
				}
				responseText = sbuffer.toString();
				handler.post(runnableUi);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	Runnable runnableUi = new Runnable(){

		@Override
		public void run() {
			TextView textView1 = (TextView) findViewById(R.id.textView1);
			textView1.setText(responseText);
		}
		
	};
	
}
