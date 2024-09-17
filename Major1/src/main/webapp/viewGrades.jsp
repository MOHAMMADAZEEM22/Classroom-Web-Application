<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Grades</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
        }
        table, th, td {
            border: 1px solid #ccc;
            padding: 10px;
        }
        th {
            background-color: #4CAF50;
            color: white;
            text-align: center;
        }
        td {
            text-align: center;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Your Grades</h2>
    <table>
        <tr>
            <th>Student ID</th>
            <th>Assignment ID</th>
            <th>Grade</th>
            <th>Feedback</th>
        </tr>
        <%
            // Fetch student ID from session
            int studentId = (Integer) session.getAttribute("userId");

            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                // Fetch grades and feedback for the student
                String sql = "SELECT g.AssignmentID, g.Grade, g.Feedback " +
                             "FROM Grades g " +
                             "WHERE g.SID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentId);
                rs = ps.executeQuery();

                while (rs.next()) {
                    int assignmentId = rs.getInt("AssignmentID");
                    int grade = rs.getInt("Grade");
                    String feedback = rs.getString("Feedback");

                    out.println("<tr>");
                    out.println("<td>" + studentId + "</td>");
                    out.println("<td>" + assignmentId + "</td>");
                    out.println("<td>" + grade + "</td>");
                    out.println("<td>" + feedback + "</td>");
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
</body>
</html>
