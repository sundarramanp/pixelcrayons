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

import com.pixelcrayons.entity.SEPreCallPlanEntity;
import com.pixelcrayons.entity.SMPreCallPlanEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("sm_pre_call_plan")
public class SMPreCallPlan {
    @Context
    private UriInfo context;

    /** Creates a new instance of SMPreCallPlan */
    public SMPreCallPlan() {
    }

    /**
     * Retrieves representation of an instance of com.restws.SMPreCallPlan
     * @return an instance of java.lang.String
     */
    @GET
    
    public String getText() {
        //TODO return proper representation object
       return "SM-Pre Call Plan Web Service";
    }

    /**
     * PUT method for updating or creating an instance of SMPreCallPlan
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putXml(@FormParam("salesman_name") String salesmanName,
    					 @FormParam("token") String token)
    {
    	SMPreCallPlanEntity cpe = new SMPreCallPlanEntity();
    	cpe.setCode(Constant.HTTP_USER_EXCEPTION);
    	cpe.setMessage("");
    	String checkStatus ="";
    	WriteLog.setMessage("********* Message Start Time : "+ java.util.Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  salesman_name :"+salesmanName);
        if (salesmanName == null || "".equals(salesmanName))        
    		cpe.setMessage("Salesman Name is Mandatory");  
        if (cpe.getMessage().length() == 0)
    	{		
    		com.pixelcrayons.utility.DBOperation db =null;
	        try
	        {
	            db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)
	            	cpe.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		cpe.setMessage(authentication);
		        	else
		        		cpe=  db.sm_pre_call_plan(salesmanName);
	            }
	        }
	        catch(Exception e)
	        {
	        	cpe.setMessage( e.getMessage());
	        }
	        finally
	        {
	        	db.closeConnection();
	        }
    	}
    	 ObjectMapper mapper = new ObjectMapper();         
         try {
 			checkStatus = mapper.writeValueAsString(cpe);
 			
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
