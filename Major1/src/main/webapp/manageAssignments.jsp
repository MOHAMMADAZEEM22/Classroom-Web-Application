<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Assignments</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { color: #4CAF50; }
        input[type="text"], textarea, input[type="submit"], input[type="button"], select {
            width: 100%; padding: 10px; margin: 10px 0;
            border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }
        textarea { height: 100px; }
        input[type="submit"], input[type="button"] {
            background-color: #4CAF50; color: white; cursor: pointer;
        }
        input[type="submit"]:hover, input[type="button"]:hover {
            background-color: #45a049;
        }
        .button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; }
        .button:hover { background: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Manage Assignments</h2>
        <form action="ManageAssignmentsServlet" method="POST">
            <input type="hidden" name="action" value="create">

            <label for="assignmentId">Assignment ID</label>
            <input type="text" id="assignmentId" name="assignmentId" placeholder="Enter Assignment ID" required>

            <label for="assignmentName">Assignment Name</label>
            <input type="text" id="assignmentName" name="assignmentName" placeholder="Enter Assignment Name" required>

            <label for="courseId">Course ID</label>
            <select id="courseId" name="courseId" required>
                <% 
                    // JDBC connection and query
                    String dbUrl = "jdbc:oracle:thin:@localhost:1521:ORCL";
                    String dbUser = "AZEEM";
                    String dbPass = "azeem";
                    Connection conn = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    
                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                        String sql = "SELECT CourseID, CourseName FROM Courses"; // Modify SQL as needed
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                        
                        while (rs.next()) {
                            int courseId = rs.getInt("CourseID");
                            String courseName = rs.getString("CourseName");
                %>
                            <option value="<%= courseId %>"><%= courseName %></option>
                <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (conn != null) conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                %>
            </select>

            <input type="submit" value="Create Assignment">
        </form>

        <br>

        <form action="ManageAssignmentsServlet" method="POST">
            <input type="hidden" name="action" value="view">
            <input type="submit" value="View All Assignments">
        </form>
        
        <br>
        <a href="dashboard" class="button">Go to Teacher Dashboard</a>
    </div>
</body>
</html>
