<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Mega City Cab Service - Login</title>
    <style>
        /* Hide scrollbars */
        ::-webkit-scrollbar {
            display: none;
        }
        * {
            scrollbar-width: none;
        }

        /* Color variables */
        :root {
            --primary-color: #0d6efd;
            --secondary-color: #6c757d;
            --success-color: #198754;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --background-color: #f8f9fa;
            --hover-bg-color: rgba(13, 110, 253, 0.05);
        }

        /* General reset */
        body, h1, form {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* Body Styling */
        body {
            background: linear-gradient(to right, #00c6ff, whitesmoke); /* Keeping original gradient */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            position: relative;
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
            position: relative;
        }

        /* Cab service imagery */
        .cab-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
        }

        .cab-icon svg {
            width: 100%;
            height: 100%;
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
            color: var(--secondary-color);
        }

        /* Form Inputs Styling */
        label {
            display: block;
            text-align: left;
            margin-bottom: 8px;
            font-size: 14px;
            color: var(--secondary-color);
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
            background-color: var(--background-color);
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: var(--primary-color);
            outline: none;
            background-color: white;
        }

        /* Submit Button */
        button {
            width: 100%;
            padding: 12px;
            background-color: var(--primary-color);
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
            color: var(--danger-color);
            font-size: 14px;
            margin-top: 10px;
        }

        /* Register link styling */
        .register-link {
            margin-top: 15px;
            font-size: 14px;
            color: var(--primary-color);
            text-decoration: none;
            display: inline-block;
            padding: 5px 10px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .register-link:hover {
            background-color: var(--hover-bg-color);
            text-decoration: underline;
        }

        /* City skyline silhouette at bottom of container */
        .city-skyline {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 60px;
            overflow: hidden;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            opacity: 0.15;
        }

        .city-skyline svg {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="login-container">
<%--    <div class="cab-icon">--%>
<%--        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="var(--primary-color)">--%>
<%--            <path d="M1,11l2-6c0.553,0,1-0.447,1-1h1c0,0.553,0.447,1,1,1h10c0.553,0,1-0.447,1-1h1c0,0.553,0.447,1,1,1l2,6v8--%>
<%--            c0,0.553-0.447,1-1,1h-1c0-0.553-0.447-1-1-1h-2c-0.553,0-1,0.447-1,1H9c0-0.553-0.447-1-1-1H6c-0.553,0-1,0.447-1,1H4--%>
<%--            c-0.553,0-1-0.447-1-1V11z M13.5,2C14.328,2,15,2.672,15,3.5S14.328,5,13.5,5S12,4.328,12,3.5S12.672,2,13.5,2z M6.5,2--%>
<%--            C7.328,2,8,2.672,8,3.5S7.328,5,6.5,5S5,4.328,5,3.5S5.672,2,6.5,2z M4,8h16v2H4V8z M6.5,13C7.328,13,8,13.672,8,14.5--%>
<%--            S7.328,16,6.5,16S5,15.328,5,14.5S5.672,13,6.5,13z M17.5,13c0.828,0,1.5,0.672,1.5,1.5S18.328,16,17.5,16S16,15.328,16,14.5--%>
<%--            S16.672,13,17.5,13z"/>--%>
<%--        </svg>--%>
<%--    </div>--%>
    <h1>Mega City Cab Service</h1>
    <p>Your journey begins here</p>
    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required placeholder="Enter your email">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required placeholder="Enter your password">
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

    <div class="city-skyline">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1200 200" preserveAspectRatio="none">
            <path fill="var(--primary-color)" d="M0,200 L0,160 L30,160 L30,130 L60,130 L60,170 L90,170 L90,150 L120,150 L120,100 L150,100 L150,120 L180,120 L180,80 L210,80 L210,100 L240,100 L240,70 L270,70 L270,130 L300,130 L300,110 L330,110 L330,70 L360,70 L360,80 L390,80 L390,60 L420,60 L420,90 L450,90 L450,70 L480,70 L480,90 L510,90 L510,50 L540,50 L540,80 L570,80 L570,60 L600,60 L600,90 L630,90 L630,70 L660,70 L660,110 L690,110 L690,80 L720,80 L720,130 L750,130 L750,120 L780,120 L780,150 L810,150 L810,140 L840,140 L840,170 L870,170 L870,120 L900,120 L900,140 L930,140 L930,160 L960,160 L960,130 L990,130 L990,140 L1020,140 L1020,120 L1050,120 L1050,90 L1080,90 L1080,110 L1110,110 L1110,80 L1140,80 L1140,100 L1170,100 L1170,150 L1200,150 L1200,200 Z"/>
        </svg>
    </div>
</div>
</body>
</html>