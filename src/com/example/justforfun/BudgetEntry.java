package com.example.justforfun;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import bar_chart.HorizontalListView;
import java.util.Map;

import database.DBManager;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


public class BudgetEntry extends Activity {
	private DatePicker dp;
	private ImageButton foodButton;
	private ImageButton trafficButton;
	private ImageButton shoppingButton;
	private ImageButton sportButton;
	private ImageButton socialButton;
	private ImageButton otherButton;
	private ImageButton addButton;
	private ImageButton queryButton;
	private PopupWindow mPopupWindow;
	private Double food;
	private Double traffic;
	private Double shopping;
	private Double sport;
	private Double social;
	private Double other;
	private Double sum;
	private TextView tvfood;
	private TextView tvtraffic;
	private TextView tvshopping;
	private TextView tvsport;
	private TextView tvsocial;
	private TextView tvother;
	private Button dayButton;
	private Button monthButton;
	private Button yearButton;
	private TextView result,monthlySum;
	private Spinner monthDropDown;
	private Button deleteButton;
	private int selectedMonth, selectedYear;
	
	
	public void setFood(Double food) {
		this.food = food;
		this.tvfood.setText(food.toString()+"€");
		
	}

	public void setTraffic(Double traffic) {
		this.traffic = traffic;
		this.tvtraffic.setText(traffic.toString()+"€");
		
	}

	public void setShopping(Double shopping) {
		this.shopping = shopping;
		this.tvshopping.setText(shopping.toString()+"€");
	}

	public void setSport(Double sport) {
		this.sport = sport;
		this.tvsport.setText(sport.toString()+"€");
	}

	public void setSocial(Double social) {
		this.social = social;
		this.tvsocial.setText(social.toString()+"€");
	}

	public void setOther(Double other) {
		this.other = other;
		this.tvother.setText(other.toString()+"€");
	}


	

