package com.nt.student;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;



@WebServlet("/submitAssignmentServlet")
@MultipartConfig
public class submitAssignmentServlet extends HttpServlet {

    // Define the directory where files will be uploaded
    private static final String UPLOAD_DIRECTORY = "D:\\projectfiles"; // Change this to your desired directory

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Get student ID from session
        int sid = (Integer) req.getSession().getAttribute("userId");
        String assignmentId = req.getParameter("assignmentId");
        String comments = req.getParameter("comments");

        // Ensure the upload directory exists
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Get the uploaded file part
        Part filePart = req.getPart("submissionFile");
        String fileName = filePart.getSubmittedFileName();

        // Define the full file path where the file will be stored
        String filePath = UPLOAD_DIRECTORY + File.separator + fileName;

        // Save the file on the server
        filePart.write(filePath);

        // Save the file path and submission details to the database
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem")) {
            String sql = "INSERT INTO Submissions (SubmissionID, SID, AssignmentID, FilePath) VALUES (submission_seq.nextval, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2, assignmentId);
            ps.setString(3, filePath);

            int result = ps.executeUpdate();
            if (result > 0) {
               
                res.sendRedirect("submissionSuccess.jsp");
            } else {
            	 res.sendRedirect("submissionError.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
