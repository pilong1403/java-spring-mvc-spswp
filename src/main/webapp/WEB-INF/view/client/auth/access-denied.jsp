<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Access Denied</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #fff;
        }

        .container {
            text-align: center;
            background-color: rgba(0, 0, 0, 0.6);
            padding: 40px 60px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
        }

        h1 {
            font-size: 100px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #ffdd57;
        }

        p {
            font-size: 18px;
            margin-bottom: 30px;
        }

        .back-btn {
            background-color: #ffdd57;
            color: #333;
            padding: 15px 30px;
            font-size: 18px;
            text-decoration: none;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        .back-btn:hover {
            background-color: #ffb347;
            transform: scale(1.05);
        }

        .error-icon {
            font-size: 150px;
            margin-bottom: 20px;
            color: #ffdd57;
        }

    </style>
</head>
<body>
    <div class="container">
        <div class="error-icon">🚫</div>
        <h1>403</h1>
        <p>Oops! You don't have permission to access this page.</p>
        <a href="/" class="back-btn">Go Back to Home</a>
    </div>
</body>
</html>