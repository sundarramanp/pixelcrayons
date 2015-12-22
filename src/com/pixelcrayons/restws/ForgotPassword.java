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
import com.pixelcrayons.entity.ResponseEntity;
import com.pixelcrayons.utility.Constant;
import com.pixelcrayons.utility.WriteLog;
@Path("forgot_password")
public class ForgotPassword {
	 @Context
	    private UriInfo context;

	    /** Creates a new instance of Profile */
	    public ForgotPassword() {
	    }

	    /**
	     * Retrieves representation of an instance of com.restws.Profile
	     * @return an instance of java.lang.String
	     */
	    @GET
	    @Produces("text/plain")
	    public String getText() {
	        //TODO return proper representation object
	        return "Forgot Password Web Service";
	    }
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
	    public String putText(@FormParam("username") String userName,
	                         @FormParam("type") String type,
	                         @FormParam("token") String token)
	    {
	    	ResponseEntity fae = new ResponseEntity();
	    	fae.setCode(Constant.HTTP_USER_EXCEPTION);
	    	fae.setMessage("");
	    	String checkStatus ="";
	    	WriteLog.setMessage("********* Message Start Time : "+ Calendar.getInstance().getTime().toString() +" "+getText()+" *********");
	        WriteLog.setMessage("   ----- Request Message -> username : "+userName+" ## "+	        										
	        										"type :"+type);
	    
	    	if (userName == null || "".equalsIgnoreCase(userName))
	    	{
	    		fae.setMessage("Username is Mandatory");
	    	}    	
	    	
	        if (fae.getMessage().length()==0)
	        {
	        	com.pixelcrayons.utility.DBOperation db = null;
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
			        		fae=  db.forgotPassword(userName);
		            }
		            	
		        }
		        catch(Exception e)
		        {
		        	fae.setMessage(e.getMessage());
		        }
		        finally
		        {
		        	if (db != null)
		        		db.closeConnection();
		        }
	        }
	        ObjectMapper mapper = new ObjectMapper();
	         checkStatus="";
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
	        System.out.println(3);
	        return checkStatus;
	    }

}
