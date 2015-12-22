/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.Context;
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
import com.pixelcrayons.entity.ViewCustomerEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("new_call_entry")
public class NewCallEntry {
    @Context
    private UriInfo context;

    /** Creates a new instance of NewCallEntry */
    public NewCallEntry() {
    }

    /**
     * Retrieves representation of an instance of com.restws.NewCallEntry
     * @return an instance of java.lang.String
     */
    @GET

    public String getText() {
        //TODO return proper representation object
        return "New Call Entry Web Service";
    }

    /**
     * PUT method for updating or creating an instance of NewCallEntry
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces("text/plain")
   // @Path("{username}/{password}/{type}")
    public String getText(@FormParam("empcode") String empCode,
                            @FormParam("salesman_name") String salesmanName,
                            @FormParam("customer_name") String customerName,
                            @FormParam("customer_contact_number") String customerContactNo,
                            @FormParam("customer_email") String customerEmail,
                            @FormParam("call_type") String callType,
                            @FormParam("call_description") String callDescription,
                            @FormParam("call_mode") String callMode,
                            @FormParam("call_objective") String callObjective,
                            @FormParam("call_entry_date") String callEntryDate,
                            @FormParam("followup_date") String followupDate,
                            @FormParam("followup_action") String followupAction,
                            @FormParam("call_planned_date") String callPlannedDate,
                            @FormParam("followup_by_date") String followupByDate,
                            @FormParam("close_call_entry") String closeCallEntry,
                            @FormParam("inside_sales") String insideSales,
                            @FormParam("token") String token)
     {
        //TODO return proper representation object
    	ResponseEntity re = new ResponseEntity();
    	re.setCode(Constant.HTTP_USER_EXCEPTION);
    	re.setMessage("");
    	String checkStatus ="";
    	Date date = null;
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
        										"salesman_name : "+salesmanName+" ## "+
        										"customer_name : "+customerName+" ## "+
        										"customer_contact_number : "+customerContactNo+" ## "+
        										"customer_email : "+customerEmail+" ## "+
        										"call_type : "+callType+" ## "+
        										"call_description : "+callDescription+" ## "+
        										"call_mode : "+callMode+" ## "+
        										"call_objective : "+callObjective+" ## "+
        										"call_entry_date : "+callEntryDate+" ## "+
        										"followup_date : "+followupDate+" ## "+
        										"followup_action : "+followupAction+" ## "+
        										"call_planned_date : "+callPlannedDate+" ## "+
        										"followup_by_date : "+followupByDate+" ## "+
        										"close_call_entry : "+closeCallEntry+" ## "+
        										"inside_sales : "+insideSales+" ## "+
        										"token :"+token);

    	if (empCode == null || "".equals(empCode))        
    		re.setMessage("Employee Code is Mandatory");
    	else if(customerName == null || "".equals(customerName))
    		re.setMessage("Customer Name is Mandatory");
    	else if(callType == null || "".equals(callType))
    		re.setMessage("Call Type is Mandatory");
    	else if(callEntryDate == null || "".equals(callEntryDate))
    		re.setMessage("Call Entry Date is Mandatory");    
    	
    	 if (re.getMessage().length()==0)
         {
         	com.pixelcrayons.utility.DBOperation db = null;
 	        try
 	        {
 	             db = new com.pixelcrayons.utility.DBOperation();
 	            boolean a = db.connectToDB();
 	            if (a == false)	                
 	            	re.setMessage("Error in DB connection");
 	            else
 	            {
 	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		re.setMessage(authentication);
		        	else
		        		re=  db.createCallEntry( empCode,
	                            salesmanName,
	                            customerName,
	                            customerContactNo,
	                            customerEmail,
	                            callType,
	                            callDescription,
	                            callMode,
	                            callObjective,
	                            callEntryDate,
	                            followupDate,
	                            followupAction,
	                            callPlannedDate,
	                            followupByDate,
	                            closeCallEntry,
	                            insideSales);
 	            }
 	            	
 	        }
 	        catch(Exception e)
 	        {
 	        	re.setMessage(e.getMessage());
 	        }
 	        finally
 	        {
 	        	if (db != null)
 	        		db.closeConnection();
 	        }
         }
         ObjectMapper mapper = new ObjectMapper();
        
         try {
 			checkStatus = mapper.writeValueAsString(re);
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
