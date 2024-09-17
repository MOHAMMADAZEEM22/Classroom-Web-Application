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

@WebServlet("/gradeAssignmentServlett")
public class GradeAssignmentServlet2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int grade = Integer.parseInt(request.getParameter("grade"));
        String feedback = request.getParameter("feedback");

        // Database connection and insert grade
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem")) {
            String sql = "INSERT INTO Grades (gradeID, SID, AssignmentID, Grade, Feedback) " +
                         "VALUES (grade_seq.nextval, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, assignmentId);
            ps.setInt(3, grade);
            ps.setString(4, feedback);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("gradeSuccess.jsp");
            } else {
                response.sendRedirect("gradeError.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
