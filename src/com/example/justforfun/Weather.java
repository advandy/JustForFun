package com.example.justforfun;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.justforfun.BudgetEntry.bsAdapter;

import bar_chart.HorizontalListView;

import database.DBManager;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Weather extends Activity {
	ArrayList<String> weatherListDay,tempList,dateList,iconList;
	ArrayList<String> weatherListWeather;
	ArrayList<String> weatherListDegree;
	ArrayList<Bitmap>bitMapArray;
	Integer[] dayImageList,weatherIconList;	
	AnimationDrawable frameAnimation;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	TextView tv6;
	TextView tv7;
	ImageView iv1;
	ProgressBar load;
	HorizontalListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		
		tv1 = (TextView)this.findViewById(R.id.textView1);
		tv2 = (TextView)this.findViewById(R.id.textView2);
		tv3 = (TextView)this.findViewById(R.id.textView3);
		tv4 = (TextView)this.findViewById(R.id.textView4);
		tv5 = (TextView)this.findViewById(R.id.textView5);
		tv6 = (TextView)this.findViewById(R.id.textView6);
		tv7 = (TextView)this.findViewById(R.id.textView7);
		iv1 = (ImageView)this.findViewById(R.id.imageView1);
		iv1.setEnabled(false);
		System.out.println(iv1.isClickable());
		load = (ProgressBar)this.findViewById(R.id.weatherloading);		 
		
		
		
		
		loadCache();
		setWeatherBG("", "");		
		final weatherReportTask wrt = new weatherReportTask();
	    wrt.execute("673711");
	    
	   
	    
	    
	    
	    
	    iv1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				View popupView = getLayoutInflater().inflate(R.layout.weather_detail, null);
				
				listView=(HorizontalListView)popupView.findViewById(R.id.listview2); 
				String[]strList = new String[tempList.size()];
			    strList = tempList.toArray(strList);
			    System.out.println("strList"+strList);
			    bsAdapter bs =new bsAdapter(Weather.this,strList);
			    
			    System.out.println("strList"+strList.length);
		    	listView.setAdapter(bs);
											  
				PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		        mPopupWindow.setTouchable(true);
		        mPopupWindow.setOutsideTouchable(true);
		        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		        mPopupWindow.showAsDropDown(popupView);
				}	
				
			
		});
         
	    
}
	
	
	public class bsAdapter extends BaseAdapter
    {
        Activity cntx;
        String[] array;
        public bsAdapter(Activity context,String[] arr)
        {
            // TODO Auto-generated constructor stub
            this.cntx=context;
            this.array = arr;
 
        }
 
        

		public int getCount()
        {
            // TODO Auto-generated method stub
            return array.length;
        }
 
        public Object getItem(int position)
        {
            // TODO Auto-generated method stub
            return array[position];
        }
 
        public long getItemId(int position)
        {
            // TODO Auto-generated method stub
            return array.length;
        }
 
      

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			  System.out.println("row is returned");
			// TODO Auto-generated method stub
			    View row=null;
	            LayoutInflater inflater=cntx.getLayoutInflater();
	            row=inflater.inflate(R.layout.list_item2, null);     
	                     
	            TextView tvcol = (TextView)row.findViewById(R.id.editText1); 
	            TextView temcol = (TextView)row.findViewById(R.id.editText2);
	            TextView timeText = (TextView)row.findViewById(R.id.timeTextView);
	            ImageView img = (ImageView)row.findViewById(R.id.imageView1);
	                    
	            temcol.setText(tempList.get(position)+"°C");
	            timeText.setText(dateList.get(position));
                img.setImageBitmap(bitMapArray.get(position));
	           
	         	            
	           ArrayList<Double> temp = new ArrayList<Double>();
	            for (int i = 0; i < tempList.size(); i++) {
	                temp.add(i, Double.parseDouble(tempList.get(i)));
	            }
	            
	            Double[] tempArray  = temp.toArray(new Double[temp.size()]);
	            
	            
	            List<Double> b = Arrays.asList(tempArray);
	            Double highest = (Collections.max(b));	             
	           
	           
	           
	            tvcol.setHeight((int) (listView.getHeight()*tempArray[position]*0.6/highest));             
	                            
	            System.out.println("check 3");
	           	             
	        return row;
		}
		
		
    }
	void setWeatherBG(String sunrise,String sunset){
		Date date = new Date();
	    SimpleDateFormat simpDate = new SimpleDateFormat("kk:mm:ss");
	    String currentTime=simpDate.format(date);
	    View view = tv1.getRootView();
		
	    try {
			Date day = new SimpleDateFormat("kk:mm:ss").parse("06:30:00");
			Date night = new SimpleDateFormat("kk:mm:ss").parse("20:00:00");
			Date current = new SimpleDateFormat("kk:mm:ss").parse(currentTime);
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			Calendar calendarCurrent = Calendar.getInstance();
			calendar1.setTime(day);
			calendar2.setTime(night);
			calendarCurrent.setTime(current);
			
			if(calendarCurrent.getTime().after(calendar1.getTime())&&calendarCurrent.getTime().before(calendar2.getTime())){
				view.setBackgroundResource(R.drawable.spin_animation_day);
				//iv1.setBackgroundResource(R.drawable.dayicon);
			}
			else{
				view.setBackgroundResource(R.drawable.spin_animation);
				//iv1.setBackgroundResource(R.drawable.moon);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 frameAnimation = (AnimationDrawable) view.getBackground();
		 view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frameAnimation.start();
			}
		});
		
	}
	
	void loadCache(){
			
		SharedPreferences settings = getSharedPreferences("MyPre", 0);
		tv1.setText(settings.getString("degree1", ""));
		tv2.setText(settings.getString("degree2", ""));
		tv3.setText(settings.getString("degree3", ""));
		tv4.setText(settings.getString("degree4", ""));
		tv5.setText(settings.getString("degree5", ""));
		tv6.setText(settings.getString("update", ""));
		tv7.setText(settings.getString("currentTemp", ""));
		ImageAdapter ia = new ImageAdapter(this);
		GridView gridview = (GridView) findViewById(R.id.gridView1);
		ia.mThumbIds =new Integer[] {settings.getInt("d0", 0),settings.getInt("d1", 0),settings.getInt("d2", 0),settings.getInt("d3", 0),settings.getInt("d4", 0)};
		dayImageList=ia.mThumbIds;
	    gridview.setAdapter(ia);
	    
	    ImageAdapter ia2 = new ImageAdapter(this);
		GridView gridview2 = (GridView) findViewById(R.id.gridView2);
		ia2.mThumbIds =new Integer[] {settings.getInt("w0", 0),settings.getInt("w1", 0),settings.getInt("w2", 0),settings.getInt("w3", 0),settings.getInt("w4", 0)};
		dayImageList=ia2.mThumbIds;
	    gridview2.setAdapter(ia2);
	}
	
	public void setActivityBackgroundColor(int color) {
	    View view = Weather.this.getWindow().getDecorView();
	    view.setBackgroundColor(color);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences settings = getSharedPreferences("MyPre", 0);
		SharedPreferences.Editor ed = settings.edit();
		ed.putString("degree1", tv1.getText().toString());
		ed.putString("degree2", tv2.getText().toString());
		ed.putString("degree3", tv3.getText().toString());
		ed.putString("degree4", tv4.getText().toString());
		ed.putString("degree5", tv5.getText().toString());
		ed.putString("update", tv6.getText().toString());
		ed.putString("currentTemp", tv7.getText().toString());
		if (dayImageList!=null&&weatherIconList!=null){
		for (int i=0;i<dayImageList.length;i++){
			ed.putInt("d"+i, dayImageList[i]);
		}
		
		for (int i=0;i<weatherIconList.length;i++){
			ed.putInt("w"+i, weatherIconList[i]);
		}
		}
		ed.commit();
	}
	

	private class weatherReportTask extends AsyncTask{
		

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
			org.w3c.dom.Document doc = (Document) result;
			NodeList titleNode = doc.getElementsByTagName("yweather:forecast");
			NodeList description =doc.getElementsByTagName("description");
			NodeList lastBuilt =doc.getElementsByTagName("lastBuildDate");
			NodeList currentTemp = doc.getElementsByTagName("yweather:condition");
//			String temp = currentTemp.item(0).getAttributes().getNamedItem("temp").getNodeValue();
//			tv7.setText(temp+"°C");
			Node nodeDescription = description.item(0);
			Node nodeLastBuild = lastBuilt.item(0);
			String strDescription = nodeDescription.getTextContent();
			String strLastBuild = nodeLastBuild.getTextContent();
			tv6.setText("   "+strDescription+"\n   Last Updated:\n   "+strLastBuild);
			
			weatherListDay = new ArrayList<String>();
			weatherListWeather = new ArrayList<String>();
			weatherListDegree = new ArrayList<String>();
			
			for (int i =0; i<titleNode.getLength();i++){
				Node nodeTitle = titleNode.item(i);				
				String day = nodeTitle.getAttributes().getNamedItem("day").getNodeValue();
				
				String low = nodeTitle.getAttributes().getNamedItem("low").getNodeValue();
				String high = nodeTitle.getAttributes().getNamedItem("high").getNodeValue();
				String code = nodeTitle.getAttributes().getNamedItem("code").getNodeValue();
				Log.i("Weather", code);
				weatherListDay.add(day);
				weatherListWeather.add(code);
				weatherListDegree.add(low+"°C/"+high+"°C");
			}
			updateUI();
			iv1.setEnabled(true);
			load.setVisibility(ProgressBar.INVISIBLE);}
			else{
				Toast.makeText(getApplicationContext(), "Network Access Failure", Toast.LENGTH_LONG).show();
				load.setVisibility(ProgressBar.INVISIBLE);
			}
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			URL url;
			try {				
				url = new URL("http://weather.yahooapis.com/forecastrss?w="+params[0]+"&u=c");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				org.w3c.dom.Document doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();				
				//load info from the weather channel api
				HourlyWeatherInfo hwi = new HourlyWeatherInfo("http://api.wunderground.com/api/fc5dd1a59270b2f8/hourly/q/CA/mannheim.json");
				iconList = hwi.iconList;
				tempList = hwi.tempList;
				dateList = hwi.dateList;
				LoadImageFromURL lifu = new LoadImageFromURL();
				bitMapArray = new ArrayList<Bitmap>();
				for (int i=0;i<iconList.size();i++){
	            Bitmap bm =lifu.loadImageFromURL(iconList.get(i));
	            bitMapArray.add(bm);
	            }
				
				return doc;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;			
		}		
	}


