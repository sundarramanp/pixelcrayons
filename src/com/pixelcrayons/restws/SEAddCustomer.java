/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pixelcrayons.entity.BusinessListEntiry;
import com.pixelcrayons.entity.ResponseEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("add_customer")
public class SEAddCustomer {
    @Context
    private UriInfo context;

    /** Creates a new instance of SEAddCustomer */
    public SEAddCustomer() {
    }

    /**
     * Retrieves representation of an instance of com.restws.SEAddCustomer
     * @return an instance of java.lang.String
     */
   
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        return "SE - Add Customer Web Service";
    }

    /**
     * PUT method for updating or creating an instance of SEAddCustomer
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    
    public String putXml(@FormParam("empcode") String empCode,
			            @FormParam("salesman_name") String salesmanName,
			            @FormParam("customer_name") String customerName,
			            @FormParam("customer_address") String customerAddress,
			            @FormParam("customer_contact_detail") String customerContactDetail,
			            @FormParam("customer_contact_number") String customerContactNumber,
			            @FormParam("customer_email") String customerEmail,
			            @FormParam("city") String city,
			            @FormParam("country_code") String countryCode,
			            @FormParam("customer_type") String customerType,
			            @FormParam("token") String token,
			            @FormParam("bussiness_list") String businessList) 
    { 
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
        										"salesman_name : "+salesmanName+" ## "+
        										"customer_name : "+customerName+" ## "+
        										"customer_address : "+customerAddress+" ## "+
        										"customer_contact_detail : "+customerContactDetail+" ## "+
        										"customer_contact_number : "+customerContactNumber+" ## "+
        										"customer_email : "+customerEmail+" ## "+
        										"city : "+city+" ## "+
        										"country_code : "+countryCode+" ## "+
        										"customer_type : "+customerType+" ## "+
        										"token : "+token+" ## "+
        										"bussiness_list"+businessList
        										);
        ResponseEntity fae = new ResponseEntity();
    	fae.setCode(Constant.HTTP_USER_EXCEPTION);
    	fae.setMessage("");
    	
    	String checkStatus ="";
    	if (empCode == null || "".equals(empCode))
    		fae.setMessage("Employee Code is Mandatory");   
    	else if (customerName == null || "".equals(customerName))
     		fae.setMessage("Customer Name is Mandatory");   
         else if (customerAddress == null || "".equals(customerAddress))
         	fae.setMessage("Customer Address is Mandatory");   
         else if (countryCode == null || "".equals(countryCode))
          	fae.setMessage("Country Code is Mandatory");
         else if (customerType == null || "".equals(customerType))
           	fae.setMessage("Customer Type is Mandatory");  
         else if (! Arrays.toString(Constant.customerType).contains(customerType))
         {     
        	 fae.setMessage(customerType+"Customer Type Should be "+ Arrays.toString(Constant.customerType));  	 
         }
    	 ArrayList<BusinessListEntiry> ble = new ArrayList<BusinessListEntiry>();
    	 if ((businessList != null) && !("".equals(businessList)))
    	 {
	    	 try {
	    		 System.out.println(1);
	 			JSONObject jsonBusinessDate = new JSONObject(businessList);
	 			String businessListData = jsonBusinessDate.getString("bussiness_list");	 			
	 			JSONArray jsonArray = new JSONArray(businessListData);
	 			System.out.println(2);
	 			for (int i = 0; i < jsonArray.length(); i++) 
	 			{
	 				System.out.println(3);
	 				jsonBusinessDate = jsonArray.getJSONObject(i);
	 				ble.add(new BusinessListEntiry(jsonBusinessDate.getString("bussniss_name"),
	 						jsonBusinessDate.getString("segment_name"),
	 						jsonBusinessDate.getString("period"),
	 						jsonBusinessDate.getString("origin"),
	 						jsonBusinessDate.getString("destination"),
	 						jsonBusinessDate.getString("ship_pm"),
	 						jsonBusinessDate.getString("tos"),
	 						jsonBusinessDate.getString("vol_pm"),
	 						jsonBusinessDate.getString("unit_code"),
	 						jsonBusinessDate.getString("potential"),
	 						jsonBusinessDate.getString("closure_date"),
	 						jsonBusinessDate.getString("status"),
	 						jsonBusinessDate.getString("reason")));	 				                                
                }
	 		} 
	     	catch (JSONException e1) {
	     		fae.setMessage("Error while parsing Business Detail - "+e1.getMessage());	     		
	 		}
    	 }
    	 if(fae.getMessage().length() == 0)
    	 {
    		com.pixelcrayons.utility.DBOperation db = null;
 	        try
 	        {
 	             db = new com.pixelcrayons.utility.DBOperation();
 	            boolean a = db.connectToDB();
 	            if (a == false)	                
 	            	fae.setMessage("Error in DB connection");
 	            else
 	            {
 	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		fae.setMessage(authentication);
		        	else
		        		fae=  db.addCustomer(  empCode,
								salesmanName,
								customerName,
								customerAddress,
								customerContactDetail,
								customerContactNumber,
								customerEmail,
								city,
								countryCode,
								customerType,
								ble);
 	            }
					
 	        }
 	        catch(Exception e)
 	        {
 	        	fae.setMessage(e.getMessage());
 	        }
 	        finally
 	        {
 	        	if (db != null)
 	        		db.closeConnection();
 	        }
    	 }
    	ObjectMapper mapper = new ObjectMapper();         
        try {
			checkStatus = mapper.writeValueAsString(fae);
			WriteLog.setMessage("   ----- Response Message -> "+checkStatus);
	        WriteLog.setMessage("********* Message End Time   : "+java.util.Calendar.getInstance().getTime().toString()+" *********");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return checkStatus;
    }
}
