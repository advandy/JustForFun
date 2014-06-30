package database;

import java.io.File;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "budgetDB";
	private static final int VERSION = 1;
	public DBHelper(Context context) {
		//super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
		super(context, Environment.getExternalStorageDirectory()+File.separator+DB_NAME,null, VERSION);
		SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+File.separator+DB_NAME,null);
	}

	
	//initiate database with two columns 

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql ="create table budget (entryDate varchar primary key,food float,traffic float,shopping float,sport float,social float,other float, entryAmount float)";
		db.execSQL(sql);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "alter table budget add year int";
        db.execSQL(sql);
        String sql1 = "alter table budget add month int";
        db.execSQL(sql1);
        String sql2 = "alter table budget add day int";
        db.execSQL(sql2);
        
        
       

	}

}
