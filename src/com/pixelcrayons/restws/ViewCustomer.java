/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
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

import com.pixelcrayons.entity.ViewCustomerEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("view_customer")
public class ViewCustomer {
    @Context
    private UriInfo context;

    /** Creates a new instance of ViewCustomer */
    public ViewCustomer() {
    }

    /**
     * Retrieves representation of an instance of com.restws.ViewCustomer
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        return "View Customer Web Service";
    }

    /**
     * PUT method for updating or creating an instance of ViewCustomer
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putText(@FormParam("customer_id") String customerID,
                          @FormParam("customer_contact_number") String customerContactNo,
                          @FormParam("customer_name") String customerName,
                          @FormParam("token") String token )
    {
    	ViewCustomerEntity vce = new ViewCustomerEntity();
    	vce.setCode(Constant.HTTP_USER_EXCEPTION);
    	vce.setMessage("");
    	String checkStatus ="";
    	Date date = null;
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  customer_id :"+customerID+" ## "+
        										"customer_contact_number : "+customerContactNo+" ## "+
        										"customer_name :"+customerName);

    	if (customerID == null || "".equals(customerID))        
    		vce.setMessage("Customer ID is Mandatory");  
    	else
    	{
	    	com.pixelcrayons.utility.DBOperation db = null;
	        try
	        {
	            db = new com.pixelcrayons.utility.DBOperation();
	            
	            boolean a = db.connectToDB();
	            if (a == false)	            
	            	vce.setMessage("Error in DB connection");	            
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	if(!authentication.equals("AUTHENTICATED"))
	            		vce.setMessage(authentication);
	            	else
	            		vce=  db.viewCustomer(customerID,customerContactNo);
	            }
	        }
	        catch(Exception e)
	        {
	        	vce.setMessage( e.getMessage());
	        }
	        finally
	        {
	        	db.closeConnection();
	        }
    	}
        ObjectMapper mapper = new ObjectMapper();              
        try 
        {
			checkStatus = mapper.writeValueAsString(vce);
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
