package com.pixelcrayons.entity;

import java.util.ArrayList;


public class DropDownEntity 
{
	int code;
	String message;
	ArrayList<AllMaster>  callMode;
	ArrayList<AllMaster>  salesTerritory;
	ArrayList<AllMaster>  callType;
	ArrayList<AllMaster>  period;
	ArrayList<AllMaster>  tos;
	ArrayList<AllMaster>  segment;
	ArrayList<AllMaster>  competitor;
	ArrayList<AllMaster>  followup_action;
	ArrayList<AllMaster>  salesMan;
	
	public ArrayList<AllMaster> getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(ArrayList<AllMaster> salesMan) {
		this.salesMan = salesMan;
	}

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

	public ArrayList<AllMaster> getCallMode() {
		return callMode;
	}

	public void setCallMode(ArrayList<AllMaster> callMode) {
		this.callMode = callMode;
	}

	public ArrayList<AllMaster> getSalesTerritory() {
		return salesTerritory;
	}

	public void setSalesTerritory(ArrayList<AllMaster> salesTerritory) {
		this.salesTerritory = salesTerritory;
	}

	public ArrayList<AllMaster> getCallType() {
		return callType;
	}

	public void setCallType(ArrayList<AllMaster> callType) {
		this.callType = callType;
	}

	public ArrayList<AllMaster> getPeriod() {
		return period;
	}

	public void setPeriod(ArrayList<AllMaster> period) {
		this.period = period;
	}

	public ArrayList<AllMaster> getTos() {
		return tos;
	}

	public void setTos(ArrayList<AllMaster> tos) {
		this.tos = tos;
	}

	public ArrayList<AllMaster> getSegment() {
		return segment;
	}

	public void setSegment(ArrayList<AllMaster> segment) {
		this.segment = segment;
	}

	public ArrayList<AllMaster> getCompetitor() {
		return competitor;
	}

	public void setCompetitor(ArrayList<AllMaster> competitor) {
		this.competitor = competitor;
	}

	public ArrayList<AllMaster> getFollowup_action() {
		return followup_action;
	}

	public void setFollowup_action(ArrayList<AllMaster> followup_action) {
		this.followup_action = followup_action;
	}

	public class AllMaster
	{
		String code;
		String name;
		
		public AllMaster(String code, String name) {
			super();
			this.code = code;
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		
	}
}
