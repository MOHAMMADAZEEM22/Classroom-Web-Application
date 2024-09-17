package com.nt.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/viewFile")
public class FileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the file name from the request
        String fileName = request.getParameter("fileName");

        // Set the directory where files are stored
        String filePath = "D:/projectfiles/" + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            response.getWriter().println("File not found");
            return;
        }

        // Set content type to the correct MIME type of the file
        String mimeType = getServletContext().getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // Set the content disposition for inline viewing
        response.setHeader("Content-Disposition", "inline;filename=" + fileName);

        // Stream the file to the client
        try (FileInputStream inStream = new FileInputStream(file); 
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
