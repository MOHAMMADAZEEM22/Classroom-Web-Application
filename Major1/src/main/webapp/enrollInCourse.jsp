<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enroll in Course</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h3 { color: #4CAF50; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; }
        select, .button { padding: 10px; font-size: 16px; width: 100%; }
        .button { background: #4CAF50; color: #fff; border: none; border-radius: 5px; cursor: pointer; }
        .button:hover { background: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h3>Enroll in Course</h3>
        <form action="EnrollCourseServlet" method="post">
            <div class="form-group">
                <label for="course">Select Course:</label>
                <select id="course" name="courseID" required>
                    <option value="">-- Select a Course --</option>
                    <% 
                        // Fetch available courses from the database
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        try {
                            // Database connection setup (replace with actual connection details)
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

                            String sql = "SELECT CourseID, CourseName FROM Courses";
                            stmt = conn.prepareStatement(sql);
                            rs = stmt.executeQuery();

                            while (rs.next()) {
                                int courseId = rs.getInt("CourseID");
                                String courseName = rs.getString("CourseName");
                    %>
                                <option value="<%= courseId %>"><%= courseName %></option>
                    <% 
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                    %>
                            <option disabled>Error loading courses</option>
                    <% 
                        } finally {
                            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                        }
                    %>
                </select>
            </div>
            <button type="submit" class="button">Enroll</button>
        </form>
    </div>
</body>
</html>
