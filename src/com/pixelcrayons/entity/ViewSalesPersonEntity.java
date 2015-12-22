package com.pixelcrayons.entity;

import java.util.ArrayList;

public class ViewSalesPersonEntity 
{
	int code;
	String message;
	ArrayList<ViewSalesPersonEntity1>  result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<ViewSalesPersonEntity1> getResult() {
		return result;
	}
	public void setResult(ArrayList<ViewSalesPersonEntity1> result) {
		this.result = result;
	}
}
