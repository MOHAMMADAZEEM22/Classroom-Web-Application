package com.nt.register;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class register_Teacher extends HttpServlet {
	private static final String REGISTER_QUERY="INSERT INTO TEACHERS VALUES(?,?)";	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter pw =res.getWriter();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try(
				Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","AZEEM","azeem");
				PreparedStatement ps=conn.prepareStatement(REGISTER_QUERY)){
			int no=Integer.parseInt(req.getParameter("teacher-id"));
			String pass=req.getParameter("teacher-password");
			ps.setInt(1, no);
			ps.setString(2, pass);
		       int count=ps.executeUpdate();
				if(count==1) {
//					pw.println("<h1>Registration Successful </h1>");
//					
//					String url="index.html";
//					res.sendRedirect(url);
					
					
					pw.println("<!DOCTYPE html>");
			        pw.println("<html>");
			        pw.println("<head>");
			        pw.println("<title>Registration Successful</title>");
			        pw.println("<style>");
			        pw.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
			        pw.println(".container { max-width: 600px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
			        pw.println("h1 { color: #4CAF50; }");
			        pw.println("p { font-size: 18px; color: #555; }");
			        pw.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; }");
			        pw.println(".button:hover { background: #45a049; }");
			        pw.println("</style>");
			        pw.println("</head>");
			        pw.println("<body>");
			        pw.println("<div class='container'>");
			        pw.println("<h1>Registration Successful!</h1>");
			        pw.println("<p>Thank you for registering. Your account has been created successfully.</p>");
			        pw.println("<a href='index.html' class='button'>Go to Login</a>");
			        pw.println("</div>");
			        pw.println("</body>");
			        pw.println("</html>");
					
					
					
					
					
					
					
				}else {
					pw.println("<h1>Registration failed....... enter values in correct format </h1>");
				}
				
			}
			
//		catch(IOException ioe) {
//			ioe.printStackTrace();
//		}
		catch(java.sql.SQLIntegrityConstraintViolationException soe) {
		//	pw.println("<h1>Registration failed....... TID cannot be taken  and reprompt this  registration page again here </h1>");
			
			
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<title>Registration Failed</title>");
			pw.println("<style>");
			pw.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
			pw.println(".container { max-width: 600px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
			pw.println("h1 { color: #FF0000; }");
			pw.println("p { font-size: 18px; color: #555; }");
			pw.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #FF0000; text-decoration: none; border-radius: 5px; }");
			pw.println(".button:hover { background: #D40000; }");
			pw.println("</style>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<div class='container'>");
			pw.println("<h1>Registration Failed</h1>");
			pw.println("<p>The TID you entered is already taken. Please try using a different TID and password.</p>");
			pw.println("<a href='teacher_register.jsp' class='button'>Try Again</a>");
			pw.println("</div>");
			pw.println("</body>");
			pw.println("</html>");

			
			
		}
		catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
					e.printStackTrace();
		}
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	
	}

}
