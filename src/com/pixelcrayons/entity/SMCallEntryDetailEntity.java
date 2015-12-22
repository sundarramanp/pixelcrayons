package com.pixelcrayons.entity;

import java.util.ArrayList;


public class SMCallEntryDetailEntity 
{
	int code;
	String message;
	ArrayList<CallEntryDetail>  result;
	
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

	public ArrayList<CallEntryDetail> getResult() {
		return result;
	}

	public void setResult(ArrayList<CallEntryDetail> result) {
		this.result = result;
	}

	public class CallEntryDetail
	{
		String salesman_name;
		Long call_number;  
		String customer_name;      
		String call_type;    
		String call_mode  ;
		String call_objective;  
		String callEntry_create_date  ; 
		String followup_date;  
		String followup_action;   
		String callEntry_close;  
		String manager_comment ;
		String follow_ups;
		
		public CallEntryDetail(String salesman_name, Long call_number,
				String customer_name, String call_type, String call_mode,
				String call_objective, String callEntry_create_date,
				String followup_date, String followup_action,
				String callEntry_close, String manager_comment,
				String follow_ups) {
			super();
			this.salesman_name = salesman_name;
			this.call_number = call_number;
			this.customer_name = customer_name;
			this.call_type = call_type;
			this.call_mode = call_mode;
			this.call_objective = call_objective;
			this.callEntry_create_date = callEntry_create_date;
			this.followup_date = followup_date;
			this.followup_action = followup_action;
			this.callEntry_close = callEntry_close;
			this.manager_comment = manager_comment;
			this.follow_ups = follow_ups;
		}
		public String getSalesman_name() {
			return salesman_name;
		}
		public void setSalesman_name(String salesman_name) {
			this.salesman_name = salesman_name;
		}
		public Long getCall_number() {
			return call_number;
		}
		public void setCall_number(Long call_number) {
			this.call_number = call_number;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}
		public String getCall_type() {
			return call_type;
		}
		public void setCall_type(String call_type) {
			this.call_type = call_type;
		}
		public String getCall_mode() {
			return call_mode;
		}
		public void setCall_mode(String call_mode) {
			this.call_mode = call_mode;
		}
		public String getCall_objective() {
			return call_objective;
		}
		public void setCall_objective(String call_objective) {
			this.call_objective = call_objective;
		}
		public String getCallEntry_create_date() {
			return callEntry_create_date;
		}
		public void setCallEntry_create_date(String callEntry_create_date) {
			this.callEntry_create_date = callEntry_create_date;
		}
		public String getFollowup_date() {
			return followup_date;
		}
		public void setFollowup_date(String followup_date) {
			this.followup_date = followup_date;
		}
		public String getFollowup_action() {
			return followup_action;
		}
		public void setFollowup_action(String followup_action) {
			this.followup_action = followup_action;
		}
		public String getCallEntry_close() {
			return callEntry_close;
		}
		public void setCallEntry_close(String callEntry_close) {
			this.callEntry_close = callEntry_close;
		}
		public String getManager_comment() {
			return manager_comment;
		}
		public void setManager_comment(String manager_comment) {
			this.manager_comment = manager_comment;
		}
		public String getFollow_ups() {
			return follow_ups;
		}
		public void setFollow_ups(String follow_ups) {
			this.follow_ups = follow_ups;
		}
	}
}
