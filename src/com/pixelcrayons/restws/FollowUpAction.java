/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
import java.util.Calendar;

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

import com.pixelcrayons.entity.ResponseEntity;
import com.pixelcrayons.entity.SEPreCallPlanEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("followup_action")
public class FollowUpAction {
    @Context
    private UriInfo context;

    /** Creates a new instance of FollowUpAction */
    public FollowUpAction() {
    }

    /**
     * Retrieves representation of an instance of com.restws.FollowUpAction
     * @return an instance of java.lang.String
     */
    @GET    
    public String getText() {
        //TODO return proper representation object
       return "FollowUp Action Web Service";
    }

    /**
     * PUT method for updating or creating an instance of FollowUpAction
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putXml(@FormParam("action") String action,
                         @FormParam("call_number") String callNumber,
                         @FormParam("token") String token)
    {
    	ResponseEntity fae = new ResponseEntity();
    	fae.setCode(Constant.HTTP_USER_EXCEPTION);
    	fae.setMessage("");
    	String checkStatus ="";
    	long callNo = 345;
    	WriteLog.setMessage("********* Message Start Time : "+ java.util.Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  action :"+action+" ## "+
        										"call_number : "+callNumber+" ## "+
        										"token : "+token);
        
        
        if (callNumber == null || "".equals(callNumber))
    		fae.setMessage("Call Number is Mandatory");   
        else if (action == null || "".equals(action))
        	fae.setMessage("Followup Action is Mandatory");   
        else if(!action.equals("Y") &&    !action.equals("N"))     	
    		fae.setMessage("Followup Action value should be Y / N ");       	
        else 
        {
        	
	        try
	        {
	        	 callNo = Long.parseLong(callNumber);
	        }
	        catch(Exception e)
	        {
	        	fae.setMessage("Invalid Call Number ("+callNumber+")");   
	        }
        }
        if (fae.getMessage().length() == 0)
    	{		
    		com.pixelcrayons.utility.DBOperation db =null;
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
		        		fae=  db.followUpAction(callNo,action.toUpperCase());
	            }
	        }
	        
	        catch(Exception e)
	        {
	        	fae.setMessage( e.getMessage());
	        }
	        finally
	        {
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
