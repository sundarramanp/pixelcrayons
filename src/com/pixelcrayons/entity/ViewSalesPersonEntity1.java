package com.pixelcrayons.entity;

public class ViewSalesPersonEntity1 
{
	String call_no;
	String customer_name;
	String customer_category;
	String sales_territory;
	String followup_date;
	String call_type;
	String followup_action;
	String salesman_name;
	public ViewSalesPersonEntity1(String call_no, String customer_name,
			String customer_category, String sales_territory,
			String followup_date, String call_type, String followup_action,
			String salesman_name) {
		super();
		this.call_no = call_no;
		this.customer_name = customer_name;
		this.customer_category = customer_category;
		this.sales_territory = sales_territory;
		this.followup_date = followup_date;
		this.call_type = call_type;
		this.followup_action = followup_action;
		this.salesman_name = salesman_name;
	}
	public String getCall_no() {
		return call_no;
	}
	public void setCall_no(String call_no) {
		this.call_no = call_no;
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
	public String getFollowup_action() {
		return followup_action;
	}
	public void setFollowup_action(String followup_action) {
		this.followup_action = followup_action;
	}
	public String getSalesman_name() {
		return salesman_name;
	}
	public void setSalesman_name(String salesman_name) {
		this.salesman_name = salesman_name;
	}
}
