package com.example.dslienhe;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;

public class MainActivity extends Activity {
	 final String DATABASE_NAME = "DSLienHe.sqlite";
	    SQLiteDatabase database;
	    
	    ListView listView;
	    ArrayList<DanhSach> list;
	    AdapterDanhSach adapter;
	    Button btnthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        readData();
    }
    
    private void addControls(){
    	btnthem = (Button) findViewById(R.id.btnthem);
    	btnthem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);
			}
		});
    	listView = (ListView) findViewById(R.id.listView);
    	list = new ArrayList<DanhSach>();
    	adapter = new AdapterDanhSach(this, list);
    	listView.setAdapter(adapter);
    }
    private void readData(){
    	 database = Database.initDatabase(this, DATABASE_NAME);
         Cursor cursor = database.rawQuery("SELECT * FROM DanhSach",null);
         list.clear();
         for(int i = 0; i < cursor.getCount(); i++ ){
        	 cursor.moveToPosition(i);
        	 int id = cursor.getInt(0);
        	 String ten = cursor.getString(1);
        	 String sdt = cursor.getString(2);
        	 String mqh = cursor.getString(3);
        	 byte[] anh = cursor.getBlob(4);
        	 list.add(new DanhSach(id, ten, sdt, mqh, anh));
         }
    	adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
