package com.nt.dashboard;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashBoard extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Set content type
        res.setContentType("text/html");

        // Get session and session attribute
        HttpSession ses = req.getSession(false);
        String userRole = (String) ses.getAttribute("user");

        // Get PrintWriter to send response
        PrintWriter pw = res.getWriter();

        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Classroom Dashboard</title>");
        pw.println("<style>");
        pw.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
        pw.println(".container { max-width: 800px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        pw.println("h1 { color: #4CAF50; }");
        pw.println("p { font-size: 18px; color: #555; }");
        pw.println(".button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background: #4CAF50; text-decoration: none; border-radius: 5px; margin-right: 10px; }");
        pw.println(".button:hover { background: #45a049; }");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class='container'>");

        // Check if the session attribute 'user' is teacher or student and display accordingly
        if (userRole != null && userRole.equalsIgnoreCase("teacher")) {
            pw.println("<h1>Teacher Classroom Console</h1>");
            pw.println("<p>Welcome, Teacher! Here are your available actions:</p>");
            pw.println("<ul>");
            pw.println("<li><a href='createCourse.jsp' class='button'>Create Course</a></li>");
            pw.println("<li><a href='manageAssignments.jsp' class='button'>Manage Assignments</a></li>");
            pw.println("<li><a href='ManageGrades3.jsp' class='button'>Manage Grades</a></li>");
            pw.println("<li><a href='ManageDiscussion.jsp' class='button'>Manage Discussions</a></li>");
            // pw.println("<li><a href='CalendarServlet' class='button'>Manage Course Calendar</a></li>");
            pw.println("</ul>");
        } else if (userRole != null && userRole.equalsIgnoreCase("student")) {
            pw.println("<h1>Student Classroom Console</h1>");
            pw.println("<p>Welcome, Student! Here are your available actions:</p>");
            pw.println("<ul>");
            pw.println("<li><a href='enrollInCourse.jsp' class='button'>Enroll in Course</a></li>");
            pw.println("<li><a href='submitAssignment3.jsp' class='button'>Submit Assignments</a></li>");
            pw.println("<li><a href='viewGrades.jsp' class='button'>View Grades</a></li>");
            pw.println("<li><a href='ParticipateinDiscussion.jsp' class='button'>Participate in Discussions</a></li>");
          //  pw.println("<li><a href='ViewCalendarServlet' class='button'>View Course Calendar</a></li>");
            pw.println("</ul>");
        } else {
            // If session does not have a valid role, prompt for login
            pw.println("<h1>Error</h1>");
            pw.println("<p>Invalid session or no role assigned. Please <a href='login.html'>login</a> again.</p>");
        }

        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");

        // Close the writer
        pw.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // You can handle any POST requests here if needed
        doGet(req, res);  // For now, redirect POST requests to doGet
    }
}
