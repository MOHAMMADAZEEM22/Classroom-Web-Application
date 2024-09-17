package com.nt.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get student ID (sender) from session
        HttpSession session = request.getSession();
        int senderId = (Integer) session.getAttribute("userId");

        // Get discussion ID and message content from the form
        int discussionId = Integer.parseInt(request.getParameter("discussionId"));
        String messageContent = request.getParameter("messageContent");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

            // Insert the message into the Messages table
            String sql = "INSERT INTO Messages (MessageID, DiscussionID, SenderID, MessageContent, SentDate) " +
                         "VALUES (message_seq.NEXTVAL, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, discussionId);
            ps.setInt(2, senderId);
            ps.setString(3, messageContent);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));  // Set current timestamp as SentDate
            ps.executeUpdate();

            // Redirect back to the discussion page with the selected discussion
            response.sendRedirect("ParticipateDiscussion.jsp?discussionId=" + discussionId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
        }
    }
}
