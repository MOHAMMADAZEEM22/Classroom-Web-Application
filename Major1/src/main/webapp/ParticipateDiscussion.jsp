<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Participate in Discussion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        label {
            font-weight: bold;
            color: #555;
        }
        select, textarea, input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .messages {
            margin-top: 30px;
        }
        .message {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
        .message strong {
            color: #333;
        }
        .message small {
            color: #888;
            float: right;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Participate in Discussion</h2>
        <form action="ParticipateDiscussion.jsp" method="GET">
            <div class="form-group">
                <label for="course">Select Course:</label>
                <select name="discussionId" onchange="this.form.submit()" required>
                    <option value="">Select a Course</option>
                    <%
                        // Fetch student ID from session
                        int studentId = (Integer) session.getAttribute("userId");

                        Connection conn = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;

                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                            // Fetch discussions related to courses the student is enrolled in
                            String sql = "SELECT d.DiscussionID, c.CourseName FROM Discussions d " +
                                         "JOIN Enrollments e ON d.CourseID = e.CourseID " +
                                         "JOIN Courses c ON d.CourseID = c.CourseID " +
                                         "WHERE e.SID = ?";
                            ps = conn.prepareStatement(sql);
                            ps.setInt(1, studentId);
                            rs = ps.executeQuery();

                            while (rs.next()) {
                                int discussionId = rs.getInt("DiscussionID");
                                String courseName = rs.getString("CourseName");
                                out.println("<option value='" + discussionId + "'>" + courseName + "</option>");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (conn != null) conn.close();
                        }
                    %>
                </select>
            </div>
        </form>

        <%
            String selectedDiscussionIdStr = request.getParameter("discussionId");
            if (selectedDiscussionIdStr != null && !selectedDiscussionIdStr.isEmpty()) {
                int selectedDiscussionId = Integer.parseInt(selectedDiscussionIdStr);
        %>
        <form action="SendMessageServlet" method="POST">
            <input type="hidden" name="discussionId" value="<%= selectedDiscussionId %>">
            <div class="form-group">
                <label for="message">Your Message:</label>
                <textarea name="messageContent" placeholder="Enter your message here" rows="4" required></textarea>
            </div>
            <input type="submit" value="Send Message">
        </form>

        <div class="messages">
            <h3>Messages in Discussion</h3>
            <%
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                    String sql = "SELECT m.MessageContent, m.SenderID, m.SentDate, u.UserName FROM Messages m " +
                                 "JOIN Users u ON m.SenderID = u.UserID " +
                                 "WHERE m.DiscussionID = ? ORDER BY m.SentDate ASC";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, selectedDiscussionId);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        String messageContent = rs.getString("MessageContent");
                        String senderName = rs.getString("UserName");
                        String sentDate = rs.getString("SentDate");

                        out.println("<div class='message'>");
                        out.println("<strong>" + senderName + ":</strong> " + messageContent);
                        out.println("<small>" + sentDate + "</small>");
                        out.println("</div>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                }
            %>
        </div>
        <%
            }
        %>
    </div>
</body>
</html>
