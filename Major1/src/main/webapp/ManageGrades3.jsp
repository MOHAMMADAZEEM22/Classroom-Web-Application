<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Grades</title>
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
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        td {
            background-color: #f9f9f9;
        }
        a {
            color: #3498db;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        input[type="number"], textarea {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
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
                int teacherId = (Integer) session.getAttribute("userId");

                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                    // Fetch submissions that haven't been graded yet
                    String sql = "SELECT s.SID, s.AssignmentID, s.FilePath FROM Submissions s " +
                                 "JOIN Assignments a ON s.AssignmentID = a.AssignmentID " +
                                 "JOIN Courses c ON a.CourseID = c.CourseID " +
                                 "LEFT JOIN Grades g ON s.SID = g.SID AND s.AssignmentID = g.AssignmentID " +
                                 "WHERE c.TID = ? AND g.GradeID IS NULL";
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
                        out.println("<td><a href='" + request.getContextPath() + "/" + filePath + "' target='_blank'>View File</a></td>");
                        out.println("<form action='GradeAssignmenttServlet' method='POST'>");
                        out.println("<td><input type='number' name='grade' min='1' max='10' required></td>");
                        out.println("<td><textarea name='feedback' required></textarea></td>");
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
