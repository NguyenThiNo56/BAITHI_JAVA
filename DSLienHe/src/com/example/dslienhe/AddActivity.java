package com.example.dslienhe;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends Activity {
	final String DATABASE_NAME = "DSLienHe.sqlite";
	final int RESQUEST_TAKE_PHOTO = 123;
	final int REQUEST_CHOOSE_PHOTO = 321;
	
	Button btnchuphinh, btnchonhinh, btnluu, btnhuy;
	EditText edtten, edtsdt, edtmqh;
	ImageView imghinhanh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		addControls();
		addEvents();
	}
	private void addControls(){
		btnchuphinh = (Button) findViewById(R.id.btnchuphinh);
		btnchonhinh = (Button) findViewById(R.id.btnchonhinh);
		btnluu = (Button) findViewById(R.id.btnluu);
		btnhuy = (Button) findViewById(R.id.btnhuy);
		edtten = (EditText) findViewById(R.id.edtten);
		edtsdt = (EditText) findViewById(R.id.edtsdt);
		edtmqh = (EditText) findViewById(R.id.edtmqh);
		imghinhanh = (ImageView) findViewById(R.id.imghinhanh);
	}
	private void addEvents(){
		btnchuphinh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takePicture();
			}
		});
		btnchonhinh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				choosePhoto();
			}
		});
		btnluu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				insert();
			}
		});
		btnhuy.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
	}
	private void takePicture(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
	}
	private void choosePhoto(){
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_CHOOSE_PHOTO){
				try{
					Uri imageUri = data.getData();
					InputStream is = getContentResolver().openInputStream(imageUri);
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					imghinhanh.setImageBitmap(bitmap);
				}catch (FileNotFoundException e){
					e.printStackTrace();
				}
			}else if(requestCode == RESQUEST_TAKE_PHOTO){
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				imghinhanh.setImageBitmap(bitmap);
			}
		}
	}
	private void insert(){
		String ten = edtten.getText().toString();
		String sdt = edtsdt.getText().toString();
		String mqh = edtmqh.getText().toString();
		byte[] anh = getByteArrayFromImageView(imghinhanh);
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("Ten", ten);
		contentValues.put("SDT", sdt);
		contentValues.put("Mqh", mqh);
		contentValues.put("Anh", anh);
		
		SQLiteDatabase database = Database.initDatabase(this,"DSLienHe.sqlite");
		database.insert("DanhSach",null, contentValues);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	private void cancel(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
