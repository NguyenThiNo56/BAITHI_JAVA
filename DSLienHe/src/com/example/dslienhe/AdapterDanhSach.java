package com.example.dslienhe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class AdapterDanhSach extends BaseAdapter{
	Activity context;
	ArrayList<DanhSach> list;
	
	public AdapterDanhSach(Activity context, ArrayList<DanhSach> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.listview_row, null);
		ImageView imghinhanh = (ImageView) row.findViewById(R.id.imghinhanh);
		TextView txtid = (TextView) row.findViewById(R.id.txtid);
		TextView txtten = (TextView) row.findViewById(R.id.txtten);
		TextView txtsdt = (TextView) row.findViewById(R.id.txtsdt);
		TextView txtmqh = (TextView) row.findViewById(R.id.txtmqh);
		Button btnsua = (Button) row.findViewById(R.id.btnsua);
		Button btnxoa = (Button) row.findViewById(R.id.btnxoa);
		
		final DanhSach danhsach = list.get(arg0);
		txtid.setText(danhsach.id + "");
		txtten.setText(danhsach.ten);
		txtsdt.setText(danhsach.sdt);
		txtmqh.setText(danhsach.mqh);
		
		Bitmap bmhinhanh = BitmapFactory.decodeByteArray(danhsach.anh, 0, danhsach.anh.length);
		imghinhanh.setImageBitmap(bmhinhanh);
		
		btnsua.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, UpdateActivity.class) ;
				intent.putExtra("ID", danhsach.id);
				context.startActivity(intent);
			}
		});
		btnxoa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setIcon(android.R.drawable.ic_delete);
				builder.setTitle("Xác nhận xóa");
				builder.setMessage("Bạn có chắc chắn muốn xóa liên hệ này?");
				builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						delete(danhsach.id);
					}
				});
				builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		
		return row;
	}
	private void delete(int idDanhSach){
		SQLiteDatabase database = Database.initDatabase(context, "DSLienHe.sqlite");
		database.delete("DanhSach","ID = ?",new String[]{idDanhSach + ""});
		list.clear();
		Cursor cursor = database.rawQuery("SELECT * FROM DanhSach", null);
		while (cursor.moveToNext()){
			int id = cursor.getInt(0);
			String ten = cursor.getString(1);
			String sdt = cursor.getString(2);
			String mqh = cursor.getString(3);
			byte[] anh = cursor.getBlob(4);
			
			list.add(new DanhSach(id, ten, sdt, mqh, anh));
		}
		notifyDataSetChanged();
		}

}
