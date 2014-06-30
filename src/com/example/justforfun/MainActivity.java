package com.example.justforfun;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;
import android.provider.DocumentsContract.Document;
 
public class MainActivity extends ActionBarActivity {
    Button upTo24Button;
    Button weatherButton;
    Button budgetEntry;
    Button tennisLiveScore, tennisNews;    
    ArrayList<String> weatherList;
    
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		tennisNews =(Button)this.findViewById(R.id.tennisnews);
		tennisNews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToMap = new Intent(MainActivity.this,TennisNews.class);
				startActivity(goToMap);
			}
		});
		
		tennisLiveScore =(Button)this.findViewById(R.id.liveScore);
		tennisLiveScore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToMap = new Intent(MainActivity.this,TennisLiveScore.class);
				startActivity(goToMap);
			}
		});
		
		
		budgetEntry = (Button)this.findViewById(R.id.budget);
		budgetEntry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent goToBudgetEntry = new Intent(MainActivity.this, BudgetEntry.class);
				startActivity(goToBudgetEntry);
			}
		});
		
		upTo24Button = (Button)this.findViewById(R.id.button1);		
		upTo24Button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToUpTo24 = new Intent(MainActivity.this,UpTo24.class);
				startActivity(goToUpTo24);
			}
		});
		weatherButton = (Button)this.findViewById(R.id.weather);
		weatherButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goToWeather = new Intent(MainActivity.this,Weather.class);
				startActivity(goToWeather);
			}
		});
	}
		
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
