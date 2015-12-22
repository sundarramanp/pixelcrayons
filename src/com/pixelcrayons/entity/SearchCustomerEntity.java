package com.pixelcrayons.entity;

import java.util.ArrayList;

public class SearchCustomerEntity {
	int code;
	String message;
	ArrayList<SearchCustomer>  result;
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
	public ArrayList<SearchCustomer> getResult() {
		return result;
	}
	public void setResult(ArrayList<SearchCustomer> result) {
		this.result = result;
	}
	public class SearchCustomer 
	{
		String salesman_name;
		String customer_id;
		String customer_name;
		
		public SearchCustomer(String salesman_name, String customer_id,
				String customer_name, String customer_contact_number,
				String customer_address, String customer_contact_detail,
				String customer_email) 
		{
			super();
			this.salesman_name = salesman_name;
			this.customer_id = customer_id;
			this.customer_name = customer_name;
			this.customer_contact_number = customer_contact_number;
			this.customer_address = customer_address;
			this.customer_contact_detail = customer_contact_detail;
			this.customer_email = customer_email;
		}
		String customer_contact_number;
		String customer_address;
	    String customer_contact_detail;
	    String customer_email;
		
		public String getSalesman_name() {
			return salesman_name;
		}
		public void setSalesman_name(String salesman_name) {
			this.salesman_name = salesman_name;
		}
		public String getCustomer_id() {
			return customer_id;
		}
		public void setCustomer_id(String customer_id) {
			this.customer_id = customer_id;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}
		public String getCustomer_contact_number() {
			return customer_contact_number;
		}
		public void setCustomer_contact_number(String customer_contact_number) {
			this.customer_contact_number = customer_contact_number;
		}
		public String getCustomer_address() {
			return customer_address;
		}
		public void setCustomer_address(String customer_address) {
			this.customer_address = customer_address;
		}
		public String getCustomer_contact_detail() {
			return customer_contact_detail;
		}
		public void setCustomer_contact_detail(String customer_contact_detail) {
			this.customer_contact_detail = customer_contact_detail;
		}
		public String getCustomer_email() {
			return customer_email;
		}
		public void setCustomer_email(String customer_email) {
			this.customer_email = customer_email;
		}   
	}
}
