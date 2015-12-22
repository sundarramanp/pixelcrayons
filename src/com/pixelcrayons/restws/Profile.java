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
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pixelcrayons.entity.ProfileResponse;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;

/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("profile")
public class Profile {
    @Context
    private UriInfo context;

    /** Creates a new instance of Profile */
    public Profile() {
    }

    /**
     * Retrieves representation of an instance of com.restws.Profile
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        return "User Profile Web Service";
    }

    /**
     * PUT method for updating or creating an instance of Profile
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putText(@FormParam("empcode") String empCode,
                         @FormParam("username") String userName,
                         @FormParam("old_password") String oldPassword,
                         @FormParam("new_password") String newPassword,
                         @FormParam("token") String token)
    {
    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  empcode :"+empCode+" ## "+
        										"username : "+userName+" ## "+
        										"old_password : "+oldPassword+" ## "+
        										"new_password :"+newPassword);
    
    	ProfileResponse pr = new ProfileResponse();
    	pr.setCode(Constant.HTTP_USER_EXCEPTION);
    	pr.setMessage("");    	
    	if (userName == null || "".equalsIgnoreCase(userName))
    	{
    		pr.setMessage("Username is Mandatory");
    	}    	
    	else if (oldPassword == null || "".equalsIgnoreCase(oldPassword))
    	{
    		pr.setMessage("Old Password is Mandatory");
    	}      
        else if(newPassword == null || "".equalsIgnoreCase(newPassword))
        {
        	pr.setMessage("New Password is Mandatory");
        }        
    	if (oldPassword.equals(newPassword))
    	{
    		pr.setMessage("New Password should not be same as Old Password");
    	}
        if (pr.getMessage().length()==0)
        {
        	com.pixelcrayons.utility.DBOperation db = null;
	        try
	        {
	             db = new com.pixelcrayons.utility.DBOperation();
	            boolean a = db.connectToDB();
	            if (a == false)	                
	            	pr.setMessage("Error in DB connection");
	            else
	            {
	            	String authentication = db.checkAuthentication(token);
	            	System.out.println("authentication"+authentication);
		        	if(!authentication.equals("AUTHENTICATED"))
		        		pr.setMessage(authentication);
		        	else
		        		pr=  db.updateUserProfile( userName,oldPassword,newPassword.toUpperCase());
	            }
	            	
	        }
	        catch(Exception e)
	        {
	        	pr.setMessage(e.getMessage());
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
			checkStatus = mapper.writeValueAsString(pr);
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
