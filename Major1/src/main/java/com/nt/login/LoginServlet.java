package com.nt.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final String LOGIN_QUERY = "SELECT COUNT(*) FROM TEACHERS WHERE TID=? AND PASSWORD=?";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpSession ses = req.getSession();

        try (PrintWriter pw = res.getWriter();
             Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");
             PreparedStatement ps = conn.prepareStatement(LOGIN_QUERY)) {

            int no = Integer.parseInt(req.getParameter("teacher-id"));
            ps.setInt(1, no);
            String pass = req.getParameter("teacher-password");
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                if (rs != null) {
                    rs.next();
                    count = rs.getInt(1);
                }

                if (count == 0) {
                    pw.println("<!DOCTYPE html>");
                    pw.println("<html>");
                    pw.println("<head>");
                    pw.println("<title>Login Failed</title>");
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
                    pw.println("<h1>Invalid Credentials</h1>");
                    pw.println("<p>The credentials you entered are not valid. Please try logging in with the correct username and password.</p>");
                    pw.println("<a href='index.html' class='button'>Try Again</a>");
                    pw.println("</div>");
                    pw.println("</body>");
                    pw.println("</html>");
                } else {
                    // Store userId and password in session attributes
                    ses.setAttribute("userId", no);
                    ses.setAttribute("password", pass);
                    ses.setAttribute("user", "teacher");

                    // Redirect to dashboard
                    res.sendRedirect("dashboard");
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}


























//package com.nt.login;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//public class LoginServlet extends HttpServlet {
//	private static final String LOGIN_QUERY="SELECT COUNT(*) FROM TEACHERS WHERE TID=? AND PASSWORD=?";	
//	@Override
//	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		
//		res.setContentType("text/html");
//		try{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		HttpSession ses=req.getSession();
//		ses.setAttribute("user", "teacher");
//		try(PrintWriter pw =res.getWriter();
//				Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","AZEEM","azeem");
//				PreparedStatement ps=conn.prepareStatement(LOGIN_QUERY)){
//			int no=Integer.parseInt(req.getParameter("teacher-id"));
//			ps.setInt(1, no);
//			String pass=req.getParameter("teacher-password");
//			ps.setString(2, pass);
//			try(ResultSet rs = ps.executeQuery()){
//				int count=0;
//				if(rs!=null) {
//					rs.next();
//					 count=rs.getInt(1);
//				}
//				if(count==0) {
//					//pw.println("<h1>Login failed You do not have an account... here make connection to go to register page </h1>");
//					
//					
//					pw.println("<!DOCTYPE html>");
//			        pw.println("<html>");
//			        pw.println("<head>");
//			        pw.println("<title>Login Failed</title>");
//			        pw.println("<style>");
//			        pw.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
//			        pw.println(".container { max-width: 600px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
//			        pw.println("h1 { color: #FF0000; }");
//			        pw.println("p { font-size: 18px; color: #555; }");
//			        pw.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #FF0000; text-decoration: none; border-radius: 5px; }");
//			        pw.println(".button:hover { background: #D40000; }");
//			        pw.println("</style>");
//			        pw.println("</head>");
//			        pw.println("<body>");
//			        pw.println("<div class='container'>");
//			        pw.println("<h1>Invalid Credentials</h1>");
//			        pw.println("<p>The credentials you entered are not valid. Please try logging in with the correct username and password.</p>");
//			        pw.println("<a href='index.html' class='button'>Try Again</a>");
//			        pw.println("</div>");
//			        pw.println("</body>");
//			        pw.println("</html>");
//					
//					
//				}else {
//				//	pw.println("<h1>Login success ...here make connection to go to teacher Dashboard</h1>");
//					res.sendRedirect("dashboard");
//				}
//				
//			}
//			
//		}catch(IOException ioe) {
//			ioe.printStackTrace();
//		}
//		catch(SQLException se) {
//			se.printStackTrace();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	@Override
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		doGet(req,res);
//	
//	}
//
//}
