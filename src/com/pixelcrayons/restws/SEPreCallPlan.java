/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.util.Calendar;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pixelcrayons.entity.SEPreCallPlanEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;
/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("se_pre_call_plan")
public class SEPreCallPlan {
    @Context
    private UriInfo context;

    /** Creates a new instance of PreCallPlan */
    public SEPreCallPlan() {
    }

    /**
     * Retrieves representation of an instance of com.restws.PreCallPlan
     * @return an instance of java.lang.String
     */
    @GET 
    public String getText() {
        //TODO return proper representation object
          return "Pre Call Plan Web Service";
    }

    /**
     * PUT method for updating or creating an instance of PreCallPlan
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putXml(@FormParam("empcode") String empCode,
    					 @FormParam("from_date") String fromDate,
    					 @FormParam("to_date") String toDate,
    					 @FormParam("startLimit") String startLimit,
    					 @FormParam("token") String token)
    {
    	SEPreCallPlanEntity cpe = new SEPreCallPlanEntity();
    	cpe.setCode(Constant.HTTP_USER_EXCEPTION);
    	cpe.setMessage("");
    	String checkStatus ="";
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
        										"from_date : "+fromDate+" ## "+
        										"to_date : "+toDate+" ## "+
        										"startLimit :"+startLimit+" ## "+"token :"+token);

    	Date date = null;
    	if (empCode == null || "".equals(empCode))        
    		cpe.setMessage("Employee Code is Mandatory");        
    	else
    	{
    		if (fromDate != null && !"".equals(fromDate))
    		{    			
		            try
		            {
		                date = new SimpleDateFormat("mm/dd/yyyy").parse(fromDate);
		            } catch (Exception ex)
		            {
		            	cpe.setMessage("Invalid from date ("+fromDate+"). Format should be MM/DD/YYYY.");
		            }
		            if ( toDate ==null || "".equals(toDate))
		            	cpe.setMessage("Enter To Date. Format should be MM/DD/YYYY.");		            
    		}    			    	
    		if (toDate != null  && !"".equals(toDate))
    		{    			
		            try
		            {
		              date = new SimpleDateFormat("mm/dd/yyyy").parse(toDate);
		            } catch (Exception ex)
		            {		              
		            	cpe.setMessage("Invalid To date ("+toDate+"). Format should be MM/DD/YYYY.");
		            }
		            if ( fromDate ==null || "".equals(fromDate))
		            	cpe.setMessage("Enter From Date. Format should be MM/DD/YYYY.");			          		            		               			
    		}
    	}
    	int startLimitValue = 1;
    	if (startLimit != null)
    	{
    		   if (startLimit.length()==0)
    			   startLimitValue = 1;
    		   else
    		   {
    			   try
    			   {
    				   startLimitValue = Integer.parseInt(startLimit);
    			   }
    			   catch (Exception e)
    			   {
    				   cpe.setMessage("Invalid StartLimit Value ("+startLimit+")");
    			   }
    		   }
    	}
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
		        		cpe=  db.se_pre_call_plan(empCode,fromDate,toDate,startLimitValue);
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
