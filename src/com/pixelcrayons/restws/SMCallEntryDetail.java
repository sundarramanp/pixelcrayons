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
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pixelcrayons.entity.SEPreCallPlanEntity;
import com.pixelcrayons.entity.SMCallEntryDetailEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("sm_call_entry_detail")
public class SMCallEntryDetail {
    @Context
    private UriInfo context;

    /** Creates a new instance of SMCallEntryDetail */
    public SMCallEntryDetail() {
    }

    /**
     * Retrieves representation of an instance of com.restws.SMCallEntryDetail
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText()
    {
       return "SM-Pre Call Plan Web Service";
    }

    /**
     * PUT method for updating or creating an instance of SMCallEntryDetail
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putText(@FormParam("salesman_name") String salesmanName,
                         @FormParam("empcode") String empCode,
                         @FormParam("customer_name") String customerName,
                         @FormParam("customer_id") String customerID,
                         @FormParam("token") String token)
    {
        
    	SMCallEntryDetailEntity smced = new SMCallEntryDetailEntity();
    	smced.setCode(Constant.HTTP_USER_EXCEPTION);
    	smced.setMessage("");
    	String checkStatus ="";
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
        										"salesman_name : "+salesmanName+" ## "+
        										"customer_name : "+customerName+" ## "+
        										"customer_id : "+customerID+" ## "+
        										"token :"+ token);

    	Date date = null;
    	if (empCode == null || "".equals(empCode))        
    		smced.setMessage("Employee Code is Mandatory");   
    	else if (customerID == null || "".equals(customerID))
    		smced.setMessage("customer ID is Mandatory");  
    	if (smced.getMessage().length() ==0)
    	{
    		com.pixelcrayons.utility.DBOperation db =null;
	        try
	        {
	            db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)
	            	smced.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		smced.setMessage(authentication);
		        	else
		        		smced=  db.getCallEntryDetail(empCode,customerID);
	            }
	            
	            	
	        }
	        catch(Exception e)
	        {
	        	smced.setMessage( e.getMessage());
	        }
	        finally
	        {
	        	db.closeConnection();
	        }
    	}
    	ObjectMapper mapper = new ObjectMapper();         
        try {
			checkStatus = mapper.writeValueAsString(smced);
			
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
