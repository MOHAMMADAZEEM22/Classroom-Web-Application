<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            width: 400px;

        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.5em;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="password"] {
            width: 92%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
        }

        button:hover {
            background-color: #45a049;
        }

        .login-toggle {
            font-size: 15px;
            color: #5C5C5C;
        }

        .login-toggle a {
            font-weight: 500;
            color: #077EFF;
            cursor: pointer;
        }
    </style>
</head>

<body>
    <div class="form-container">

        <h2>Student Registration</h2>
        <form action="regurl2" method="POST" class="form">
           
            <label for="student-id">Enter Student Id:</label>
            <input type="text" id="student-id" name="student-id" required maxlength="4"
             name="quantity" onkeypress="return (event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode == 46)">
            <label for="student-password">Password:</label>
            <input type="password" id="student-password" name="student-password" required maxlength="8">
            <button type="submit">Register</button>
            <p class='login-toggle'>Already have an account <a href="index.html">Login here</a></p>

        </form>
</body>

</html>