
<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Grades</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        input[type="number"], textarea, input[type="submit"] {
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
        <h2>Manage Grades</h2>
        <table>
            <tr>
                <th>Student ID</th>
                <th>Assignment ID</th>
                <th>Submitted File</th>
                <th>Grade</th>
                <th>Feedback</th>
                <th>Action</th>
            </tr>
            <%
                // Teacher ID from session (you should have a mechanism for getting the teacher's session)
                int teacherId = (Integer) request.getSession().getAttribute("userId");

                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                    // Query to fetch submitted assignments of students registered in teacher's courses
                    String sql = "SELECT s.SID, s.AssignmentID, s.FilePath FROM Submissions s " +
                                 "JOIN Assignments a ON s.AssignmentID = a.AssignmentID " +
                                 "JOIN Courses c ON a.CourseID = c.CourseID " +
                                 "WHERE c.TID = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, teacherId);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int studentId = rs.getInt("SID");
                        int assignmentId = rs.getInt("AssignmentID");
                        String filePath = rs.getString("FilePath");

                        out.println("<tr>");
                        out.println("<td>" + studentId + "</td>");
                        out.println("<td>" + assignmentId + "</td>");
                        out.println("<td><a href='" + filePath + "' target='_blank'>View File</a></td>");
                        out.println("<form action='gradeAssignmentServlet' method='POST'>");
                        out.println("<td><input type='number' name='grade' min='1' max='10' required></td>");
                        out.println("<td><textarea name='feedback' placeholder='Write feedback here...' required></textarea></td>");
                        out.println("<td>");
                        out.println("<input type='hidden' name='studentId' value='" + studentId + "'>");
                        out.println("<input type='hidden' name='assignmentId' value='" + assignmentId + "'>");
                        out.println("<input type='submit' value='Submit Grade'>");
                        out.println("</td>");
                        out.println("</form>");
                        out.println("</tr>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                }
            %>
        </table>
    </div>
</body>
</html>
