<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Assignment</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
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
        select, input[type="file"], input[type="submit"] {
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
    </style>
</head>
<body>
    <div class="container">
        <h2>Submit Assignment</h2>
        <form action="SubmittAssignmentServlet" method="POST" enctype="multipart/form-data">
            
            <div class="form-group">
                <label for="assignment">Choose Assignment:</label>
                <select name="assignmentId" required>
                    <%
                        // Fetch student ID from session
                        int studentId = (Integer) session.getAttribute("userId");

                        Connection conn = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;

                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                            // Fetch assignments related to the courses the student is enrolled in
                            String sql = "SELECT a.AssignmentID, a.AssignmentName FROM Assignments a " +
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
            </div>

            <div class="form-group">
                <label for="file">Upload File:</label>
                <input type="file" name="file" required>
            </div>

            <input type="submit" value="Submit Assignment">
        </form>
    </div>
</body>
</html>
