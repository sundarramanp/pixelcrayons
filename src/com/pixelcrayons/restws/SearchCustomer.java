/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pixelcrayons.entity.SearchCustomerEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("search_customer")
public class SearchCustomer {
    @Context
    private UriInfo context;

    /** Creates a new instance of SearchCustomer */
    public SearchCustomer() {
    }

    /**
     * Retrieves representation of an instance of com.restws.SearchCustomer
     * @return an instance of java.lang.String
     */
    @GET
    public String getText() {
        //TODO return proper representation object
       return "Search Customer Web Service";
    }

    /**
     * PUT method for updating or creating an instance of SearchCustomer
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putXml(@FormParam("empcode") String empCode,
                        @FormParam("customer_id") String customerID,
                        @FormParam("customer_name") String customerName,
                        @FormParam("from_date") String fromDate,
                        @FormParam("to_date") String toDate,
                        @FormParam("customer_of") String customer_of,
                        @FormParam("token") String token) 
    {
    	SearchCustomerEntity sce = new SearchCustomerEntity();
    	sce.setCode(Constant.HTTP_USER_EXCEPTION);
    	sce.setMessage("");
    	String checkStatus ="";
    	Date date = null;
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
    	WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
				"customer_id : "+customerID+" ## "+
				"customer_name :"+customerName+" ## "+
				"from_date : "+fromDate+" ## "+
				"to_date : "+toDate+" ## "+
				"customer_of : "+customer_of+" ## "+
				"token : "+token);

    	if (customerID == null || "".equals(customerID))        
    		sce.setMessage("Customer ID is Mandatory");        
    	else
    	{    		
    		if (fromDate != null && !"".equals(fromDate))
    		{    			
		            try
		            {
		                date = new SimpleDateFormat("mm/dd/yyyy").parse(fromDate);
		            } catch (Exception ex)
		            {
		            	sce.setMessage("Invalid from date ("+fromDate+"). Format should be MM/DD/YYYY.");
		            }
		            if ( toDate ==null || "".equals(toDate))
		            	sce.setMessage("Enter To Date. Format should be MM/DD/YYYY.");		            
    		}    			    	
    		if (toDate != null  && !"".equals(toDate))
    		{    			
		            try
		            {
		              date = new SimpleDateFormat("mm/dd/yyyy").parse(toDate);
		            } catch (Exception ex)
		            {		              
		            	sce.setMessage("Invalid To date ("+toDate+"). Format should be MM/DD/YYYY.");
		            }
		            if ( fromDate ==null || "".equals(fromDate))
		            	sce.setMessage("Enter From Date. Format should be MM/DD/YYYY.");			          		            		               			
    		}
    	}
    	if (sce.getMessage().length() == 0)
    	{
	    	com.pixelcrayons.utility.DBOperation db = null;
	        try
	        {
	            db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)
	            	sce.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		sce.setMessage(authentication);
		        	else
		        		sce=  db.getSearchCustomer(empCode,customerID,
                                fromDate,toDate,customer_of);
	            }
	            	
	
	        }
	        catch(Exception e)
	        {
	            checkStatus = e.getMessage();
	        }
	        finally
	        {
	        	db.closeConnection();
	        }
    	}
        ObjectMapper mapper = new ObjectMapper();         
        try {
			checkStatus = mapper.writeValueAsString(sce);
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
        WriteLog.setMessage("   ----- Response Message -> "+checkStatus);
        WriteLog.setMessage("********* Message End Time   : "+java.util.Calendar.getInstance().getTime().toString()+" *********");
        return checkStatus;
    }
}
