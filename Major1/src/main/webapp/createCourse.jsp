<!DOCTYPE html>
<html>
<head>
    <title>Create Course</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { color: #4CAF50; }
        input[type="text"], textarea, input[type="submit"] {
            width: 100%; padding: 10px; margin: 10px 0;
            border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }
        textarea { height: 100px; }
        input[type="submit"] {
            background-color: #4CAF50; color: white; cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Course</h2>
        <form action="CreateCourseServlet" method="POST">
            <label for="courseId">Course ID</label>
            <input type="text" id="courseId" name="courseId" placeholder="Enter course ID" required>

            <label for="courseName">Course Name</label>
            <input type="text" id="courseName" name="courseName" placeholder="Enter course name" required>

            <label for="courseDescription">Course Description</label>
            <textarea id="courseDescription" name="courseDescription" placeholder="Enter course description"></textarea>

            <input type="submit" value="Create Course">
        </form>
    </div>
</body>
</html>
