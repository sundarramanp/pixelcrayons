/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pixelcrayons.entity.ViewSalesPersonEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("view_sales_person")
public class SMVIewSalesPerson {
    @Context
    private UriInfo context;

    /** Creates a new instance of SMVIewSalesPerson */
    public SMVIewSalesPerson() {
    }

    /**
     * Retrieves representation of an instance of com.restws.SMVIewSalesPerson
     * @return an instance of java.lang.String
     */
    @GET   
    public String getText() {
        //TODO return proper representation object
        return "SM-VIew Sales Person Web Service";
    }

    /**
     * PUT method for updating or creating an instance of SMVIewSalesPerson
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces("text/plain")
    public String putXml(@FormParam("sales_person_name") String salesPersonName,
                         @FormParam("empcode") String empCode,
                         @FormParam("token") String token)
    {
    	ViewSalesPersonEntity vspe = new ViewSalesPersonEntity();
    	vspe.setCode(Constant.HTTP_USER_EXCEPTION);
    	vspe.setMessage("");
    	String checkStatus ="";
    	Date date = null;
    	WriteLog.setMessage("********* Message Start Time : "+ java.util.Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  sales_person_name :"+salesPersonName+" ## "+
        										"empcode : "+empCode+" ## "+"token : "+token);

    	if (empCode == null || "".equals(empCode))        
    		vspe.setMessage("Employee Code is Mandatory");   
    	if(vspe.getMessage().equals(""))
    	{
	    	com.pixelcrayons.utility.DBOperation db = null;
	        try
	        {
	            db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)
	            	vspe.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		vspe.setMessage(authentication);
		        	else
		        		vspe=  db.smVIewSalesPerson(empCode);
	            }
	        }
	        catch(Exception e)
	        {
	        	vspe.setMessage( e.getMessage());
	        }
	        finally
	        {
	        	db.closeConnection();
	        }
    	}
        ObjectMapper mapper = new ObjectMapper();      
        
        try 
        {
			checkStatus = mapper.writeValueAsString(vspe);
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
