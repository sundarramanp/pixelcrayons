package com.pixelcrayons.entity;

import java.util.ArrayList;

public class SMPreCallPlanEntity {
	int code;
	String message;
	ArrayList<CallPlan>  result;
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
	public ArrayList<CallPlan>  getResult() {
		return result;
	}
	public void setResult(ArrayList<CallPlan>  result) {
		this.result = result;
	}
	public class CallPlan
	{
		Long call_no;
		String salesman_name;    
		String customer_name;              
		String customer_category; 
		String sales_territory;   
		String followup_date;
		String call_type;
		String call_type_status ;  
		String call_mode;
		String followup_action;
		
		public CallPlan(Long call_no, String salesman_name,
				String customer_name, String customer_category,
				String sales_territory, String followup_date, String call_type,
				String call_type_status, String call_mode,
				String followup_action) {
			super();
			this.call_no = call_no;
			this.salesman_name = salesman_name;
			this.customer_name = customer_name;
			this.customer_category = customer_category;
			this.sales_territory = sales_territory;
			this.followup_date = followup_date;
			this.call_type = call_type;
			this.call_type_status = call_type_status;
			this.call_mode = call_mode;
			this.followup_action = followup_action;
		}
		public Long getCall_no() {
			return call_no;
		}
		public void setCall_no(Long call_no) {
			this.call_no = call_no;
		}
		public String getSalesman_name() {
			return salesman_name;
		}
		public void setSalesman_name(String salesman_name) {
			this.salesman_name = salesman_name;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}
		public String getCustomer_category() {
			return customer_category;
		}
		public void setCustomer_category(String customer_category) {
			this.customer_category = customer_category;
		}
		public String getSales_territory() {
			return sales_territory;
		}
		public void setSales_territory(String sales_territory) {
			this.sales_territory = sales_territory;
		}
		public String getFollowup_date() {
			return followup_date;
		}
		public void setFollowup_date(String followup_date) {
			this.followup_date = followup_date;
		}
		public String getCall_type() {
			return call_type;
		}
		public void setCall_type(String call_type) {
			this.call_type = call_type;
		}
		public String getCall_type_status() {
			return call_type_status;
		}
		public void setCall_type_status(String call_type_status) {
			this.call_type_status = call_type_status;
		}
		public String getCall_mode() {
			return call_mode;
		}
		public void setCall_mode(String call_mode) {
			this.call_mode = call_mode;
		}
		public String getFollowup_action() {
			return followup_action;
		}
		public void setFollowup_action(String followup_action) {
			this.followup_action = followup_action;
		}
	}
}
