package com.pixelcrayons.entity;

import java.util.ArrayList;

public class ViewCustomerEntity 
{
	int code;
	String message;
	ArrayList<ViewCustomer>  result;
	
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
	public ArrayList<ViewCustomer> getResult() {
		return result;
	}
	public void setResult(ArrayList<ViewCustomer> result) {
		this.result = result;
	}
	public class ViewCustomer
	{
		String customer_name;
		String customer_address;
		String customer_contact_detail;
		String customer_contact_number;
		String customer_email;
		ArrayList<BusinessName>  businessresult;
		
		public ViewCustomer(String customer_name, String customer_address,
				String customer_contact_detail, String customer_contact_number,
				String customer_email, ArrayList<BusinessName> businessresult) {
			super();
			this.customer_name = customer_name;
			this.customer_address = customer_address;
			this.customer_contact_detail = customer_contact_detail;
			this.customer_contact_number = customer_contact_number;
			this.customer_email = customer_email;
			this.businessresult = businessresult;
		}
		public ArrayList<BusinessName> getBusinessresult() {
			return businessresult;
		}
		public void setBusinessresult(ArrayList<BusinessName> businessresult) {
			this.businessresult = businessresult;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
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
		public String getCustomer_contact_number() {
			return customer_contact_number;
		}
		public void setCustomer_contact_number(String customer_contact_number) {
			this.customer_contact_number = customer_contact_number;
		}
		public String getCustomer_email() {
			return customer_email;
		}
		public void setCustomer_email(String customer_email) {
			this.customer_email = customer_email;
		}
		
	}
	public class BusinessName
	{
		String bussniss_name;
		String segment_name;
		String period;
		String origin;
		String destination;
		String ship_pm;
		String tos;
		String vol_pm;                              
		String unit_code;                              
		String potential;                              
		String closure_date;                              
		String status;                              
		String reason;
		
		public BusinessName(String bussniss_name, String segment_name,
				String period, String origin, String destination,
				String ship_pm, String tos, String vol_pm, String unit_code,
				String potential, String closure_date, String status,
				String reason) {
			super();
			this.bussniss_name = bussniss_name;
			this.segment_name = segment_name;
			this.period = period;
			this.origin = origin;
			this.destination = destination;
			this.ship_pm = ship_pm;
			this.tos = tos;
			this.vol_pm = vol_pm;
			this.unit_code = unit_code;
			this.potential = potential;
			this.closure_date = closure_date;
			this.status = status;
			this.reason = reason;
		}
		public String getBussniss_name() {
			return bussniss_name;
		}
		public void setBussniss_name(String bussniss_name) {
			this.bussniss_name = bussniss_name;
		}
		public String getSegment_name() {
			return segment_name;
		}
		public void setSegment_name(String segment_name) {
			this.segment_name = segment_name;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getShip_pm() {
			return ship_pm;
		}
		public void setShip_pm(String ship_pm) {
			this.ship_pm = ship_pm;
		}
		public String getTos() {
			return tos;
		}
		public void setTos(String tos) {
			this.tos = tos;
		}
		public String getVol_pm() {
			return vol_pm;
		}
		public void setVol_pm(String vol_pm) {
			this.vol_pm = vol_pm;
		}
		public String getUnit_code() {
			return unit_code;
		}
		public void setUnit_code(String unit_code) {
			this.unit_code = unit_code;
		}
		public String getPotential() {
			return potential;
		}
		public void setPotential(String potential) {
			this.potential = potential;
		}
		public String getClosure_date() {
			return closure_date;
		}
		public void setClosure_date(String closure_date) {
			this.closure_date = closure_date;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}   
	}

}
