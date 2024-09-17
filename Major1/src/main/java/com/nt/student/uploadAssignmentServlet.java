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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/uploadAssignmentServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,  // 1MB threshold
                 maxFileSize = 1024 * 1024 * 10,   // 10MB max size
                 maxRequestSize = 1024 * 1024 * 50 // 50MB total
)
public class uploadAssignmentServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploadedFiles";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int studentId = (Integer) session.getAttribute("userId");  // Student ID from session
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        
        // Handle file upload
        Part filePart = request.getPart("file");
        String fileName = extractFileName(filePart);
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        // Ensure the directory exists
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Save the file on the server
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Save file info into the database
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "AZEEM", "azeem")) {
            String sql = "INSERT INTO Submissions (SubmissionID, SID, AssignmentID, FilePath) " +
                         "VALUES (submission_seq.nextval, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, assignmentId);
            ps.setString(3, filePath);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("submissionSuccess2.jsp");
            } else {
                response.sendRedirect("submissionError.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }

    // Helper method to extract file name
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
