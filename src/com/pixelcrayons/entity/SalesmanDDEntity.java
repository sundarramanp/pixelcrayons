package com.pixelcrayons.entity;

import java.util.ArrayList;

import com.pixelcrayons.entity.SEPreCallPlanEntity.PreCallPlanEntity;

public class SalesmanDDEntity 
{
	int code;
	String message;
	ArrayList<SalesmanDD>  salesman_name;
	public class SalesmanDD 
	{
		String salesman_name;
		String empcode;
		public SalesmanDD(String salesman_name, String empcode) {
			super();
			this.salesman_name = salesman_name;
			this.empcode = empcode;
		}
		public String getSalesman_name() {
			return salesman_name;
		}
		public void setSalesman_name(String salesman_name) {
			this.salesman_name = salesman_name;
		}
		public String getEmpcode() {
			return empcode;
		}
		public void setEmpcode(String empcode) {
			this.empcode = empcode;
		}
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
	public ArrayList<SalesmanDD> getSalesmanName() {
		return salesman_name;
	}
	public void setSalesmanName(ArrayList<SalesmanDD> salesman_name) {
		this.salesman_name = salesman_name;
	}
	

}
