package com.nt.teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageAssignmentsServlet")
public class ManageAssignmentsServlet extends HttpServlet {
   

    // JDBC credentials
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // Update with your DB details
    private static final String DB_USER = "AZEEM";
    private static final String DB_PASS = "azeem";
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("create".equalsIgnoreCase(action)) {
            createAssignment(req, res);
        } else if ("view".equalsIgnoreCase(action)) {
            viewAssignments(req, res);
        } else {
            res.sendRedirect("manageAssignments.jsp");
        }
    }

    private void createAssignment(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int assignmentId = Integer.parseInt(req.getParameter("assignmentId"));
        String assignmentName = req.getParameter("assignmentName");
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO Assignments (AssignmentID, AssignmentName, CourseID) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, assignmentId);
            pstmt.setString(2, assignmentName);
            pstmt.setInt(3, courseId);

            int rowsInserted = pstmt.executeUpdate();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Assignment Status</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }");
            out.println(".container { max-width: 600px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center; }");
            out.println("h3 { color: #4CAF50; margin-bottom: 20px; }");
            out.println(".button { display: inline-block; padding: 12px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; margin: 5px; }");
            out.println(".button:hover { background: #45a049; }");
            out.println(".error { color: #f44336; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            if (rowsInserted > 0) {
                out.println("<h3>Assignment created successfully!</h3>");
                out.println("<a href='manageAssignments.jsp' class='button'>Create Another Assignment</a>");
                out.println("<br><br>");
                out.println("<a href='dashboard' class='button'>Go to Teacher Dashboard</a>");
            } else {
                out.println("<h3 class='error'>Failed to create assignment. Try again.</h3>");
                out.println("<a href='manageAssignments.jsp' class='button'>Retry</a>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head><title>Assignment Created</title></head>");
//            out.println("<body>");
//            if (rowsInserted > 0) {
//                out.println("<h3>Assignment created successfully!</h3>");
//                out.println("<a href='manageAssignments.jsp' class='button'>Create Another Assignment</a>");
//                out.println("<br><br>");
//                out.println("<a href='dashboard' class='button'>Go to Teacher Dashboard</a>");
//            } else {
//                out.println("<h3>Failed to create assignment. Try again.</h3>");
//                out.println("<a href='manageAssignments.jsp' class='button'>Retry</a>");
//            }
//            out.println("</body>");
//            out.println("</html>");
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewAssignments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT * FROM Assignments";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Assignments</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }");
            out.println(".container { max-width: 800px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("h3 { color: #4CAF50; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
            out.println("th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; }");
            out.println(".button:hover { background: #45a049; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h3>Assignments List</h3>");
            out.println("<table>");
            out.println("<tr><th>AssignmentID</th><th>AssignmentName</th><th>CourseID</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("AssignmentID") + "</td>");
                out.println("<td>" + rs.getString("AssignmentName") + "</td>");
                out.println("<td>" + rs.getInt("CourseID") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href='manageAssignments.jsp' class='button'>Back</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
