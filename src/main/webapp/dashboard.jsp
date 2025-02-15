<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="MegaCity Cab Service - Instance Status">
    <meta name="author" content="MegaCity Cab Team">
    <title>MegaCity Cab Service</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            color: #333;
            text-align: center;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        h1 {
            color: #007bff;
            font-size: 2.5rem;
            margin-bottom: 1rem;
        }

        p {
            font-size: 1.2rem;
            margin-bottom: 1rem;
        }

        .status {
            font-weight: bold;
            color: #28a745; /* Green color for "Up" status */
        }

        footer {
            margin-top: 20px;
            font-size: 0.9rem;
            color: #666;
        }
    </style>
</head>
<body>
<h1>MegaCity Cab Service</h1>
<p>System Status: <span class="status">Instance is up</span></p>
<p>Welcome to the MegaCity Cab Service! The platform is running smoothly.</p>

<footer>&copy; <%= new java.util.Date().getYear() + 1900 %> MegaCity Cab Service. All rights reserved.</footer>
</body>
</html>
