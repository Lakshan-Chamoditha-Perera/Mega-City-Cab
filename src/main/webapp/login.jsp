<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
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

        /* Container for the login form */
        .login-container {
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
        input[type="password"] {
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
        input[type="password"]:focus {
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

        /* Link to forgot password */
        .forgot-password {
            margin-top: 10px;
            font-size: 14px;
            color: #0072ff;
            text-decoration: none;
            display: inline-block;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }

        /* Register link styling */
        .register-link {
            margin-top: 10px;
            font-size: 14px;
            color: #0072ff;
            text-decoration: none;
            display: inline-block;
        }

        .register-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>Mega City Cab Service</h1>
    <p>Login to your account</p>
    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="submit">Login</button>
    </form>

    <!-- Display error messages based on the error parameter -->
    <c:if test="${not empty param.error}">
        <c:choose>
            <c:when test="${param.error == 'invalid_input'}">
                <p class="error-message">Please provide both email and password.</p>
            </c:when>
            <c:when test="${param.error == 'user_not_found'}">
                <p class="error-message">User not found. Please check your email or register.</p>
            </c:when>
            <c:when test="${param.error == 'invalid_credentials'}">
                <p class="error-message">Invalid email or password. Please try again.</p>
            </c:when>
            <c:when test="${param.error == 'system_error'}">
                <p class="error-message">An unexpected error occurred. Please try again later.</p>
            </c:when>
            <c:otherwise>
                <p class="error-message">Login failed. Please try again.</p>
            </c:otherwise>
        </c:choose>
    </c:if>

    <!-- Link to the registration page -->
    <a href="${pageContext.request.contextPath}/auth/register" class="register-link">Don't have an account? Register</a>
</div>
</body>
</html>