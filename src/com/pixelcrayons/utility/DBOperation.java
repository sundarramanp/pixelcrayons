/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pixelcrayons.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;

import com.pixelcrayons.entity.BusinessListEntiry;
import com.pixelcrayons.entity.DropDownEntity;
import com.pixelcrayons.entity.LoginResponse;
import com.pixelcrayons.entity.ProfileResponse;
import com.pixelcrayons.entity.ResponseEntity;
import com.pixelcrayons.entity.SEPreCallPlanEntity;
import com.pixelcrayons.entity.SMCallEntryDetailEntity;
import com.pixelcrayons.entity.SMPreCallPlanEntity;
import com.pixelcrayons.entity.SalesmanDDEntity;
import com.pixelcrayons.entity.SearchCustomerEntity;
import com.pixelcrayons.entity.ViewCustomerEntity;
import com.pixelcrayons.entity.ViewSalesPersonEntity;
import com.pixelcrayons.entity.ViewSalesPersonEntity1;
/**
 *
 * @author sundar.inmaa
 */
public class DBOperation {
    private String connectString = "jdbc:oracle:thin:@";
	private String dbServer = "192.168.1.60";
	private String dbSID = "SAASqc";
	private String dbUserName = "live";
	private String dbPassword = "newageqc";
    private Connection dbConnection = null;
	private ResultSet resultset = null;
	private Statement statement = null;
	HashMap<String, String> empDetail = new HashMap<String, String>(); 
    
	public  boolean connected;    
        public boolean connectToDB() throws SQLException, ClassNotFoundException, Exception
        {
                connected = false;
		try
                {
			connectString = connectString+dbServer+":1521:"+dbSID;                      
			Class.forName("oracle.jdbc.OracleDriver");                        
			dbConnection = DriverManager.getConnection(connectString, dbUserName, dbPassword);
			dbConnection.setAutoCommit(false);
			statement = dbConnection.createStatement();
                        connected = true;

                }
        catch(SQLException sqlexception)
        {
            connected = false;
            System.out.println("Error on connecting db == "+sqlexception);

        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            connected = false;
            System.out.println("Error on connecting db == "+classnotfoundexception);

        }
             return connected;
        }
      
         public void closeConnection()
         {
       
             try
             {
                 if (resultset != null)
                	 resultset.close();
                 if (statement != null)
                	 statement.close();
                 if (dbConnection != null)
                	 dbConnection.close();
                                
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }
             finally
             {
            	 resultset = null;
            	 statement = null;
                 dbConnection = null;
             }
            
         }
        public HashMap<String, String> getEmpDetail() {
     		return empDetail;
     	}