public void updateUI(){
	tv1.setText(weatherListDegree.get(0));
	tv2.setText(weatherListDegree.get(1));
	tv3.setText(weatherListDegree.get(2));
	tv4.setText(weatherListDegree.get(3));
	tv5.setText(weatherListDegree.get(4));	
	
	
	ArrayList<Integer> dList = new ArrayList<Integer>();
	for (int i=0; i<weatherListDay.size();i++){
		String day = weatherListDay.get(i);
		if (day.equals("Mon")){
			dList.add(R.drawable.mon);
		}
		else if (day.equals("Tue")){
			dList.add(R.drawable.tue);
		}else if (day.equals("Wed")){
			dList.add(R.drawable.wed);
		}else if (day.equals("Thu")){
			dList.add(R.drawable.thu);
		}else if (day.equals("Fri")){
			dList.add(R.drawable.fri);
		}else if (day.equals("Sat")){
			dList.add(R.drawable.sat);
		}else if (day.equals("Sun")){
			dList.add(R.drawable.sun);
		}
		else dList.add(R.drawable.sun);
	}
	GridView gridview = (GridView) findViewById(R.id.gridView1);
	ImageAdapter ia = new ImageAdapter(this);
	ia.mThumbIds =new Integer[] {dList.get(0),dList.get(1),dList.get(2),dList.get(3),dList.get(4)};
	dayImageList=ia.mThumbIds;
    gridview.setAdapter(ia);
    
    if(iconList.get(0)!=null){
    	LoadImageFromURL lifu = new LoadImageFromURL();
    	Bitmap bm =lifu.loadImageFromURL(iconList.get(0));
    	if(bm!=null) {
    		iv1.setImageBitmap(bm);}
    	
    }
    
    if(tempList.get(0)!=null){
    	tv7.setText(tempList.get(0)+"°C");
    }
    
    
    ArrayList<Integer> wList = new ArrayList<Integer>();
	for (int i=0; i<weatherListWeather.size();i++){
		String day = weatherListWeather.get(i);
		if (day.equals("Mon")){
			wList.add(R.drawable.mon);
		}
		else if (day.equals("31")||day.equals("32")||day.equals("33")||day.equals("34")){
			wList.add(R.drawable.sunny);
			
		}else if (day.equals("29")||day.equals("30")){
			wList.add(R.drawable.most_sunny);
			
		}else if (day.equals("24")){
			wList.add(R.drawable.windy);
		}else if (day.equals("27")||day.equals("28")||day.equals("44")){
			wList.add(R.drawable.cloudy);
			
		}else if (day.equals("16")||day.equals("43")||day.equals("46")){
			wList.add(R.drawable.snow);
		}else if (day.equals("5")||day.equals("6")||day.equals("10")){
			wList.add(R.drawable.rainy);
		}
		else if (day.equals("11")||day.equals("12")||day.equals("39")){
			wList.add(R.drawable.shower);
		}
		else wList.add(R.drawable.ic_launcher);
	}
    
    GridView gridview2 = (GridView) findViewById(R.id.gridView2);
	ImageAdapter ia2 = new ImageAdapter(this);
	ia2.mThumbIds =new Integer[] {wList.get(0),wList.get(1),wList.get(2),wList.get(3),wList.get(4)};
	weatherIconList = ia2.mThumbIds;
    gridview2.setAdapter(ia2);

	
}
}

