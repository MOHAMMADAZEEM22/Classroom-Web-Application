<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Assignment</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { color: #4CAF50; }
        input[type="file"], textarea, select, input[type="submit"] {
            width: 100%; padding: 10px; margin: 10px 0;
            border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }
        textarea { height: 100px; }
        input[type="submit"] {
            background-color: #4CAF50; color: white; cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Submit Assignment</h2>

        <form action="submitAssignmentServlet" method="POST" enctype="multipart/form-data">
            <label for="assignmentId">Select Assignment</label>
            <select id="assignmentId" name="assignmentId" required>
                <option value="">-- Select Assignment --</option>
                <%
                    HttpSession ses = request.getSession(false);
                    if (ses == null || ses.getAttribute("userId") == null) {
                        out.println("<option disabled>No assignments available</option>");
                    } else {
                        int sid = (Integer) ses.getAttribute("userId");

                        // Database connection to get assignments related to student's enrolled courses
                        Connection conn = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                            // Query to fetch assignments based on student's enrolled courses
                            String sql = "SELECT a.AssignmentID, a.AssignmentName FROM Assignments a " +
                                         "JOIN Enrollments e ON a.CourseID = e.CourseID " +
                                         "WHERE e.SID = ?";
                            ps = conn.prepareStatement(sql);
                            ps.setInt(1, sid);
                            rs = ps.executeQuery();

                            // Populate the dropdown with assignment IDs and names
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
                    }
                %>
            </select>

            <label for="submissionFile">Upload Assignment</label>
            <input type="file" id="submissionFile" name="submissionFile" accept=".txt,.pdf,.doc,.docx" required>

            <label for="comments">Additional Comments</label>
            <textarea id="comments" name="comments" placeholder="Enter any additional comments (optional)"></textarea>

            <input type="submit" value="Submit Assignment">
        </form>
    </div>
</body>
</html>
