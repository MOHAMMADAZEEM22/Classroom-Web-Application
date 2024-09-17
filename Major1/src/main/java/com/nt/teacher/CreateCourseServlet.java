package com.nt.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreateCourseServlet")
public class CreateCourseServlet extends HttpServlet {

    // JDBC credentials
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // Update with your DB details
    private static final String DB_USER = "AZEEM";
    private static final String DB_PASS = "azeem";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get input data from form
        int courseId = Integer.parseInt(req.getParameter("courseId"));  // Input for CourseID
        String courseName = req.getParameter("courseName");
        String courseDescription = req.getParameter("courseDescription");

        // Retrieve TID from HttpSession
        HttpSession session = req.getSession(false);
        int tid = (Integer) session.getAttribute("userId"); // Ensure that you set "userId" in session during login

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // JDBC connection and prepared statement
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Load JDBC driver (if required, depending on your setup)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish database connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // SQL query for inserting into Courses table
            String sql = "INSERT INTO Courses (CourseID, CourseName, DESCRIPTION, TID) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            // Set parameters for CourseID, CourseName, CourseDescription, and TID
            ps.setInt(1, courseId);
            ps.setString(2, courseName);
            ps.setString(3, courseDescription);
            ps.setInt(4, tid);

            // Execute the query
            int rowsInserted = ps.executeUpdate();

            // Output success message
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Course Created</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
            out.println(".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("h3 { color: #4CAF50; }");
            out.println("a.button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; }");
            out.println("a.button:hover { background: #45a049; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            if (rowsInserted > 0) {
                out.println("<h3>Course created successfully!</h3>");
                out.println("<a href='createCourse.jsp' class='button'>Create Another Course</a>");
                out.println("<br><br>");
                out.println("<a href='dashboard' class='button'>Go to Teacher Dashboard</a>");
            } else {
                out.println("<h3>Failed to create course. Try again.</h3>");
                out.println("<a href='createCourse.jsp' class='button'>Retry</a>");
            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (java.sql.SQLIntegrityConstraintViolationException soe) {
            out.println("<html><body><h3>Enter your ID properly</h3>");
            out.println("<a href='createCourse.jsp' class='button' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px;'>BACK</a></body></html>");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<html><body><h3>Error occurred: " + e.getMessage() + "</h3></body></html>");
        } finally {
            // Close resources
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

































//package com.nt.teacher;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/CreateCourseServlet")
//public class CreateCourseServlet extends HttpServlet {
//    
//
//    // JDBC credentials
//    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // Update with your DB details
//    private static final String DB_USER = "AZEEM";
//    private static final String DB_PASS = "azeem";
//    
//    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        // Get input data from form
//        int courseId = Integer.parseInt(req.getParameter("courseId"));  // Input for CourseID
//        String courseName = req.getParameter("courseName");
//        String courseDescription = req.getParameter("courseDescription");
//        int tid = Integer.parseInt(req.getParameter("tid"));  // Teacher ID
//        
//        res.setContentType("text/html");
//        PrintWriter out = res.getWriter();
//        
//        // JDBC connection and prepared statement
//        Connection conn = null;
//        PreparedStatement ps = null;
//        
//        try {
//            // Load JDBC driver (if required, depending on your setup)
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            // Establish database connection
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//            // SQL query for inserting into Courses table
//            String sql = "INSERT INTO Courses (CourseID, CourseName, DESCRIPTION, TID) VALUES (?, ?, ?, ?)";
//            ps = conn.prepareStatement(sql);
//
//            // Set parameters for CourseID, CourseName, CourseDescription, and TID
//            ps.setInt(1, courseId);
//            ps.setString(2, courseName);
//            ps.setString(3, courseDescription);
//            ps.setInt(4, tid);
//
//            // Execute the query
//            int rowsInserted = ps.executeUpdate();
//            
//            // Output success message
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Course Created</title>");
//            out.println("<style>");
//            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
//            out.println(".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
//            out.println("h3 { color: #4CAF50; }");
//            out.println("a.button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; }");
//            out.println("a.button:hover { background: #45a049; }");
//            out.println("</style>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<div class='container'>");
//            if (rowsInserted > 0) {
//                out.println("<h3>Course created successfully!</h3>");
//                out.println("<a href='createCourse.jsp' class='button'>Create Another Course</a>");
//                out.println("<br><br>");
//                out.println("<a href='dashboard' class='button'>Go to Teacher Dashboard</a>");
//            } else {
//                out.println("<h3>Failed to create course. Try again.</h3>");
//                out.println("<a href='createCourse.jsp' class='button'>Retry</a>");
//            }
//            out.println("</div>");
//            out.println("</body>");
//            out.println("</html>");
//
//        }catch(java.sql.SQLIntegrityConstraintViolationException soe) {
//        	out.println("Enter your ID properly");
//        	out.println("<a href='createCourse.jsp' class='button' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px;'>BACK</a>");
//        }
//        catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            out.println("<html><body><h3>Error occurred: " + e.getMessage() + "</h3></body></html>");
//        } finally {
//            // Close resources
//            try {
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
