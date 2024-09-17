<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Assignment</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        label, select, input[type="file"], input[type="submit"] {
            display: block;
            width: 100%; padding: 10px; margin: 10px 0;
        }
        input[type="submit"] { background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        input[type="submit"]:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Submit Assignment</h2>
        <form action="uploadAssignmentServlet" method="POST" enctype="multipart/form-data">
            <label for="assignment">Select Assignment:</label>
            <select name="assignmentId" id="assignmentId" required>
                <option value="">--Select Assignment--</option>
                <%
                    int studentId = (Integer) session.getAttribute("userId");
                    Connection conn = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;

                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                        // Query to get assignments from the student's enrolled courses
                        String sql = "SELECT a.AssignmentID, a.AssignmentName " +
                                     "FROM Assignments a " +
                                     "JOIN Enrollments e ON a.CourseID = e.CourseID " +
                                     "WHERE e.SID = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, studentId);
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            int assignmentId = rs.getInt("AssignmentID");
                            String assignmentName = rs.getString("AssignmentName");
                            out.println("<option value='" + assignmentId + "'>" + assignmentName + "</option>");
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

            <label for="file">Upload Assignment:</label>
            <input type="file" name="file" id="file" required>

            <input type="submit" value="Submit Assignment">
        </form>
    </div>
</body>
</html>
