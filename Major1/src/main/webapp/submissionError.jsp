<!DOCTYPE html>
<html>
<head>
    <title>Assignment Submission Failed</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background-color: #f4f4f4; 
            padding: 20px; 
        }
        .container { 
            max-width: 600px; 
            margin: 0 auto; 
            padding: 20px; 
            background-color: white; 
            border-radius: 8px; 
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); 
            text-align: center;
        }
        h2 { 
            color: #e74c3c; /* Red for error message */
        }
        p {
            font-size: 18px;
            color: #333;
        }
        a.button { 
            display: inline-block; 
            padding: 10px 20px; 
            font-size: 16px; 
            color: white; 
            background-color: #e74c3c; /* Red button for retry */
            text-decoration: none; 
            border-radius: 5px; 
            margin: 10px;
        }
        a.button:hover { 
            background-color: #c0392b; 
        }
        a.button-alt { 
            display: inline-block; 
            padding: 10px 20px; 
            font-size: 16px; 
            color: white; 
            background-color: #4CAF50; /* Green for dashboard */
            text-decoration: none; 
            border-radius: 5px; 
            margin: 10px;
        }
        a.button-alt:hover { 
            background-color: #45a049; 
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Assignment Submission Failed!</h2>
        <p>There was an issue submitting your assignment. Please try again or contact your instructor for assistance.</p>
        
        <!-- Links for retrying or going to the dashboard -->
        <a href="submitAssignment.jsp" class="button">Retry Submission</a>
        <a href="dashboard.jsp" class="button-alt">Go to Dashboard</a>
    </div>
</body>
</html>
