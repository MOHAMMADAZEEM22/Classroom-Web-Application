package com.nt.student;
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
import jakarta.servlet.http.HttpSession;

@WebServlet("/EnrollCourseServlet")
public class EnrollCourseServlet extends HttpServlet {
   

    // Handles both GET and POST requests
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer studentId = (Integer) session.getAttribute("userId"); // Assuming student ID is stored in session
        PrintWriter pw = res.getWriter();

        if (studentId == null) {
            res.sendRedirect("login.jsp"); // Redirect to login if session is invalid
            return;
        }

        String courseID = req.getParameter("courseID");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Database connection setup (replace with actual connection details)
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

            // Fetch the next value from the sequence
            String sqlSeq = "SELECT enrollment_seq.NEXTVAL AS nextVal FROM dual";
            ps = conn.prepareStatement(sqlSeq);
            rs = ps.executeQuery();
            int enrollmentId = 0;
            if (rs.next()) {
                enrollmentId = rs.getInt("nextVal");
            }

            // Insert the new enrollment record
            String sqlInsert = "INSERT INTO Enrollments (EnrollmentID, SID, CourseID) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sqlInsert);
            ps.setInt(1, enrollmentId);
            ps.setInt(2, studentId);
            ps.setInt(3, Integer.parseInt(courseID));
            int rowsInserted = ps.executeUpdate();
            
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Enrollment Success</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            pw.println(".container { max-width: 600px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            pw.println("h1 { color: #4CAF50; }");
            pw.println("p { font-size: 18px; color: #555; }");
            pw.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; margin-right: 10px; }");
            pw.println(".button:hover { background: #45a049; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='container'>");

            if (rowsInserted > 0) {
            	pw.println("<h1>Enrollment Successful!</h1>");
                pw.println("<p>You have been successfully enrolled in the selected course.</p>");
                pw.println("<a href='enrollInCourse.jsp' class='button'>Enroll in Another Course</a>");
                pw.println("<a href='dashboard' class='button'>Go to Dashboard</a>");
            } else {
            	 pw.println("<h1>Enrollment Failed</h1>");
                 pw.println("<p>There was an issue with your enrollment. Please try again.</p>");
                 pw.println("<a href='enrollInCourse.jsp' class='button'>Retry Enrollment</a>");
             }
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.jsp");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Calls doGet to handle POST requests
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
