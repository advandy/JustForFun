package com.example.justforfun;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class TennisNews extends Activity {

	ArrayList<String>headlines = new ArrayList<String>();
	ArrayList<String>update = new ArrayList<String>();
	ArrayList<String>url = new ArrayList<String>();
	ArrayList<String>img = new ArrayList<String>();
	ArrayList<String>summary = new ArrayList<String>();
	
	private List<Map<String, Object>> mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tennis_news);
		ListView lv = (ListView)this.findViewById(R.id.tennisnewslistview);
//		load();	
//		mData = getData();
//		MyAdapter adapter = new MyAdapter(this);
//  	  	lv.setAdapter(adapter);	
		
		View view = lv.getRootView();
		view.setBackgroundResource(R.drawable.itf);
  	  	
  	  	loadFromESPN update = new loadFromESPN();
  	  	update.execute();
	}
	
	
	
	class loadFromESPN extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			getNewsFromAPI();
			System.out.println("espn: get news feed");
			mData = getData();
			System.out.println("espn: get image from url");
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			ListView lv = (ListView)TennisNews.this.findViewById(R.id.tennisnewslistview);
			MyAdapter adapter = new MyAdapter(TennisNews.this);
	  	  	lv.setAdapter(adapter);		  	  	
	  	    View view = lv.getRootView();
			view.setBackgroundResource(0);
			view.setBackgroundColor(Color.rgb(0, 0, 0));
			
		}
		
	}
	
	
	private void getNewsFromAPI(){
		JSONParser jp = new JSONParser();
		JSONObject jpObject = jp.getJSONFromUrl("http://api.espn.com/v1/sports/tennis/news/headlines/top/?apikey=h8kjgpk72pmnwvfmby9wbyp6");
		try {
			JSONArray feed = jpObject.getJSONArray("headlines");
			for(int i=0;i<feed.length();i++){
				headlines.add(feed.getJSONObject(i).getString("headline"));
				update.add(feed.getJSONObject(i).getString("lastModified"));
				summary.add(feed.getJSONObject(i).getString("description"));
				url.add(feed.getJSONObject(i).getJSONObject("links").getJSONObject("mobile").getString("href"));
				JSONArray imgObj = feed.getJSONObject(i).getJSONArray("images");
				if(imgObj.length()==0){
					img.add(null);
				}else{
					img.add(imgObj.getJSONObject(0).getString("url"));
					}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("espn"+e);
		}	

	}
	
    
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<headlines.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headline", headlines.get(i));
			map.put("update", update.get(i));
			map.put("url", url.get(i));
			if(img.get(i)!=null){
			LoadImageFromURL imageloader = new LoadImageFromURL();
			Bitmap bm = imageloader.loadImageFromURL(img.get(i));
			map.put("img", bm);}
			else{
				map.put("img", null);
			}
			list.add(map);	
		}	
		
		return list;
	}
	
	
	
	
	public void showInfo(String news){
		new AlertDialog.Builder(this)
		.setTitle("Tennis Top News")
		.setMessage(news)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();
		
	}
	
	
	
	public final class ViewHolder{
		public ImageView img;
		public TextView headline;
		public TextView update;
		public CheckBox box;
	}
	
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			if (convertView == null) {
				
				holder=new ViewHolder();  
				
				convertView = mInflater.inflate(R.layout.news_item, null);
				holder.img = (ImageView)convertView.findViewById(R.id.img);
				holder.headline = (TextView)convertView.findViewById(R.id.title1);
				holder.update = (TextView)convertView.findViewById(R.id.time);
				holder.box = (CheckBox)convertView.findViewById(R.id.checked);
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			
			if(mData.get(position).get("img")!=null){
				
				holder.img.setImageBitmap((Bitmap) mData.get(position).get("img"));
				
			}
			else{
				Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.itf);
				holder.img.setImageBitmap(icon);
			}
						
			
			holder.headline.setText((String)mData.get(position).get("headline"));
			holder.update.setText((String)mData.get(position).get("update"));
			holder.box.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String url = (String) mData.get(position).get("url");
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse(url);
					i.setData(u);
					startActivity(i);
				}
			});
			convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInfo(summary.get(position));					
			}
		});
			
			
			return convertView;
		}
		
	}
}