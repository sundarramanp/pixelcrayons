package com.pixelcrayons.restws;


import java.io.IOException;
import java.util.Calendar;

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

import com.pixelcrayons.entity.ProfileResponse;
import com.pixelcrayons.entity.SalesmanDDEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

@Path("sales_man_list")
public class SalesmanList {
    @Context
    private UriInfo context;

    /** Creates a new instance of Profile */
    public SalesmanList() {
    }

    /**
     * Retrieves representation of an instance of com.restws.Profile
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        return "Salesman drop down Web Service";
    }

    /**
     * PUT method for updating or creating an instance of Profile
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putText(@FormParam("token") String token)
    {
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  token :"+token);
    
        SalesmanDDEntity ddSE = new SalesmanDDEntity();
        ddSE.setCode(Constant.HTTP_USER_EXCEPTION);
        ddSE.setMessage("");    	
    	
        if (ddSE.getMessage().length()==0)
        {
        	com.pixelcrayons.utility.DBOperation db = null;
	        try
	        {
	             db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)	                
	            	ddSE.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		ddSE.setMessage(authentication);
		        	else
		        		ddSE=  db.getSalesmanEntity();
	            }
	            	
	        }
	        catch(Exception e)
	        {
	        	ddSE.setMessage(e.getMessage());
	        }
	        finally
	        {
	        	if (db != null)
	        		db.closeConnection();
	        }
        }
        ObjectMapper mapper = new ObjectMapper();
        String checkStatus="";
        try {
			checkStatus = mapper.writeValueAsString(ddSE);
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
        System.out.println(3);
        return checkStatus;
    }
}

