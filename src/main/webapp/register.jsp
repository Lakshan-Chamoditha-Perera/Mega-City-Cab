<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <style>
        /* General reset */
        body, h1, form {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* Body Styling */
        body {
            background: linear-gradient(to right, #00c6ff, whitesmoke); /* Gradient background */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Container for the register form */
        .register-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 420px;
            text-align: center;
            border: 1px solid #ddd;
        }

        /* Title */
        h1 {
            margin-bottom: 20px;
            font-size: 28px;
            color: #333;
            font-weight: 600;
        }

        /* Description or Tagline */
        p {
            font-size: 16px;
            margin-bottom: 30px;
            color: #555;
        }

        /* Form Inputs Styling */
        label {
            display: block;
            text-align: left;
            margin-bottom: 8px;
            font-size: 14px;
            color: #555;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="tel"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            color: #333;
            box-sizing: border-box;
            transition: border-color 0.3s ease-in-out;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="email"]:focus,
        input[type="tel"]:focus {
            border-color: #0072ff;
            outline: none;
        }

        /* Submit Button */
        button {
            width: 100%;
            padding: 12px;
            background-color: #0072ff;
            border: none;
            border-radius: 4px;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* Error message styling */
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }

        /* Success message styling */
        .success-message {
            color: green;
            font-size: 14px;
            margin-top: 10px;
        }

        /* Link to login page */
        .login-link {
            margin-top: 20px;
            font-size: 14px;
            color: #0072ff;
            text-decoration: none;
            display: inline-block;
        }

        .login-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h1>Mega City Cab Service</h1>
    <p>Create your account</p>
    <form action="${pageContext.request.contextPath}/auth/register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Register</button>
    </form>

    <!-- Display error message if registration fails -->
    <c:if test="${not empty param.error}">
        <p class="error-message">
            Registration failed. Please try again.
            <span>
                    ${param.error}
            </span>
        </p>

    </c:if>

    <!-- Display success message if registration is successful -->
    <c:if test="${not empty param.success}">
        <p class="success-message">Registration successful! Please log in.</p>
    </c:if>

    <a href="${pageContext.request.contextPath}/auth/login" class="login-link">Already have an account? Login</a>
</div>
</body>
</html>