	public BudgetEntry() {
		// TODO Auto-generated constructor stub
		this.food=0.0;
		this.traffic=0.0;
		this.shopping=0.0;
		this.sport=0.0;
		this.social=0.0;
		this.other=0.0;
	}
	
	
	private HorizontalListView listView;
	private Double[] itemSum;
	private String[] itemLabel;
	private int[]barHeight;
	private Double highest; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.budget_entry);
    	TabHost tabHost=(TabHost)this.findViewById(R.id.tabHost);
    	tabHost.setup();

    	TabSpec spec1=tabHost.newTabSpec("Tab 1");
    	spec1.setContent(R.id.tab1);
    	spec1.setIndicator("",getResources().getDrawable(R.drawable.money));
    	
    	TabSpec spec2=tabHost.newTabSpec("Tab 2");
    	spec2.setIndicator("",getResources().getDrawable(R.drawable.statistics));    	
    	spec2.setContent(R.id.tab2);   	

    	tabHost.addTab(spec1);
    	tabHost.addTab(spec2);   
    	
    	   	
    	
    	//Tab 1
    	dp = (DatePicker)this.findViewById(R.id.datePicker1);
    	foodButton = (ImageButton)this.findViewById(R.id.imageButton1);
    	trafficButton = (ImageButton)this.findViewById(R.id.imageButton2);
    	shoppingButton = (ImageButton)this.findViewById(R.id.imageButton3);
    	sportButton = (ImageButton)this.findViewById(R.id.imageButton4);
    	socialButton = (ImageButton)this.findViewById(R.id.imageButton5);
    	otherButton = (ImageButton)this.findViewById(R.id.imageButton6);
    	addButton = (ImageButton)this.findViewById(R.id.imageButton7);
    	queryButton = (ImageButton)this.findViewById(R.id.imageButton8);
    	
    	tvfood = (TextView)this.findViewById(R.id.textView1);
    	tvtraffic = (TextView)this.findViewById(R.id.textView2);
    	tvshopping = (TextView)this.findViewById(R.id.textView3);
    	tvsport = (TextView)this.findViewById(R.id.textView4);
    	tvsocial = (TextView)this.findViewById(R.id.textView5);
    	tvother = (TextView)this.findViewById(R.id.textView6);
    	
    	
    	monthDropDown = (Spinner)this.findViewById(R.id.spinner1);
    	monthDropDown.setSelection(3, true);
	  	
    	String[] items = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
    	monthDropDown.setAdapter(adapter);
    	setSpinnerItemSelectedByValue(monthDropDown, items[dp.getMonth()]);
    	
    	monthDropDown.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    	
    	updateBarChart(dp.getYear(),dp.getMonth()+1);
    	
    	
    	
    	
    	foodButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    itemEntry(v,1);
				
			}
		});
        trafficButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemEntry(v,2);
			}
		});
        shoppingButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemEntry(v,3);
			}
		});
       sportButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemEntry(v,4);
			}
		});
       socialButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemEntry(v,5);
			}
		});
       otherButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemEntry(v,6);
			}
		});
       addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBManager dbm = new DBManager(getBaseContext());
				int month =dp.getMonth()+1;
				dbm.addEntryBySQL(dp.getDayOfMonth()+"/"+month+"/"+dp.getYear(), food, traffic, sport, shopping, social, other,dp.getDayOfMonth(),month,dp.getYear());
				Context context = getApplicationContext();			
			    setFood(0.0);
			    setTraffic(0.0);
			    setShopping(0.0);
			    setSport(0.0);
			    setSocial(0.0);
			    setOther(0.0);				
				CharSequence text = "Entry Saved!";
				int duration = Toast.LENGTH_SHORT;
				updateBarChart(dp.getYear(), month);
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
       queryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View popupView = getLayoutInflater().inflate(R.layout.pop_query, null);
				CheckedTextView tv =(CheckedTextView)popupView.findViewById(R.id.checkedTextView1);							  
		        mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		        mPopupWindow.setTouchable(true);
		        mPopupWindow.setOutsideTouchable(true);
		        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		        mPopupWindow.showAtLocation(findViewById(R.id.textView1),Gravity.BOTTOM,0,0); 
		        
		       DBManager dbm = new DBManager(getBaseContext());
		       String sql = "select* from budget where year=? AND month=? AND day=?";
		       String[]bindArgs = {String.valueOf(dp.getYear()),String.valueOf(dp.getMonth()+1),String.valueOf(dp.getDayOfMonth())};
		       Map map=dbm.queryBySQL(sql, bindArgs);
		       tv.setText(map.get("entryDate")+":\n"+"Food: "+map.get("food")+"\n"+ "Traffic: "+map.get("traffic")+"\n"+"Shopping: "+map.get("shopping")+"\n"+"Sport: "+map.get("sport")+"\n"+"Social: "+map.get("social")+"\n"+"Other: "+map.get("other")+"\n"+"totalSum: "+map.get("entryAmount"));
				
		       deleteButton = (Button)popupView.findViewById(R.id.deleteItem);
		       deleteButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DBManager dbm = new DBManager(getBaseContext());
						String date = String.valueOf(dp.getDayOfMonth())+"/"+String.valueOf(dp.getMonth()+1)+"/"+String.valueOf(dp.getYear());
						dbm.deleteBySQL(date);
                        updateBarChart(dp.getYear(), dp.getMonth()+1);
						mPopupWindow.dismiss();
						Toast.makeText(getApplicationContext(), date+" item deleted", Toast.LENGTH_SHORT).show();
					}
				});
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
			// TODO Auto-generated method stub
			    View row=null;
	            LayoutInflater inflater=cntx.getLayoutInflater();
	            row=inflater.inflate(R.layout.list_item, null);
	             
	            DecimalFormat df = new DecimalFormat("#.##");
	            final TextView title    =   (TextView)row.findViewById(R.id.title);
	            TextView tvcol1 =   (TextView)row.findViewById(R.id.colortext01);
	            TextView tvcol2 =   (TextView)row.findViewById(R.id.colortext02);
	            TextView tvcol3 =   (TextView)row.findViewById(R.id.colortext03);
	            TextView tvcol4 =   (TextView)row.findViewById(R.id.colortext04);
	            TextView tvcol5 =   (TextView)row.findViewById(R.id.colortext05);
	            TextView tvcol6 =   (TextView)row.findViewById(R.id.colortext06);
	             
	            TextView tv1     =   (TextView)row.findViewById(R.id.text01);
	            TextView tv2     =   (TextView)row.findViewById(R.id.text02);
	            TextView tv3     =   (TextView)row.findViewById(R.id.text03);
	            TextView tv4     =   (TextView)row.findViewById(R.id.text04);
	            TextView tv5     =   (TextView)row.findViewById(R.id.text05);
	            TextView tv6     =   (TextView)row.findViewById(R.id.text06);
	            
	            title.setText(itemLabel[position]);
	            
	            List<Double> b = Arrays.asList(itemSum);
	            highest = (Collections.max(b));	             
	           
	            tvcol1.setHeight((int) (listView.getHeight()*itemSum[0]*0.8/highest));
	            tvcol2.setHeight((int) (listView.getHeight()*itemSum[1]*0.8/highest));
	            tvcol3.setHeight((int) (listView.getHeight()*itemSum[2]*0.8/highest));
	            tvcol4.setHeight((int) (listView.getHeight()*itemSum[3]*0.8/highest));
	            tvcol5.setHeight((int) (listView.getHeight()*itemSum[4]*0.8/highest));
	            tvcol6.setHeight((int) (listView.getHeight()*itemSum[5]*0.8/highest));          
	            
	             
	            tv1.setText(df.format(itemSum[0]));
	            tv2.setText(df.format(itemSum[1]));
	            tv3.setText(df.format(itemSum[2]));
	            tv4.setText(df.format(itemSum[3]));
	            tv5.setText(df.format(itemSum[4]));
	            tv6.setText(df.format(itemSum[5]));
	            
	            row.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						View popupView = getLayoutInflater().inflate(R.layout.pop_item_entry_table, null);
						ListView lv =(ListView)popupView.findViewById(R.id.listView1);
				        List<String> data = new ArrayList<String>();
				        DBManager dbm = new DBManager(getApplicationContext());
				        String sql = "select *from budget where month=? and year=? order by day";
				        String[]bindArgs={String.valueOf(selectedMonth),String.valueOf(selectedYear)};
				        Map map = dbm.multiQueryBySQL(sql, bindArgs);
				        Map<String,String> map2 = new HashMap<String,String>();
				        for (int i=0;i<map.size();i++){
				        	map2 = (Map<String, String>) map.get(String.valueOf(i));
				        	String str = map2.get("entryDate")+": food: "+map2.get("food")+" traffic: "+map2.get("traffic") +" shopping: "+map2.get("shopping")+" social: "+map2.get("social")+" sport: "+map2.get("sport")+" other: "+map2.get("other");
				        	data.add(str);
				        }
				        
				        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BudgetEntry.this, R.layout.simple_text,
				                R.id.text1, data);
				        lv.setAdapter(adapter);
				       
						  
				        mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
				        mPopupWindow.setTouchable(true);
				        mPopupWindow.setOutsideTouchable(true);
				        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
				        mPopupWindow.showAtLocation(findViewById(R.id.monthlySum), Gravity.CLIP_HORIZONTAL,0, 0);
					}
				});
	            
	        
	           	             
	        return row;
		}
		
		
    }
    
    public void itemEntry(View v,final int i){
    	View popupView = getLayoutInflater().inflate(R.layout.pop_entry, null);
  
        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (i==1||i==2||i==3){
        	mPopupWindow.showAsDropDown(v);
        }else
        mPopupWindow.showAtLocation(findViewById(R.id.textView5), Gravity.CLIP_HORIZONTAL,0, 0);
        Button add = (Button)popupView.findViewById(R.id.button1);
        final EditText et = (EditText)popupView.findViewById(R.id.editText1);
        
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!et.getText().equals("")){
				switch(i)
				{
				case 1: setFood(Double.parseDouble(et.getText().toString()));
				break;
				case 2: setTraffic(Double.parseDouble(et.getText().toString()));
				break;
				case 3: setShopping(Double.parseDouble(et.getText().toString()));
				break;
				case 4: setSport(Double.parseDouble(et.getText().toString()));
				break;
				case 5: setSocial(Double.parseDouble(et.getText().toString()));
                break;
				case 6: setOther(Double.parseDouble(et.getText().toString()));
				break;
				}}
				mPopupWindow.dismiss();
			}
		});       
    	
    }
    
    
    
    public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= apsAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(apsAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
    } 
    private class CustomOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Toast.makeText(parent.getContext(), 
					parent.getItemAtPosition(position).toString()+" Month",
					Toast.LENGTH_SHORT).show();
			String selected = parent.getItemAtPosition(position).toString();
			if (selected.equals("January")){
				updateBarChart(dp.getYear(),1);
			}
			else if (selected.equals("February")){
				updateBarChart(dp.getYear(),2);
			}
			else if (selected.equals("March")){
				updateBarChart(dp.getYear(),3);
			}
			else if (selected.equals("April")){
				updateBarChart(dp.getYear(),4);
			}
			else if (selected.equals("May")){
				updateBarChart(dp.getYear(),5);
			}
			else if (selected.equals("June")){
				updateBarChart(dp.getYear(),6);
			}
			else if (selected.equals("July")){
				updateBarChart(dp.getYear(),7);
			}
			else if (selected.equals("August")){
				updateBarChart(dp.getYear(),8);
			}
			else if (selected.equals("September")){
				updateBarChart(dp.getYear(),9);
			}
			else if (selected.equals("October")){
				updateBarChart(dp.getYear(),10);
			}
			else if (selected.equals("November")){
				updateBarChart(dp.getYear(),11);
			}
			else if (selected.equals("December")){
				updateBarChart(dp.getYear(),12);
			}
			

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			Toast.makeText(parent.getContext(), 
					"nothing choosed",
					Toast.LENGTH_SHORT).show();

		}
      
	}
    public void updateBarChart(int year, int month){
    	listView=(HorizontalListView)this.findViewById(R.id.listview); 
    	System.out.println("budget listview"+listView);
    	monthlySum = (TextView)this.findViewById(R.id.monthlySum);
    	DBManager dbm = new DBManager(getApplicationContext());
      	String[]args = {String.valueOf(month),String.valueOf(year)};
    	Map map =dbm.queryBySQL("select sum(food) as foodtotal,sum(traffic) as traffictotal,sum(shopping)as shoppingtotal, sum(social)as socialtotal,sum(sport)as sporttotal, sum(other)as othertotal from budget where month=? and year=?", args);
    	System.out.println(map);
    	ArrayList<Double> arr1 = new ArrayList<Double>();
    	ArrayList<String> arr2 = new ArrayList<String>();
    	if (map.get("foodtotal")!=""){
    	arr1.add(Double.parseDouble((String) map.get("foodtotal")));
    	arr1.add(Double.parseDouble((String) map.get("traffictotal")));
    	arr1.add(Double.parseDouble((String) map.get("shoppingtotal")));
    	arr1.add(Double.parseDouble((String) map.get("socialtotal")));
    	arr1.add(Double.parseDouble((String) map.get("sporttotal")));
    	arr1.add(Double.parseDouble((String) map.get("othertotal")));
    	arr2.add(String.valueOf(month));
    	System.out.println("+++++"+arr1+arr2);
    	itemSum = arr1.toArray(new Double[arr1.size()]);
    	itemLabel = arr2.toArray(new String[arr2.size()]);
    	System.out.println("+++++"+itemSum+itemLabel);
    	Double sum=0.0;
    	 for (int i=0; i<arr1.size();i++){
    		 sum +=arr1.get(i);
    	 }
    	 DecimalFormat  df  = new DecimalFormat("######0.00");
    	 monthlySum.setText("Sum: "+String.valueOf(df.format(sum)));
    	 selectedMonth = month;
    	 selectedYear = year;
    	
    	bsAdapter bs =new bsAdapter(BudgetEntry.this, itemLabel);
    	listView.setAdapter(bs);
    	}
    	else{
    		Toast.makeText(getApplicationContext(), 
					"No entry this month",
					Toast.LENGTH_SHORT).show();
    		
    	}
    }
}
