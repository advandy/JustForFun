package com.example.justforfun;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.justforfun.TennisNews.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TennisLiveScore extends Activity {
	ArrayList<ArrayList<Element>> drawList = new ArrayList<ArrayList<Element>>();
	ArrayList<ArrayList<String>> ScoreList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> gameScoreList = new ArrayList<ArrayList<String>>();
	 ArrayList<String> eventList = new ArrayList<String>();
	 ArrayList<String> fullEventList = new ArrayList<String>();
		ArrayList<Integer> numOfGames = new ArrayList<Integer>();
		ArrayList<String> serve = new ArrayList<String>();
	ArrayList<String> status = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tennis_livescore);
		Button refresh = (Button)this.findViewById(R.id.refresh);
		Button all = (Button)this.findViewById(R.id.all);
		Button live = (Button)this.findViewById(R.id.live);
		
		all.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				drawList =new ArrayList<ArrayList<Element>>();
				ScoreList = new ArrayList<ArrayList<String>>();
				gameScoreList = new ArrayList<ArrayList<String>>();
				status = new ArrayList<String>();
				serve = new ArrayList<String>();
				eventList=new ArrayList<String>();
				numOfGames=new ArrayList<Integer>();
				fullEventList=new ArrayList<String>();
				
				
				ListView lv = (ListView)TennisLiveScore.this.findViewById(R.id.tennisLiveScoreList);
				liveTicker("http://www.livescore.com/tennis/");
				MyAdapter ad = new MyAdapter(getApplicationContext());
				lv.setAdapter(ad);
			}
		});
		
		live.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				drawList =new ArrayList<ArrayList<Element>>();
				ScoreList = new ArrayList<ArrayList<String>>();
				gameScoreList = new ArrayList<ArrayList<String>>();
				status = new ArrayList<String>();
				serve = new ArrayList<String>();
				eventList=new ArrayList<String>();
				numOfGames=new ArrayList<Integer>();
				fullEventList=new ArrayList<String>();
				
				
				ListView lv = (ListView)TennisLiveScore.this.findViewById(R.id.tennisLiveScoreList);
				liveTicker("http://www.livescore.com/tennis/live");
				MyAdapter ad = new MyAdapter(getApplicationContext());
				lv.setAdapter(ad);
				
			}
		});
		
		ListView lv = (ListView)this.findViewById(R.id.tennisLiveScoreList);
		View view = lv.getRootView();
		view.setBackgroundColor(Color.rgb(0,0,0));
		liveTicker("http://www.livescore.com/tennis/");
		MyAdapter ad = new MyAdapter(getApplicationContext());
		lv.setAdapter(ad);
		
		
	}
	
	public void liveTicker(String url){
		
	        Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Elements server = doc.select(".fd");
				
				       
		        for(Element s : server){
		        	if(s.text().equals("")){
		        		
		        	}else
		        	status.add(s.text());
		        	
		        }
		     	        
			        Elements serves = doc.select(".serv");
			        Elements elements = doc.select(".ft");
			        Elements table = doc.select("table.league-multi");
			       for(Element e:table){
			    	   int count = 0;
			    	  eventList.add(e.select(".league").text());
			    	 
			    	  for(Element row:e.select("tr")){
			    		  count+=1;
			    		  Elements tds = row.select("td");
			    	
			    		  if(tds.size()>1){
			    			  if (tds.get(1).className().equals("ball-cell ball ")||tds.get(1).className().equals(" ball ")||tds.get(1).className().equals("ball-cell ball doubles")||tds.get(1).className().equals(" ball doubles")){
			    				  serve.add("serve");
			    			  }else serve.add("receive");
			    			  System.out.println(tds.get(1));
			    		  }
			    	  }
			    	  numOfGames.add((count-1)/2);
			       }
			       
			       
			       System.out.println(serve);
			       System.out.println(numOfGames);
		        
			    for(int i=0;i<eventList.size();i++){
			    	for (int j=0;j<numOfGames.get(i);j++){
			    		fullEventList.add(eventList.get(i));
			    	}
			    }
			       
		        for (int i= 0;i<elements.size();i++){
		        	ArrayList<Element>draw = new ArrayList<Element>();
		        	ArrayList<String>score = new ArrayList<String>();
		        	draw.add(elements.get(i));
		        	draw.add(elements.get(i+1));	        	
		        	score.add(serves.get(i).text());
		        	score.add(serves.get(i+1).text());
		        	i+=1;
		        	drawList.add(draw);
		        	gameScoreList.add(score);
		        }
		        System.out.println(drawList);
		        System.out.println(gameScoreList);
		        
		        Elements scores = doc.select(".ts");
		        for(int i=0;i<scores.size();i++){
		        	ArrayList<String>score = new ArrayList<String>();
		          for(int j=i;j<i+10;j++){
		        	  if(scores.get(j).text().equals(""))continue;
		        	  else {
		        		  score.add(scores.get(j).text());
		        	  }
		          }
		          ScoreList.add(score);
		          i=i+9;
		          
		        	
		        }	        
		        System.out.println(ScoreList);
		        
		        for(int i=0;i<drawList.size();i++){		        	
		        	
		        	ArrayList<String> gameResult = ScoreList.get(i);
		        	ArrayList<String> result1 = new ArrayList<String>();
		        	ArrayList<String> result2 = new ArrayList<String>();
		        	
		        	for(int j=0;j<gameResult.size()/2;j++){
		        		result1.add(gameResult.get(j));
		        	}
		        	
		        	for(int k=gameResult.size()/2;k<gameResult.size();k++){
		        		result2.add(gameResult.get(k));
		        	}
		        	
		        	
		        	
		        }
		      
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("loadingerror");
			}

	}
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return drawList.size();
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
		
		
		public final class ViewHolder{
			public TextView p1,p2,score1,score2,status,g1,g2,event;
			public ImageView serve1,serve2;
			
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			if (convertView == null) {				
				holder=new ViewHolder();				
				convertView = mInflater.inflate(R.layout.livescore, null);
				holder.p1 = (TextView)convertView.findViewById(R.id.p1);
				holder.p2 = (TextView)convertView.findViewById(R.id.p2);
				holder.score1 = (TextView)convertView.findViewById(R.id.score1);
				holder.score2 = (TextView)convertView.findViewById(R.id.score2);
				holder.status = (TextView)convertView.findViewById(R.id.status);
				holder.g1 = (TextView)convertView.findViewById(R.id.g1);
				holder.g2 = (TextView)convertView.findViewById(R.id.g2);
				holder.event = (TextView)convertView.findViewById(R.id.event);
				holder.serve1 = (ImageView)convertView.findViewById(R.id.serve1);
				holder.serve2= (ImageView)convertView.findViewById(R.id.serve2);
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.event.setText(fullEventList.get(position));
			
			if(drawList.get(position).get(0).select("strong").isEmpty()){
				holder.p1.setText(drawList.get(position).get(0).text());
			}else{
				holder.p1.setText(drawList.get(position).get(0).text()+" ✓");
			
				
			}
			
			Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.serve);
			holder.serve1.setImageBitmap(null);
			holder.serve2.setImageBitmap(null);
			
			if(serve.get(position*2).equals("serve")){
				if(!status.get(position).equals("FT")){
				holder.serve1.setImageBitmap(icon);}
			}
			
			if(serve.get(position*2+1).equals("serve")){
				if(!status.get(position).equals("FT"))
				holder.serve2.setImageBitmap(icon);
			}
			
			if(drawList.get(position).get(1).select("strong").isEmpty()){
				holder.p2.setText(drawList.get(position).get(1).text());
			}else{
				holder.p2.setText(drawList.get(position).get(1).text()+" ✓");
					
			}
			
			
			
			holder.status.setText(status.get(position));
			holder.g1.setText(gameScoreList.get(position).get(0));
			holder.g2.setText(gameScoreList.get(position).get(1));
			
			ArrayList<String>score = ScoreList.get(position);
			ArrayList<String> s1 = new ArrayList<String>();
			ArrayList<String>s2 = new ArrayList<String>();
			for(int i=0;i<score.size()/2;i++){
				if(score.get(i).length()>1){
					String x = score.get(i).substring(0,1);
					String y = score.get(i).substring(1);
					s1.add(x+"("+y+")"+" ");
				}else
				s1.add(score.get(i)+" ");
			}
			
			for(int j=score.size()/2;j<score.size();j++){
				if(score.get(j).length()>1){
					String x = score.get(j).substring(0,1);
					String y = score.get(j).substring(1);
					s2.add(x+"("+y+")"+" ");
				}else				
				s2.add(score.get(j)+" ");
			}
			
			String str1 = "";
			for(String s:s1){
				str1 +=s+" ";
			}
			
			String str2 ="";
			for(String s:s2){
				str2 +=s+" ";
			}
			
			holder.score1.setText(str1);
			holder.score2.setText(str2);

			return convertView;
		}
		
	}	
	
	
	
}
