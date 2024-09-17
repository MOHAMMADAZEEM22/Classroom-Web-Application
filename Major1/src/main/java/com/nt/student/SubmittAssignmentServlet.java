package com.nt.student;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/SubmittAssignmentServlet")
@MultipartConfig
public class SubmittAssignmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get student ID from session
        HttpSession session = request.getSession();
        int studentId = (Integer) session.getAttribute("userId");

        // Get assignment ID from form
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));

        // Get uploaded file
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Define upload path
        String uploadPath = getServletContext().getRealPath("/") + "uploadedFiles" + File.separator + fileName;
        File uploads = new File(uploadPath);

        // Initialize JDBC objects
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem");

            // Check if the student has already submitted the assignment
            String checkSubmissionSQL = "SELECT COUNT(*) FROM Submissions WHERE SID = ? AND AssignmentID = ?";
            ps = conn.prepareStatement(checkSubmissionSQL);
            ps.setInt(1, studentId);
            ps.setInt(2, assignmentId);
            rs = ps.executeQuery();

            // If a record exists, inform the student and stop further submission
            if (rs.next() && rs.getInt(1) > 0) {
                // Redirect to an "already submitted" page or show a message
                response.sendRedirect("alreadySubmitted.jsp");
            } else {
                // Save the file to the server
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, uploads.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Insert submission details into the database
                String insertSQL = "INSERT INTO Submissions (SubmissionID, SID, AssignmentID, FilePath) " +
                                   "VALUES (submission_seq.nextval, ?, ?, ?)";
                ps = conn.prepareStatement(insertSQL);
                ps.setInt(1, studentId);
                ps.setInt(2, assignmentId);
                ps.setString(3, "uploadedFiles/" + fileName);
                ps.executeUpdate();

                // Redirect to success page or show success message
                response.sendRedirect("submissionSuccess2.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
        }
    }
}
