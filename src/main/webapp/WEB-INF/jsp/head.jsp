<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 20.10.2023
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/my.css">

    <link rel="apple-touch-icon" sizes="180x180" href="/icon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/icon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/icon/favicon-16x16.png">
    <link rel="manifest" href="/icon/site.webmanifest">
</head>
<body>
<nav class="navbar bg-body-tertiary">
    <div class="container-fluid head">
            <img src="/images/Quest.png" alt="Logo" class="head-image">
    </div>
</nav>
<div style="padding-top: 5%">
        <h1 style="text-align: center">Вопросы: легкий левел!</h1>
        <h2 style="text-align: center">жми старт и погнали!</h2><br>
    <a href="/play" style="margin-left: 42%">
        <img src="/images/start.png" alt="Start" height="250" width="300" class="image-start">
    </a>
</div>
</body>
</html>
