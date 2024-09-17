package com.nt.teacher;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GradeAssignmenttServlet")
public class GradeAssignmentServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve student and assignment details from form
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int grade = Integer.parseInt(request.getParameter("grade"));
        String feedback = request.getParameter("feedback");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Connect to the database
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

            // Insert grade details into the Grades table
            String sql = "INSERT INTO Grades (GradeID, SID, AssignmentID, Grade, Feedback) VALUES (grade_seq.nextval, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, assignmentId);
            ps.setInt(3, grade);
            ps.setString(4, feedback);

            // Execute the query
            ps.executeUpdate();

            // Redirect the teacher to a success page or refresh the manage grades page
            response.sendRedirect("gradeSuccess.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            // In case of an error, redirect to an error page
            response.sendRedirect("gradeError.jsp");
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
        }
    }
}
