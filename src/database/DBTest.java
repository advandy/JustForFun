package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.justforfun.HourlyWeatherInfo;
import com.example.justforfun.JSONParser;


import android.app.ProgressDialog;
import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase {

	public DBTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void initTable(){
		DBHelper db = new DBHelper(getContext());
		db.getWritableDatabase();
	}
	
//	public void insertTable(){
//		DBManager dbm = new DBManager(getContext());
//		String sql = "insert into budget(entryDate, entryAmount) values(?,?)";
//		Object[] argsBind = {"3/28/2014",10.9};
//		dbm.updateBySQL(sql, argsBind);
//	}
//	public void insertTable3(){
//		DBManager dbm = new DBManager(getContext());
//		String sql = "insert into budget(entryDate,traffic,entryAmount) values(?,?,?)";
//		Object[] argsBind = {"3/2/2014",12,12};
//		dbm.updateBySQL(sql, argsBind);
//	}
//	public void deleteAll(){
//		DBManager dbm = new DBManager(getContext());
//		String sql = "delete from budget where entryAmount=?";
//		Object[]argsBind = {10.9};
//		
//		dbm.updateBySQL(sql,argsBind);
//	}
	
	public void query(){
		DBManager dbm = new DBManager(getContext());
		String sql = "select sum(food) as foodTotal from budget";			
		Map map=dbm.queryBySQL(sql, null);
		System.out.println("---food--"+map);
		
	}
	
	public void insertTable2(){
		DBManager dbm = new DBManager(getContext());
		
	//	dbm.addEntryBySQL("3/11/2014", 1.0, 2.0, 1.0, 1.0, 0.0, 0.0);
		
	}
	
	public void deleteEntry(){
		DBManager dbm = new DBManager(getContext());
		
		dbm.deleteBySQL("30/3/2014");
	}
	
	public void addToServer(){
		JSONParser jsonParser = new JSONParser();
		String url = "http://10.0.2.2/justforfun/create_new_entry.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("entryDate", "2011/4/15"));
        params.add(new BasicNameValuePair("food", "9"));
        JSONObject json = jsonParser.makeHttpRequest(url,
                "POST", params);
        
        System.out.println("params: "+params);
        System.out.println("json: "+json);
	
	}
	
	public void getNews(){
		JSONParser jp = new JSONParser();
		ArrayList<String>headlines = new ArrayList<String>();
		ArrayList<String>update = new ArrayList<String>();
		ArrayList<String>url = new ArrayList<String>();
		ArrayList<String>img = new ArrayList<String>();
		JSONObject jpObject = jp.getJSONFromUrl("http://api.espn.com/v1/sports/tennis/news/headlines/top/?apikey=h8kjgpk72pmnwvfmby9wbyp6");
		try {
			JSONArray feed = jpObject.getJSONArray("headlines");
			for(int i=0;i<feed.length();i++){
				headlines.add(feed.getJSONObject(i).getString("headline"));
				update.add(feed.getJSONObject(i).getString("lastModified"));
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
		
		
		System.out.println("espn"+headlines);
		System.out.println("espn"+update);
		System.out.println("espn"+url);
		System.out.println("espn"+img);
	}
	
	public void html(){
		Document doc;
		try {
			doc = Jsoup.connect("http://www.wettpoint.com/results/tennis/").get();
			Elements newsHeadlines = doc.select(".gen");
			for(Element e:newsHeadlines){
			System.out.println("newsheadlines: "+e);}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("htmlerror");
		}
		
		
	}
	public void getJSON(){
		HourlyWeatherInfo hwi = new HourlyWeatherInfo("http://api.wunderground.com/api/fc5dd1a59270b2f8/hourly/q/CA/mannheim.json");
		
		
	}
}