     	public void setEmpDetail(HashMap<String, String> empDetail) {
     		this.empDetail = empDetail;
     	}
     	private String getEmployeeList(HashMap<String, String> empDetail)
     	{
     	if (!empDetail.isEmpty())
     	{     		 
			  String result ="";
			  Set set = empDetail.entrySet();
			  Iterator i = set.iterator();
     	      while(i.hasNext()) {
     	         Map.Entry me = (Map.Entry)i.next();
     	         result = result+"'"+me.getKey()+"',";
     	       
     	      }     	          	    
     		return "("+result.substring(0,result.length()-1)+")";
     	}
     	else
     	return "";
     	}
    public String checkAuthentication(String token)
    {
    	if (token == null ||"".equals(token))
			return "Token is Mandatory to validate Authentication";
    	try
    	{
    		Integer.parseInt(token);
    	}
    	catch(Exception e)
    	{
    		return "Token ID should be Numberic character";
    	}
    	String stmt;    	
    	if (connected)
		{
    		 String tokenExpireLimit = Constant.TOKEN_EXPIRE_LIMIT; 
			 stmt = "select employee_code,pk_employee_master.get_name(employee_code) employee_name from prs_ws_token_Detail "
			 		+ "where  create_Date between sysdate- "+tokenExpireLimit+" and sysdate+"+tokenExpireLimit
			 		+ " and token_id = '"+token+"'";
			 System.out.println(stmt);
			try
			{
					resultset = statement.executeQuery(stmt);
					resultset.next();		
					if (resultset.getRow() == 0)
					{						
	                 	return "Unauthorized Request";					
					}
					empDetail.put(resultset.getString("employee_code"), resultset.getString("employee_name"));					
					stmt = "select access_code,pk_employee_master.get_name(access_code) employee_name from employee_access_detail "
							+ " where employee_code = '"+resultset.getString("employee_code")+"'";
					if (resultset != null)
						resultset.close();
					resultset = statement.executeQuery(stmt);
					 while (resultset.next()) 
					 {
						 empDetail.put(resultset.getString("access_code"), resultset.getString("employee_name"));				      					
					 }
			}
			catch(SQLException er)
			{
				return ("Error while selecting WebService Token Detail "+er.toString());
				
			}		
		}
        else
             return "Error in Database Connection";
    	if(empDetail.size() > 0)
    		setEmpDetail(empDetail);
    	return "AUTHENTICATED";
    }
    public SalesmanDDEntity getSalesmanEntity()
    {
    	HashMap<String, String> salesmanList = getEmpDetail();
    	SalesmanDDEntity sdde = new SalesmanDDEntity();
    	sdde.setCode(Constant.HTTP_USER_EXCEPTION);
    	sdde.setMessage("");
    	if (!salesmanList.isEmpty())
     	{     		 
			  String result ="";
			  Set set = salesmanList.entrySet();
			  Iterator i = set.iterator();
			  ArrayList<SalesmanDDEntity.SalesmanDD> obj = new ArrayList<SalesmanDDEntity.SalesmanDD>();
     	      while(i.hasNext()) {
     	         Map.Entry me = (Map.Entry)i.next();
     	         result = result+"'"+me.getKey()+"',";
     	        obj.add(sdde.new SalesmanDD(me.getValue().toString(),me.getKey().toString()));     	       
     	      } 
     	     sdde.setSalesmanName(obj);
     	     sdde.setCode(Constant.HTTP_SUCCESS);
     	     sdde.setMessage("Success");
     	}
    	return sdde;
    }
    public DropDownEntity getDropDownDetail()
    {
    	DropDownEntity dde = new DropDownEntity();
    	dde.setCode(Constant.HTTP_USER_EXCEPTION);
    	dde.setMessage("");
    	HashMap<String, String> salesmanList = getEmpDetail();
    	String stmt;
    	if (!salesmanList.isEmpty())
     	{     
    		try
    		{
			  String result ="";
			  Set set = salesmanList.entrySet();
			  Iterator i = set.iterator();
			  ArrayList<DropDownEntity.AllMaster> obj = new ArrayList<DropDownEntity.AllMaster>();
     	      while(i.hasNext()) {
     	         Map.Entry me = (Map.Entry)i.next();
     	         result = result+"'"+me.getKey()+"',";
     	        obj.add(dde.new AllMaster(me.getKey().toString(),me.getValue().toString()));     	       
     	      } 
     	     dde.setSalesMan(obj);     	  
    		}
    		catch(Exception e)
    		{
    			dde.setMessage("Error while selecting salesman list "+e.getMessage());
    			return dde;
    		}
    		ArrayList<DropDownEntity.AllMaster> obj = new ArrayList<DropDownEntity.AllMaster>();
    		obj.add(dde.new AllMaster("PHONE","Phone"));
    		obj.add(dde.new AllMaster("VISIT","Visit"));   
    		obj.add(dde.new AllMaster("EMAIL","Email"));   
    		dde.setCallMode(obj);
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> salesTerritory = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from territory_master "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			salesTerritory.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setSalesTerritory(salesTerritory);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting territory_master "+er.toString());
                    dde.setMessage("Error while selecting territory_master "+er.toString());     
                    return dde;
            } 
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> callType = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from call_type_master "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			callType.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setCallType(callType);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting call_type_master "+er.toString());
                    dde.setMessage("Error while selecting call_type_master "+er.toString());  
                    return dde;
            }
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> segment = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from segment_master "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			segment.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setSegment(segment);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting segment_master "+er.toString());
                    dde.setMessage("Error while selecting segment_master "+er.toString());   
                    return dde;
            }
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> period = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from period_master "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			period.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setPeriod(period);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting period_master "+er.toString());
                    dde.setMessage("Error while selecting period_master "+er.toString());    
                    return dde;
            }
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> tos = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from tos_master  "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			tos.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setPeriod(tos);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting TOS Master "+er.toString());
                    dde.setMessage("Error while selecting TOS Master "+er.toString()); 
                    return dde;
            }
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> cm = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from competitor_master  "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			cm.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setCompetitor(cm);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting competitor_master  "+er.toString());
                    dde.setMessage("Error while selecting competitor_master "+er.toString()); 
                    return dde;
            }
    		try
    		{
	    		ArrayList<DropDownEntity.AllMaster> am = new ArrayList<DropDownEntity.AllMaster>();
	    		stmt = "select code,name from action_master  "
	    				+ "where lov_status <> 'HIDE'";
	    		resultset = statement.executeQuery(stmt);
	    		while (resultset.next()) 
                {
	    			am.add(dde.new AllMaster(resultset.getString("code"),resultset.getString("name")));
                }
	    		dde.setFollowup_action(am);
    		}
    		catch(Exception er)
            {
                    System.out.println("Error while selecting Action Master  "+er.toString());
                    dde.setMessage("Error while selecting Action Master "+er.toString()); 
                    return dde;
            }
    		ArrayList<DropDownEntity.AllMaster> followupAction = new ArrayList<DropDownEntity.AllMaster>();
    		followupAction.add(dde.new AllMaster("Y","Yes"));
    		followupAction.add(dde.new AllMaster("N","No"));       		   
    		dde.setCallMode(followupAction);
     	}
    	 dde.setCode(Constant.HTTP_SUCCESS);
	     dde.setMessage("Success");
    	return dde;
    }
    public LoginResponse checkEmpExist(String email,String password) 
	{

         String stmt = "";
         LoginResponse lr = new LoginResponse();
         lr.setToken(-1);
		 lr.setEmpcode("");
		 lr.setEmpType("");		 
		 lr.setName("");
		 lr.setCode(417);
		 lr.setMessage("");
		 if (email == null || "".equals(email))
			 lr.setMessage("eMail ID is Mandatory");
		 else if (password == null || "".equals(password))
			 lr.setMessage("Password is Mandatory");
	
		 try {
			boolean a = connectToDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			lr.setMessage("Error in Database Connection"+e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			lr.setMessage("Error in Database Connection  ClassNotFoundException"+e.getMessage());
		} catch (Exception e) {
			// lr.setMessage("Error in Database Connection"+e.getMessage());			
		}
		 if (lr.getMessage().length() > 0)
			 return lr;
		 System.out.println("connected"+connected);
		if (connected)
		{
			 stmt = "select pk_login_Ws_seq.nextval token,"
                                + "aum.name name,"
                                + "aed.employee_code,"
                                + "pk_hrms_designation_master.get_name(em.designation_code) designation "
                                + " from    adm_user_master aum,"
                                + " employee_master em,"
                                + " adm_employee_Detail aed"
                                + " where aum.name             = aed.user_name"
                                + " and  aed.company_code     = em.company_code"
                                + " and aed.employee_code    = em.code"
                                + " and aum.password    =  '"+password+"'"
                                + " and em.email        = '"+email+"'"
                                		+ " and rownum = 1";
                       System.out.println(stmt);

			try
			{
					resultset = statement.executeQuery(stmt);
					resultset.next();		
					if (resultset.getRow() == 0)
					{
						lr.setMessage("Invalid credentials - "+email+" / "+password);
	                 	return lr;					
					}
					 lr.setToken(resultset.getInt("token"));
					 lr.setEmpcode(resultset.getString("employee_code"));
					 lr.setEmpType(resultset.getString("designation"));
					 lr.setName(resultset.getString("name"));
					 try {
						 stmt =" insert into prs_ws_token_detail(create_user,"
							 		+ "create_date,"
							 		+ "token_id,employee_code,"
							 		+ "email,designation,note)"
							 		+ "values "
							 		+ "('"+resultset.getString("name")+"',sysdate,"					 		
							 		+  "'"+lr.getToken()+"',"
							 		+  "'"+lr.getEmpcode()+"',"
							 		+  "'"+email+"',"
							 		+  "'"+lr.getEmpType()+"',null)";
						 	System.out.println(stmt);
			                statement.executeQuery(stmt);
			                dbConnection.commit();
			           	 System.out.println("commit");
			            }
					catch(Exception er)
					{
						System.out.println("Error while inserting into PRS WebService Token Detail "+er.toString());
						lr.setMessage("Error while inserting into PRS WebService Token Detail : "+er.toString());
			            return lr;
					}
					 
					lr.setMessage("Success");						 
					lr.setCode(200);							 
					 
			}
			catch(SQLException er)
			{
				System.out.println("Error while select outbound record "+er.toString());
				lr.setMessage("Error while select outbound record "+er.toString());
			}		
		}
        else
            lr.setMessage("Error in Database Connection");
                if (lr.getEmpcode().length() ==0)
                	 lr.setMessage("Invalid credentials - "+email+" / "+password);
        closeConnection();         
           return lr;
	}
    public SEPreCallPlanEntity se_pre_call_plan(String empCode,
    											String fromDate,
    											String toDate,
    											int startLimit)
    {
    	SEPreCallPlanEntity cpe = new SEPreCallPlanEntity();
    	cpe.setCode(Constant.HTTP_USER_EXCEPTION);
    	cpe.setMessage("");

        String stmt = "select code"
                + " from employee_master"
                + " where code = '"+empCode+"'";
        System.out.println(stmt);
        String salemanList = getEmployeeList(getEmpDetail());
        try
        {
                resultset = statement.executeQuery(stmt);                
                resultset.next();  
                if (resultset.getRow() == 0)
                {
                	cpe.setMessage("Invalid Employee Code ("+empCode+")");
                	return cpe;
                }  
                else
                {
                	if (!salemanList.contains(empCode))
           		 	{
                		cpe.setMessage("Employee Code ("+empCode+") doesn't have privilege to access.");
           			 	return cpe;
           		 	}
                }
        }
        catch(SQLException er)
        {
                System.out.println("Error while selecting employee master"+er.toString());
                cpe.setMessage("Error while selecting employee master"+er.toString());                
        }    
         
        try
        {        	
        	stmt = "select * from(select rank() over (order by call_no) record_id,call_no,customer_code,customer_name,"
            		+ "customer_category,sales_territory,to_char(followup_date,'MM/DD/RRRR') followup_date,call_type,call_status,"
            		+ "call_mode,followup_action"
            		+ " from CALL_FOLLOW_UP_MONITOR"
            		+ " where salesman_code = '"+empCode+"'"
            		+ " and nvl(followup_completed, 'N') = 'N'"
            		+ " order by call_no)"
            		+ "where record_id between "+startLimit+" and "+ (startLimit+9);   
                resultset = statement.executeQuery(stmt);
                ArrayList<SEPreCallPlanEntity.PreCallPlanEntity> obj = new ArrayList<SEPreCallPlanEntity.PreCallPlanEntity>();
                int recordCount =0;
                while (resultset.next()) 
                {            
	                if (resultset.getRow() != 0)
	                {   
	                	recordCount++;
	                	obj.add(cpe.new PreCallPlanEntity(resultset.getInt("record_id"),
	                			resultset.getLong("call_no"),
	                			resultset.getString("customer_code"),
	                			resultset.getString("customer_name"),
	                			resultset.getString("customer_category"),
	                			resultset.getString("sales_territory"),
	                			resultset.getString("followup_date"),
	                			resultset.getString("call_type"),
	                			resultset.getString("call_status"),
	                			resultset.getString("call_mode"),
	                			resultset.getString("followup_action")
	                			));  	                	
	                	
	                }
	                cpe.setResult(obj);
                    cpe.setCode(Constant.HTTP_SUCCESS);
                    cpe.setMessage("Success");
                }     
                if (recordCount == 0)
                {
                	cpe.setCode(Constant.HTTP_NO_CONTENT);
                    cpe.setMessage("No Data Found");	  
                    cpe.setResult(null);                    
                }
        }
        catch(SQLException er)
        {
                System.out.println("Error while selecting employee master"+er.toString());
                cpe.setMessage("Error while selecting employee master"+er.toString());                
        }              
    	return cpe;
    }
    private String getDBData(String stmt)
    {
       String result ="";
       System.out.println("getDBData "+ stmt);
        try
        {
                resultset = statement.executeQuery(stmt);            
                resultset.next();
                try
                {
                result = resultset.getString(1);
            }
                catch(Exception e)
                {
                    return "";
                }
         }
       
        catch(SQLException er)
        {
                System.out.println("Error while select outbound record "+er.toString());
                return stmt;
        }
        System.out.println("result"+result);
       return result;

    }
     public ResponseEntity createCallEntry(String empCode,
                                    String salesmanName,
                                    String customerName,
                                    String customerContactNo,
                                    String customerEmail,
                                    String callType,
                                    String callDescription,
                                    String callMode,
                                    String callObjective,
                                    String callEntryDate,
                                    String followupDate,
                                    String followupAction,
                                    String callPlannedDate,
                                    String followupByDate,
                                    String closeCallEntry,
                                    String insideSales)
    {
    	 
    	 ResponseEntity re = new ResponseEntity();
    	 re.setCode(Constant.HTTP_USER_EXCEPTION);
    	 re.setMessage("");
    	 String stmt ="";
    	 String empName ="";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (empCode!= null && !"".equals(empCode))
    	 {
	          stmt = "select code,name"
	                 + " from employee_master"
	                 + " where code = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 re.setMessage("Employee Code ("+empCode+") does not exist in master");
	                 	return re;
	                 }
	                 else
	                 {
	                	 if (!salemanList.contains(empCode))
	            		 {
	                		 re.setMessage("Employee Code ("+empCode+") doesn't have privilege to create call entry.");
	            			 return re;
	            		 }
	                	 empName = resultset.getString("name");
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 re.setMessage("Error while selecting employee master"+er.toString());                
	         }    
    	 }
    	 String customerCode ="";
    	 if (customerName!= null && !"".equals(customerName))
    	 {
    		 stmt = "select code"
	                 + " from customer_master"
	                 + " where name = '"+customerName+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 re.setMessage("Customer Name ("+customerName+") does not exist in master");
	                 	 return re;
	                 } 
	                 else
	                	 customerCode = resultset.getString("code");
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 re.setMessage("Error while selecting customer master"+er.toString());
	                 return re;
	         }
    	 }

	
        String callNo = "";
         stmt = "select greatest(to_number(replace(to_char(sysdate, 'RRRRMMDD') || '001',' ','')),nvl(max(call_no), 0) + 1) call_no"
                 + " from 	 mms_call_detail"
                 + " where  substr(call_no,1,8) = to_char(sysdate, 'RRRRMMDD')";
         callNo = getDBData(stmt);
         
          stmt = "select name, code, lov_status"
                  + " from call_type_status_master"
                  + " where code = '"+callType+"'";
         String callTypename = "";
         if (callType.length() > 0)
             callTypename = getDBData(stmt);
	
	        String instStmt = "insert into mms_call_detail(create_user,create_date,run_user,run_date,"
	                + "salesman_code,salesman_name,"
	                + "call_date,call_no,"
	                + "call_type,call_type_name,"
	                + "call_mode,"
	                + "customer_code,customer_name,"                                
	                + "followup_date,followup_action,call_description,"
	                + "company_code,branch_code,location_code,"  
	                + "followup_by,followup_by_name,"
	                + "followup_completed,followup_action_description,"
	                + "planned_date,"
	                + "note)"
	                + " values"
	                + "('WebService',sysdate,'WebService',sysdate,"
	                + "'"+empCode+"','"+empName+"',"
	                + "nvl(to_date('"+callEntryDate+"','MM/DD/RR'),sysdate),'"+callNo+"',"
	                + "'"+callType+"','"+callTypename+"',"
	                + "'"+callMode+"',"
	                + "'"+customerCode+"','"+customerName+"',"
	                + "to_date('"+followupDate+"','MM/DD/RR'),'"+followupAction+"','"+callDescription+"',"
	                + "'FSL','AE','11001',"
	                + "null,null,"
	                + "'"+closeCallEntry+"','"+followupAction+"',"
	                + "to_date('"+callPlannedDate+"','MM/DD/RR'),"
	                + "'"+customerContactNo+"'||'-'||'"+customerEmail+"')";
        try {
                statement.executeQuery(instStmt);
                dbConnection.commit();
            }
		catch(Exception er)
		{
			System.out.println("Error while inserting into MMS Client Detail "+er.toString());
            re.setMessage("Error while inserting into MMS Client Detail : "+er.toString());
            return re;
		}

	    return re;      
     }
    
     public ProfileResponse updateUserProfile( String userName,
                                     			String oldPassword,
                                     			String newPassword)
     {             
         ProfileResponse pr = new ProfileResponse();
         pr.setCode(Constant.HTTP_USER_EXCEPTION);
         pr.setMessage("");
         String stmt = "select name,password"
                 + " from adm_user_master"
                 + " where name = '"+userName+"'";
         try
        {
                resultset = statement.executeQuery(stmt); 

                System.out.println("resultsetd"+resultset.getRow());
                resultset.next();  
                if (resultset.getRow() == 0)
                {
                	pr.setMessage("Invalid User Name");
                	return pr;
                }
                               
                if  (!oldPassword.equalsIgnoreCase(resultset.getString("password")))                
                {                	
                	pr.setMessage("Invalid Old Password");
                	return pr;
                }               
                if (resultset.getString("password").length() > 0)
                {
                       try {
                           String updatestmt = "update adm_user_master set password = '"+newPassword+"'"
                                   + "where name = '"+userName+"'";
                            statement.executeQuery(updatestmt);
                            dbConnection.commit();
                            pr.setCode(Constant.HTTP_SUCCESS);
                            pr.setMessage("Successfull");
                        }
						catch(Exception er)
						{
							System.out.println("Error while updating adm_user_master"+er.toString());
							pr.setMessage("Error while updating adm_user_master : "+er.toString());
						}                 
                }
                else
                {
                	pr.setCode(Constant.HTTP_NO_CONTENT);
                    pr.setMessage("No data found");
                }     
        }
        catch(SQLException er)
        {
                System.out.println("Error while select Adm User Master"+er.toString());
                pr.setMessage("Error while select Adm User Master : "+er.toString());                
        }      
          return pr;
     }
     public ViewSalesPersonEntity smVIewSalesPerson(String empCode)
    {
    	 ViewSalesPersonEntity vsp = new ViewSalesPersonEntity();
    	 vsp.setCode(Constant.HTTP_USER_EXCEPTION);
    	 vsp.setMessage("");
    	 String stmt ="";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (empCode.length() > 0)
    	 {
    		 if (!salemanList.contains(empCode))
    		 {
    			 vsp.setMessage("Employee Code ("+empCode+") doesn't have privilege to access.");
    			 return vsp;
    		 }
	          stmt = "select code"
	                 + " from employee_master"
	                 + " where code = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 vsp.setMessage("Invalid Employee Code ("+empCode+")");
	                 	 return vsp;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 vsp.setMessage("Error while selecting employee master"+er.toString());                
	         }    
    	 }
    	 System.out.println();
            stmt = "select call_no,customer_name,"
            				+ "customer_category,sales_territory,"
            				+ "to_Char(followup_date,'MM/DD/RRRR') followup_date,"
            				+ "call_type,followup_action,salesman_name"
            				+ "  from CALL_FOLLOW_UP_MONITOR "
            				+ " where salesman_code= '"+empCode+"' "
            						+ "and salesman_code in " +salemanList;
            
            System.out.println(stmt);
            int recCount =0;
                try
                {
                	resultset = statement.executeQuery(stmt);

                    ArrayList<ViewSalesPersonEntity1> obj = new ArrayList<ViewSalesPersonEntity1>();
                    while (resultset.next()) 
                    {        
    	                if (resultset.getRow() != 0)
    	                {             
    	                	
    	                	obj.add(new ViewSalesPersonEntity1(resultset.getString("call_no"),
    	                			resultset.getString("customer_name"),
    	                			resultset.getString("customer_category"),
    	                			resultset.getString("sales_territory"),
    	                			resultset.getString("followup_date"),
    	                			resultset.getString("call_type"),
    	                			resultset.getString("followup_action"),
    	                			resultset.getString("salesman_name"))
    	                			);     
    	                	recCount++;
    	                }  
                    }
                    if (recCount == 0)
                    {                
                    	vsp.setCode(Constant.HTTP_NO_CONTENT);
                    	vsp.setMessage("No Data found");
                    }
                    else
                    {
                    	vsp.setResult(obj);
                    	vsp.setCode(Constant.HTTP_SUCCESS);
                    	vsp.setMessage("Success");
                    }
                	
                	
			}
			catch(SQLException er)
			{
				 System.out.println("Error while selecting CALL_FOLLOW_UP_MONITOR : "+er.toString());
				 vsp.setMessage("Error while selecting CALL_FOLLOW_UP_MONITOR : "+er.toString());                
			}               
               return vsp;
     }
     public SearchCustomerEntity getSearchCustomer(String empCode,
                                     String customerID,
                                     String fromDate,
                                     String toDate,
                                     String customer_of)
    {
    	 SearchCustomerEntity sce = new SearchCustomerEntity();
    	 sce.setCode(Constant.HTTP_USER_EXCEPTION);
    	 sce.setMessage("");
    	 String stmt ="";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (empCode!= null && !"".equals(empCode))
    	 {
	          stmt = "select code"
	                 + " from employee_master"
	                 + " where code = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	sce.setMessage("Invalid Employee Code ("+empCode+")");
	                 	return sce;
	                 }  
	                 else
	                 {
	                	 if (!salemanList.contains(empCode))
	            		 {
	                		 sce.setMessage("Employee Code ("+empCode+") doesn't have privilege to access.");
	            			 return sce;
	            		 }
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 sce.setMessage("Error while selecting employee master"+er.toString());                
	         }    
    	 }
    	 if (customerID!= null && !"".equals(customerID))
    	 {
    		 stmt = "select code"
	                 + " from customer_master"
	                 + " where code = '"+customerID+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	sce.setMessage("Invalid Customer ID ("+customerID+")");
	                 	return sce;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 sce.setMessage("Error while selecting customer master"+er.toString());                
	         }
    	 }
    	
    	 if (sce.getMessage().length() > 0)
    		 return sce;
    	         
         int recordCount =0;         
         try
         {
        	 stmt = "select distinct pk_employee_master.get_name(csm.salesman_code) salesman_name,"
               		+ "cm.code customer_code,cm.name customer_name, "
               		+ " nvl(cam.mobile_no,cam.phone) mobile_no,cam.address,cam.contact_person,cam.email from customer_segment_master csm, "
               		+ " customer_address_master cam,customer_master cm"
               		+ " where cm.code = csm.customer_code(+)"
               		+ " and cm.code = cam.customer_code(+) "
               		+ " and cam.address_type (+) = 'PRIMARY' ";               
              if (empCode!= null && !"".equals(empCode))
                     stmt = stmt.concat(" and csm.salesman_code= '"+empCode+"'");              
              if (customerID!= null && !"".equals(customerID))
                     stmt = stmt.concat(" and cm.code= '"+customerID+"'");              
              if (fromDate != null && !"".equals(fromDate))
                    stmt = stmt.concat(" and  cm.create_date between to_date('"+fromDate+"', 'MM/DD/RRRR') and to_date('"+toDate+"', 'MM/DD/RRRR')");              
                 resultset = statement.executeQuery(stmt);
                 System.out.println(stmt);
                 ArrayList<SearchCustomerEntity.SearchCustomer> obj = new ArrayList<SearchCustomerEntity.SearchCustomer>();
                 while (resultset.next()) 
                 {      System.out.println("SAdfaf");          
 	                if (resultset.getRow() != 0)
 	                {             
 	                	recordCount++;
 	                	obj.add(sce.new SearchCustomer(
 	                			resultset.getString("salesman_name"),
 	                			resultset.getString("customer_code"),
 	                			resultset.getString("customer_name"),
 	                			resultset.getString("mobile_no"),
 	                			resultset.getString("address"),
 	                			resultset.getString("contact_person"),
 	                			resultset.getString("email"))
 	                			);  
 	                	
 	                	
 	                }  
                 }
                 sce.setResult(obj);
                 sce.setCode(Constant.HTTP_SUCCESS);
                 sce.setMessage("Success");
         }
         
         catch(SQLException er)
         {
                 System.out.println("Error while selecting mms customer detail"+er.toString());
                 sce.setMessage("Error while selecting mms customer detail"+er.toString());                
         }   
         if (recordCount == 0)
         {
        	 sce.setCode(Constant.HTTP_NO_CONTENT);
        	 sce.setMessage("No Data Found");	  
        	 sce.setResult(null);                    
         }
               return sce;
    }
     public ViewCustomerEntity viewCustomer(String customerID,String customerContactNo)
     {
    	 ViewCustomerEntity vce = new ViewCustomerEntity();
    	 vce.setCode(Constant.HTTP_USER_EXCEPTION);
    	 vce.setMessage("");
    	 String stmt ="";
    	 if (customerID != null && !"".equals(customerID))
    	 {
    		 stmt = "select code"
	                 + " from customer_master"
	                 + " where code = '"+customerID+"'";
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 vce.setMessage("Invalid Customer ID ("+customerID+")");
	                 	return vce;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 vce.setMessage("Error while selecting customer master"+er.toString());                
	         }
    	 }
    	 stmt = "select pk_segment_master.get_import_export(segment_code) import_export,"
         		+ "segment_name,pk_period_master.get_name(PERIOD_CODE) period,"
         		+ "nvl(pk_port_master.get_name(por),por) por,nvl(pk_port_master.get_name(fdc),fdc) fdc,no_of_shipment,tos_code,NO_OF_UNIT,"
         		+ "pk_segment_master.get_unit_code(segment_code) unit,closure_date,"
         		+ "CURRENT_POTENTIAL,status,REASON "
                  + " from mms_client_segment_detail mccm "
                  + " where customer_code = '"+customerID+"'";
    	 
    	 System.out.println(stmt);
    	 try
    	 {
             resultset = statement.executeQuery(stmt);             
             ArrayList<ViewCustomerEntity.BusinessName> businessName = new ArrayList<ViewCustomerEntity.BusinessName>();
             while (resultset.next()) 
             {             
	                if (resultset.getRow() != 0)
	                {      
	                	businessName.add(vce.new BusinessName(resultset.getString("import_export"),
	                			resultset.getString("segment_name"),
	                			resultset.getString("period"),
	                			resultset.getString("por"),
	                			resultset.getString("fdc"),
	                			resultset.getString("no_of_shipment"),
	                			resultset.getString("tos_code"),
	                			resultset.getString("NO_OF_UNIT"),
	                			resultset.getString("unit"),
	                			resultset.getString("CURRENT_POTENTIAL"),
	                			resultset.getString("closure_date"),
	                			resultset.getString("status"),
	                			resultset.getString("REASON")));  	                	              	                	
	                }  
             }             
        	 try
        	 {
                 stmt = "select cm.code customer_code,cm.name customer_name,"
                 		+ "nvl(cam.mobile_no,cam.phone) mobile_no,cam.address,"
                 		+ "cam.contact_person,cam.email "
                 		+ "from customer_address_master cam,customer_master cm"
                 		+ " where cm.code = cam.customer_code(+)"
                 		+ " and cam.address_type (+) = 'PRIMARY' "
                 		+ " and cm.code= '"+customerID+"' ";
                 if (customerContactNo != null)
                	 if (customerContactNo.length() >0)
                		 stmt = stmt.concat(" and 1=1 ");
                 System.out.println(stmt);
        		 if (resultset != null)
        			 resultset.close();
                 resultset = statement.executeQuery(stmt);    
                
                 ArrayList<ViewCustomerEntity.ViewCustomer> viewCustomer = new ArrayList<ViewCustomerEntity.ViewCustomer>();                 
                 while (resultset.next()) 
                 {              
    	                if (resultset.getRow() != 0)
    	                {             	                	
    	                	viewCustomer.add(vce.new ViewCustomer(resultset.getString("customer_name"),
    	                			resultset.getString("address"),
    	                			resultset.getString("contact_person"),
    	                			resultset.getString("mobile_no"),
    	                			resultset.getString("email"),
    	                			businessName
    	                			));  	                	                  	                	
    	                }  
                 }
                 vce.setResult(viewCustomer);
                 vce.setCode(Constant.HTTP_SUCCESS);
                 vce.setMessage("Success"); 
        	 }
        	 catch(Exception er)
        	 {
        		 System.out.println("Error while selecting mms_client_contact_master"+er.toString());
        		 vce.setMessage("Error while selecting mms_client_contact_master"+er.toString());                
        	 }
    	 }
    	 catch(Exception er)
    	 {
    		 System.out.println("Error while selecting mms_client_segment_detail"+er.toString());
    		 vce.setMessage("Error while selecting mms_client_segment_detail"+er.toString());
    		 return vce;
    	 }
          return vce;
     }
     public SMPreCallPlanEntity sm_pre_call_plan(String salesmanName)
     {
    	 SMPreCallPlanEntity cpe = new SMPreCallPlanEntity();
    	 cpe.setCode(Constant.HTTP_USER_EXCEPTION);
    	 cpe.setMessage("");
    	 String stmt ="";
    	 String empCode ="";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (salesmanName.length() > 0)
    	 {
    		 stmt = "select code"
	                 + " from employee_master"
	                 + " where name = '"+salesmanName+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 cpe.setMessage("Invalid Salesman Name ID ("+salesmanName+")");
	                 	return cpe;
	                 }    
	                 else
	                 {
	                	 empCode = resultset.getString("code");
	                	 if (!salemanList.contains(empCode))
	            		 {
	                		 cpe.setMessage("Salesman Name ("+salesmanName+") doesn't have privilege to access.");
	            			 return cpe;
	            		 }
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee_master "+er.toString());
	                 cpe.setMessage("Error while selecting employee_master "+er.toString());
	                 return cpe;
	         }
    	 }    
           if (empCode.length() == 0)
           {
        	  cpe.setMessage("Invalid Salesman Name ID ("+salesmanName+")");           
           }
           else
           {               
             	try
       	 		{
         		  stmt = "select call_no,salesman_name,"
             			+ "	customer_name,customer_category,"
             			+ "sales_territory,to_Char(followup_date,'MM/DD/RRRR') followup_date,call_type,"
             			+ "call_status,call_mode,followup_action "
             			+ " from CALL_FOLLOW_UP_MONITOR "
             			+ " where salesman_code= '"+empCode+"' "
                  		+ "and nvl(followup_completed, 'N') = 'N'"
                  		+ "and salesman_code in " +salemanList;    
                
                 System.out.println(stmt);
                 int recCount =0;
	       		 if (resultset != null)
	       			 resultset.close();
	                resultset = statement.executeQuery(stmt);    
               
	                ArrayList<SMPreCallPlanEntity.CallPlan> smentires = new ArrayList<SMPreCallPlanEntity.CallPlan>();                 
	                while (resultset.next()) 
	                {              
	   	                if (resultset.getRow() != 0)
	   	                {             	                	
	   	                	smentires.add(cpe.new CallPlan(resultset.getLong("call_no"),
	   	                			resultset.getString("salesman_name"),
	   	                			resultset.getString("customer_name"),
	   	                			resultset.getString("customer_category"),
	   	                			resultset.getString("sales_territory"),
	   	                			resultset.getString("followup_date"),
	   	                			resultset.getString("call_type"),
	   	                			resultset.getString("call_status"),
	   	                			resultset.getString("call_mode"),
	   	                			resultset.getString("followup_action")
	   	                			));  	
	   	                	recCount++;
	   	                	
	   	                }  
	                }	
                    if (recCount == 0)
                    {                
                    	cpe.setCode(Constant.HTTP_NO_CONTENT);
                    	cpe.setMessage("No Data found");
                    }
                    else
                    {
                    	cpe.setResult(smentires);
                    	cpe.setCode(Constant.HTTP_SUCCESS);
                    	cpe.setMessage("Success");
                    }	                
       	 		}
		       	 catch(Exception er)
		       	 {
		       		 System.out.println("Error while selecting mms_client_contact_master"+er.toString());
		       		 cpe.setMessage("Error while selecting mms_client_contact_master"+er.toString());                
		       	 }
           	}
           return cpe;
     }
     public SMCallEntryDetailEntity getCallEntryDetail(String empCode,
                                      String customerID)
    {
    	 SMCallEntryDetailEntity cede = new SMCallEntryDetailEntity();
    	 cede.setCode(Constant.HTTP_USER_EXCEPTION);
    	 cede.setMessage("");
    	 String stmt ="";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (customerID.length() > 0)
    	 {
    		 stmt = "select code"
	                 + " from customer_master"
	                 + " where code = '"+customerID+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 cede.setMessage("Invalid Customer ID ("+customerID+")");
	                 	return cede;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 cede.setMessage("Error while selecting customer master"+er.toString());
	                 return cede;
	         }
    	 }
    	 if (empCode.length() > 0)
    	 {
    		 stmt = "select code"
	                 + " from employee_master"
	                 + " where code = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 cede.setMessage("Invalid Employee Code ("+empCode+")");
	                 	 return cede;
	                 }  
	                 else
	                 {
	            		 if (!salemanList.contains(empCode))
	            		 {
	            			 cede.setMessage("Employee Code ("+empCode+") doesn't have privilege to access.");
	            			 return cede;
	            		 }
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 cede.setMessage("Error while selecting employee master"+er.toString());
	                 return cede;
	         }
    	 }
    	  
         
         try
    	 {    
        	 stmt = "select salesman_name,call_no,customer_name,call_type,call_mode,"
                     + " call_description,to_Char(create_Date,'MM/DD/RRRR') create_Date,"
                     + " to_Char(followup_date,'MM/DD/RRRR') followup_date,"
                     + " followup_action,followup_by,manager_comments,nvl(followup_completed,'N') followup_completed "   
                     + " from CALL_FOLLOW_UP_MONITOR"
                     + " where salesman_code= '"+empCode+"' ";
             if (customerID.length() > 0)
            	 stmt = stmt.concat(" and customer_code= '"+customerID+"'");
             stmt = stmt.concat(" order by call_no");
             
             System.out.println(stmt);
    		 if (resultset != null)
    			 resultset.close();
             resultset = statement.executeQuery(stmt);    
            
             ArrayList<SMCallEntryDetailEntity.CallEntryDetail> callEntryDetail = new ArrayList<SMCallEntryDetailEntity.CallEntryDetail>();                 
             while (resultset.next()) 
             {              
	                if (resultset.getRow() != 0)
	                {             	                	
	                	callEntryDetail.add(cede.new CallEntryDetail(resultset.getString("salesman_name"),
	                			resultset.getLong("call_no"),
	                			resultset.getString("customer_name"),
	                			resultset.getString("call_type"),
	                			resultset.getString("call_mode"),
	                			resultset.getString("call_description"),
	                			resultset.getString("create_Date"),
	                			resultset.getString("followup_date"),
	                			resultset.getString("followup_action"),
	                			resultset.getString("followup_by"),
	                			resultset.getString("manager_comments"),
	                			resultset.getString("followup_completed")
	                			));  	                	              
	                	
	                }  
             }
             cede.setResult(callEntryDetail);
             cede.setCode(Constant.HTTP_SUCCESS);
             cede.setMessage("Success"); 
    	 }
    	 catch(Exception er)
    	 {
    		 System.out.println("Error while selecting mms_client_contact_master"+er.toString());
    		 cede.setMessage("Error while selecting mms_client_contact_master"+er.toString());                
    	 }
               return cede;
     }
     public ResponseEntity followUpAction(long callNumber,
                                  String action)
     {
    	 ResponseEntity fae = new ResponseEntity();
    	 fae.setCode(Constant.HTTP_USER_EXCEPTION);
    	 fae.setMessage("");
    	 String stmt ="";
    	 
    		 stmt = "select call_no"
	                 + " from mms_call_detail"
	                 + " where call_no = '"+callNumber+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 fae.setMessage("Call Number does not exist ("+callNumber+")");
	                 	return fae;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 fae.setMessage("Error while selecting customer master"+er.toString());
	                 return fae;
	         }
    	


	         try {
                   String updatestmt = "update mms_call_detail set followup_completed = '"+action+"'"
                           + "where call_no = '"+callNumber+"'";
                   System.out.println(updatestmt);
                    statement.executeQuery(updatestmt);
                    dbConnection.commit();
                    fae.setCode(Constant.HTTP_SUCCESS);
                    fae.setMessage("Success"); 
                }
                catch(Exception er)
                {
                        System.out.println("Error while updating adm_user_master"+er.toString());
                        fae.setMessage("Error while updating adm_user_master"+er.toString());
                }                 
               return fae;
     }
     public ResponseEntity addCustomer(String empCode,
	            String salesmanName,
	            String customerName,
	            String customerAddress,
	            String customerContactDetail,
	            String customerContactNumber,
	            String customerEmail,
	            String city,
	            String countryCode,
	            String customerType,
	            ArrayList<BusinessListEntiry> businessList)
	   {
    	 ResponseEntity fae = new ResponseEntity();
    	 fae.setCode(Constant.HTTP_USER_EXCEPTION);
    	 fae.setMessage("");
    	 String stmt ="";
    	 String customerCode = "";
    	 String salemanList = getEmployeeList(getEmpDetail());
    	 if (empCode != null && empCode.length() > 0)
    	 {
    		 stmt = "select name,company_code,location_code,branch_code,employee_location_code"
	                 + " from employee_master"
	                 + " where code = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 fae.setMessage("Employee Code ("+empCode+") does not exist in master");
	                 	 return fae;
	                 } 
	                 else
	                 {
	                	 if (!salemanList.contains(empCode))
	            		 {
	                		 fae.setMessage("Employee Code ("+empCode+") doesn't have privilege to access.");
	            			 return fae;
	            		 } 
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 fae.setMessage("Error while selecting employee master - "+er.toString());
	                 return fae;
	         }
    	 }
    	 if (countryCode != null && countryCode.length() > 0)
    	 {
    		 stmt = "select code"
	                 + " from country_master"
	                 + " where code = '"+countryCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 fae.setMessage("Country Code ("+countryCode+") does not exist in master.");
	                 	 return fae;
	                 }                              
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting country master"+er.toString());
	                 fae.setMessage("Error while selecting country master - "+er.toString());
	                 return fae;
	         }
    	 }
    	 if (customerName != null && customerName.length() > 0)
    	 {
    		 stmt = "select code"
	                 + " from customer_master"
	                 + " where name = '"+customerName+"'"
	                 		+ " and country_code = '"+countryCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() != 0)
	                 {	                	 
	                	fae.setMessage(customerName+" was already belonging to this country ("+customerName+") in core system. REF. Code :: " +resultset.getString("code"));
	                 	return fae;
	                 }    	                 
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting customer master"+er.toString());
	                 fae.setMessage("Error while selecting customer master "+er.toString());
	                 return fae;
	         }
    	 }
    	 String companyCode="",
    	 		branchCode="",
    	 		locationCode="",
    	 		userName ="";
    	 if (empCode != null && empCode.length() > 0)
    	 {
    		 stmt = "select aub.company_code,aub.branch_Code,aub.location_code,aed.user_name "
    		 		+ "from adm_employee_Detail aed,adm_user_branch aub"
    		 		+ " where aub.user_name = aed.user_name"
    		 		+ " and is_default = 'Y'"
    		 		+ " and aed.employee_code  = '"+empCode+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 fae.setMessage("Employee default location not found. ("+empCode+").");
	                 	 return fae;
	                 }
	                 else
	                 {
	                	 companyCode = resultset.getString("company_code") ;
	                	 branchCode = resultset.getString("branch_Code") ;
	                	 locationCode = resultset.getString("location_code") ;
	                	 userName = resultset.getString("user_name") ;
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting country master"+er.toString());
	                 fae.setMessage("Error while selecting country master - "+er.toString());
	                 return fae;
	         }
    	 }
    	 stmt = "select nvl(max(uid_code)+1,to_number(to_char(sysdate,'YYYYMM')||'00001')) customer_uid"
    	 		+ " FROM customer_for_approval"
    	 		+ " where  substr(uid_code,1,6) = to_char(sysdate,'YYYYMM')";
    	 
    	 customerCode = getDBData(stmt);
    	 
    	 String isAgent,isVendor,isRemoval,isCorporateCustomer;
    	 if (customerType.equals("CORPORATE_CUSTOMER"))
    			 isCorporateCustomer = "Y";
    	 else
    		     isCorporateCustomer = "N";
    	 if (customerType.equals("AGENT"))
    			 isAgent = "Y";
    	 else
    		 isAgent = "N";
    	 if (customerType.equals("VENDOR"))
    			 isVendor = "Y";
    	 else
    		 isVendor = "N";
    	 if (customerType.equals("REMOVAL"))
    			 isRemoval = "Y";
    	 else
    		 isRemoval = "N";
    	 stmt = "insert into customer_for_approval ( create_user,create_date,run_user, run_date,"
    	 							+ "company_code,branch_code,location_code,"
    	 							+ "uid_code,term_code,"
    	 							+ "name, lov_status,   "
    	 							+ "country_code,is_customer,"
    	 							+ "is_corporate_customer,"
    	 							+ "is_overseas_agent,"
    	 							+ "is_vendor,is_prs_customer,"
    	 							+ "base_currency_code,"
    	 							+ "address,city_code,city,"
    	 							+ "phone, email,"    	 						
    	 							+ "edi_receiver_id,"
    	 							+ "ws_uid,STATUS,"
    	 							+ "note,TYPE)"
    	 							+ "values"
    	 							+ "('"+userName+"',sysdate,'"+userName+"', sysdate,"
    	 							+ "'"+companyCode+"','"+branchCode+"','"+locationCode+"',"
    	 							+ "'"+customerCode+"','CASH',"
    	 							+ "'"+customerName+"','DISPLAY',"
    	 							+ "'"+countryCode+"','Y',"
    	 							+ "'"+isCorporateCustomer+"','"+isAgent+"',"
    	 							+ "'"+isVendor+"','"+isRemoval+"',"
    	 							+ "pk_country_master.get_currency_code('"+countryCode+"'),"
    	 							+ "'"+customerAddress+"',null,'"+city+"',"
    	 							+ "'"+customerContactNumber+"','"+customerEmail+"',"
    	 							+ "'"+empCode+"','"+empCode+"','NEW',"
    	 							+ "'Created by using pixcelcrayon webservice.','CUSTOMER')";
    	 
    	 System.out.println(stmt);
    	 try {
             statement.executeQuery(stmt);
             dbConnection.commit();
             fae.setCode(Constant.HTTP_SUCCESS);
             fae.setMessage("Success. Soon you will get confirmation from ITC team through eMail. Ref ID : "+customerCode);
         }
		catch(Exception er)
		{
			System.out.println("Error while inserting into customer_for_approval "+er.toString());
			fae.setMessage("Error while inserting into customer_for_approval : "+er.toString());
           return fae;
		}    	 
    	 return fae;
	   }
     public ResponseEntity forgotPassword(String userID)
     {
    	 ResponseEntity fae = new ResponseEntity();
    	 fae.setCode(Constant.HTTP_USER_EXCEPTION);
    	 fae.setMessage("");
    	 String stmt ="";
    	 String customerCode = "";
    	 String empCode ="";
    	 String empName ="";
    	 if (userID != null && userID.length() > 0)
    	 {
    		 stmt = "select name,code from employee_master em "
    		 		+ " where upper(email) = '"+userID.toUpperCase()+"'";
	         System.out.println(stmt);
	         try
	         {
	                 resultset = statement.executeQuery(stmt);                
	                 resultset.next();  
	                 if (resultset.getRow() == 0)
	                 {
	                	 fae.setMessage("User Name ("+userID+") does not exist");
	                 	 return fae;
	                 }   
	                 else
	                 {
	                	 empCode = resultset.getString("code");
	                 	  empName = resultset.getString("name");
	                 }
	         }
	         catch(SQLException er)
	         {
	                 System.out.println("Error while selecting employee master"+er.toString());
	                 fae.setMessage("Error while selecting employee master - "+er.toString());
	                 return fae;
	         }
    	 }
    	 if (empCode == null || "".equals(empCode))
    	 {
    		 fae.setMessage("User ID ("+userID+") does not exist");
    		 return fae;
    	 }
    	String password = "",companyCode ="",branchCode = "",locationCode = "";
    	try
        {    	stmt = "select aub.company_code,aub.branch_code,aub.location_code,aum.password "
			        		+ "from adm_employee_detail aed,adm_user_branch aub,"
			    			+ " adm_user_master aum"
			    			+ " where aum.name = aed.user_name"			    			
			    			+ " and aed.employee_code ='"+empCode+"'"
			    		    + " and aub.user_name = aum.name"
			    		    + " and aub.is_Default ='Y'"
			    		    + " AND aum.password  IS NOT NULL"; 
        System.out.println(stmt);
                resultset = statement.executeQuery(stmt);                
                resultset.next();  
                if (resultset.getRow() == 0 || resultset.getString("password").equals(""))
                {
                	fae.setMessage("Password not found for this employee ("+empCode+").");
                	 return fae;
                }
                else 
                {
                	password = resultset.getString("password") ;
                	companyCode = resultset.getString("company_code") ;
                	branchCode = resultset.getString("branch_code") ;	
               		locationCode = resultset.getString("location_code") ;
               		String messageBody = "<h1><span style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\">Dear "+empName+",</span><br style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\" />"
               				+ "<span style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\">Your password is "+password+". Please change your password on your login.</span><br style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\" />"
               				+ "<span style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\">Kind regards,</span><br style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\" />"
               				+ "<span style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\">Newage Software Zone</span><br style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\" />"
               				+ "<br style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\" />"
               				+ "<b style=\"color: rgb(34, 34, 34); font-family: arial, sans-serif; font-size: 12.8px; line-height: normal;\">*** PLEASE DO NOT REPLY TO THIS MESSAGE ***</b></h1>";
               		stmt = "{call pk_mail.send_mail("
               				+ "'"+companyCode+"',"
               				+ "'"+branchCode+"', "
               				+ "'"+locationCode+"',"
               				+ "'itc@newage-global.com', "
               				+ "'"+userID+"', "
               				+ "'Newage Software Zone - Password Resend',"
               				+ "'"+messageBody+"')}";
               		System.out.println(stmt);
               		try
               		{
               		CallableStatement csmt=dbConnection.prepareCall(stmt);  
               		csmt.execute();
               		fae.setCode(Constant.HTTP_SUCCESS);
               		fae.setMessage("Success...Password has been sent to your eMail ID "+userID); 
               		}
               		catch(Exception er)
               		{
               			System.out.println("Error while sending mail "+er.toString());
                        fae.setMessage("Error while sending mail - "+er.toString());
                        return fae;
               		}
                }
               
        }
        catch(SQLException er)
        {
                System.out.println("Error while selecting employee password "+er.toString());
                fae.setMessage("Error while selecting country master - "+er.toString());
                return fae;
        }
    	 return fae;
	   }
}
