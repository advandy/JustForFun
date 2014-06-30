package database;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbhelper;
	public DBManager(Context context) {
		// TODO Auto-generated constructor stub
		
		dbhelper = new DBHelper(context);
		dbhelper.getWritableDatabase();
	}
	
	//add/update entry into Database
	public boolean addEntryBySQL(String date,double food, double traffic,double sport,double shopping,double social,double other,int day,int month, int year){
		boolean flag = false;
		try {
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			String query ="select * from budget where entryDate=?";
			String[]str = {date};
			Map map = queryBySQL(query, str);
			System.out.println("entryDate: "+map.get("entryDate"));
			if(map.get("entryDate")==null){
				System.out.println("success add");
				String sql ="insert into budget(entryDate,food,traffic,sport,shopping,social,other,entryAmount,day,month,year) values(?,?,?,?,?,?,?,?,?,?,?)";
				Object[] bindArgs = {date,food,traffic,sport,shopping,social,other,food+traffic+sport+shopping+social+other,day,month,year};
				db.execSQL(sql, bindArgs);	
				flag = true;
				
			}
			else{				
				upDateEntryBySQL(date,food,traffic,sport,shopping,social,other);
				System.out.println("you have already this entry");
				flag = true;
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		return flag;		
	}
	
	public void upDateEntryBySQL(String date,double food, double traffic,double sport,double shopping,double social,double other){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String query ="select * from budget where entryDate=?";
		String[]str = {date};
		Map map = queryBySQL(query, str);
		
		double food_old = Double.parseDouble((String) map.get("food"));
		double traffic_old = Double.parseDouble((String) map.get("traffic"));
		double sport_old = Double.parseDouble((String) map.get("sport"));
		double shopping_old = Double.parseDouble((String) map.get("shopping"));
		double social_old = Double.parseDouble((String) map.get("social"));
		double other_old = Double.parseDouble((String) map.get("other"));
		
		double entryAmount_old = Double.parseDouble((String) map.get("entryAmount"));
		String sql = "update budget set food=?,traffic=?,sport=?,shopping=?,social=?,other=?,entryAmount=?where entryDate=?";
		Object[]argsBind = {food_old+food,traffic_old+traffic,sport_old+sport,shopping_old+shopping,social_old+social,other_old+other,entryAmount_old+food+traffic+sport+shopping+social+other,date};
		db.execSQL(sql, argsBind);
		
	}
	
	public void deleteBySQL(String date){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String sql = "delete from budget where entryDate=?";
		Object[]bindArgs = {date};
		db.execSQL(sql, bindArgs);
	}
	
	//query database
	public Map<String,String> queryBySQL(String sql,String[]bindArgs){
		Map<String,String> map = new HashMap<String, String>(); 
		try {
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(sql, bindArgs);
			while(cursor.moveToNext()){
				for(int i=0; i<cursor.getColumnCount();i++){
					String col_name = cursor.getColumnName(i);
					String col_value = cursor.getString(cursor.getColumnIndex(col_name));
					if(col_value==null){
						col_value="";
					}
					map.put(col_name, col_value);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		return map;		
	}
	public Map<String,Object> multiQueryBySQL(String sql,String[]bindArgs){
		Map<String,String> value =new HashMap<String,String>();
		Map<String,Object> map = new HashMap<String, Object>(); 
		try {
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(sql, bindArgs);
			cursor.moveToNext();
			
			for(int i=0;i<cursor.getCount();i++){
				for(int j=0; j<cursor.getColumnCount();j++){
					String col_name = cursor.getColumnName(j);
					String col_value = cursor.getString(cursor.getColumnIndex(col_name));
					if(col_value==null){
						col_value="";
					}
					
					value.put(col_name, col_value);
					
					
				}
				map.put(String.valueOf(i),value);
				value=new HashMap<String,String>();
				cursor.moveToNext();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		return map;		
	}
}
