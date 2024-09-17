<%@ page import="java.sql.*, jakarta.servlet.http.*, jakarta.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Discussion</title>
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
        select, input[type="text"], input[type="submit"] {
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
        <h2>Create a New Discussion</h2>
        <form action="CreateDiscussionServlet" method="POST">
            
            <div class="form-group">
                <label for="course">Select Course:</label>
                <select name="courseId" required>
                    <%
                        // Fetch teacher ID from session
                        int teacherId = (Integer) session.getAttribute("userId");

                        Connection conn = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;

                        try {
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                            // Fetch courses created by the teacher
                            String sql = "SELECT CourseID, CourseName FROM Courses WHERE TID = ?";
                            ps = conn.prepareStatement(sql);
                            ps.setInt(1, teacherId);
                            rs = ps.executeQuery();

                            while (rs.next()) {
                                int courseId = rs.getInt("CourseID");
                                String courseName = rs.getString("CourseName");
                                out.println("<option value='" + courseId + "'>" + courseName + "</option>");
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
                <label for="topic">Discussion Topic:</label>
                <input type="text" name="topic" placeholder="Enter topic to discuss" required>
            </div>

            <!-- CreatedBy will be taken from session as TeacherID -->
            <input type="submit" value="Create Discussion">
        </form>
    </div>
</body>
</html>
