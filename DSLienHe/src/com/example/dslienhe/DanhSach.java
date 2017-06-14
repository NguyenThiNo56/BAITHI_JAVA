package com.example.dslienhe;

public class DanhSach {
	public int id;
	public String ten;
	public String sdt;
	public String mqh;
	public byte[] anh;
	
	public DanhSach (int id, String ten, String sdt, String mqh, byte[] anh){
		this.id = id;
		this.ten = ten;
		this.sdt = sdt;
		this.mqh = mqh;
		this.anh = anh;
	}
}
