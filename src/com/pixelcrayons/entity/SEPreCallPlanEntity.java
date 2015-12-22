package com.pixelcrayons.entity;

import java.util.ArrayList;

public class SEPreCallPlanEntity 
{
	int code;
	String message;
	ArrayList<PreCallPlanEntity>  result;
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
	public ArrayList<PreCallPlanEntity>  getResult() {
		return result;
	}
	public void setResult(ArrayList<PreCallPlanEntity>  result) {
		this.result = result;
	}
	public class PreCallPlanEntity 
	{
		Integer recordId;
		Long call_no;
		String customer_id;
		String customer_name;
		String customer_category ="";
		String sales_territory ="";
		String followup_date;
		String call_type;
		String call_type_status;
		String call_mode;
		String followup_action;
		
		
		public PreCallPlanEntity(Integer recordId, Long call_no,
				String customer_id, String customer_name, String customer_category,
				String sales_territory, String followup_date, String call_type,
				String call_type_status, String call_mode, String followup_action) {
			super();
			this.recordId = recordId;
			this.call_no = call_no;
			this.customer_id = customer_id;
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
		public Integer getRecordId() {
			return recordId;
		}
		public void setRecordId(Integer recordId) {
			this.recordId = recordId;
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
