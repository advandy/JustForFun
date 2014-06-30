package com.example.justforfun;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class HourlyWeatherInfo {
	public ArrayList<String> tempList = new ArrayList<String>();
	public ArrayList<String> dateList = new ArrayList<String>();
	public ArrayList<String>iconList = new ArrayList<String>();
	
	public HourlyWeatherInfo(String url){
		JSONParser jParser = new JSONParser();
		JSONObject json =jParser.getJSONFromUrl(url);
		
		try {
			JSONArray forecast = json.getJSONArray("hourly_forecast");
			for(int i=0;i<12;i++){
				JSONObject tempObject =forecast.getJSONObject(i).getJSONObject("temp");
				JSONObject dateObject = forecast.getJSONObject(i).getJSONObject("FCTTIME");
				String iconURL = forecast.getJSONObject(i).getString("icon_url");
				String date = dateObject.getString("civil");
				String temp = tempObject.getString("metric");		
				
				tempList.add(temp);
				dateList.add(date);
				iconList.add(iconURL);
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}
