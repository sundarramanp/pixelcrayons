/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.restws;

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

import com.pixelcrayons.utility.WriteLog;


/**
 * REST Web Service
 *
 * @author sundar.inmaa
 */

@Path("login")
public class Login {
    @Context
    private UriInfo context;

    /** Creates a new instance of GenericResource */
    public Login() {
    }

    /**
     * Retrieves representation of an instance of com.restws.GenericResource
     * @return an instance of java.lang.String
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     */
    
     @GET       
     public String getText() {
    	 return "Login Web Services";
              
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
   // @Path("{username}/{password}/{type}")
    public String putText(@FormParam("username") String userid,
                          @FormParam("password") String password,
                          @FormParam("type") String type) {        //
        String checkStatus;
        WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
        WriteLog.setMessage("   ----- Request Message ->  username :"+userid+" ## "+
        										"password : "+password+" ## "+
        										"type :"+type);
        com.pixelcrayons.utility.DBOperation db = null;
        try
        {        	
             db = new com.pixelcrayons.utility.DBOperation();        
	         	    	         
	         ObjectMapper mapper = new ObjectMapper();
	   	     checkStatus = mapper.writeValueAsString(db.checkEmpExist(userid,password));         
	   	     db.closeConnection();
        }
        catch(Exception e)
        {
            checkStatus = e.getMessage();
        }
        finally
        {
        	db.closeConnection();
        }
        WriteLog.setMessage("   ----- Response Message -> "+checkStatus);
        WriteLog.setMessage("********* Message End Time   : "+java.util.Calendar.getInstance().getTime().toString()+" *********");
        return checkStatus;
    }
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
   
}
