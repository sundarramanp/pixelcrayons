package com.pixelcrayons.controler;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
public class HomeServlet extends HttpServlet
{
   @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException 
   {
	// TODO Auto-generated method stub
	//super.doGet(req, resp);
	RequestDispatcher rd = getServletContext().getRequestDispatcher("/HTML/home.html");
	
	rd.forward(req, resp);
	
   }
 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
    {		
	 String userName =(String)req.getParameter("username");
		String passWord =(String)req.getParameter("password");
	 System.out.println("asdf");	
		
	}
}
