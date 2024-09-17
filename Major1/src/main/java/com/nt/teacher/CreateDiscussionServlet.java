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
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreateDiscussionServlet")
public class CreateDiscussionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user (teacher) ID from session
        HttpSession session = request.getSession();
        int teacherId = (Integer) session.getAttribute("userId");

        // Get course ID and topic from the form
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String topic = request.getParameter("topic");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

            // Insert the discussion into the Discussion table
            String sql = "INSERT INTO Discussions (DiscussionID, CourseID, Topic, CreatedBy) VALUES (discussion_seq.NEXTVAL, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, topic);
            ps.setInt(3, teacherId);
            ps.executeUpdate();

            // Redirect to a success page
            response.sendRedirect("discussionSuccess.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
        }
    }
}
