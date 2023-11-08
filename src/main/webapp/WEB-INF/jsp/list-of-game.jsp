<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.10.2023
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/my.css">
    <link rel="stylesheet" type="text/css" href="/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="/css/play.css">

    <link rel="apple-touch-icon" sizes="180x180" href="/icon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/icon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/icon/favicon-16x16.png">
    <link rel="manifest" href="/icon/site.webmanifest">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark">
    <div class="container-fluid" style="margin-left: 12%;">
        <a class="navbar-brand" href="/first-page">
            <img src="/images/Q.png" width="50" class="logo"/>
        </a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ml-auto">
                <a class="nav-link" aria-current="page" href="/first-page">Home</a>
                <a class="nav-link" href="/statistics">Statistics</a>
                <a class="nav-link" href="/list-of-game">List of games</a>
                <a class="nav-link" href="/settings">Settings</a>
            </div>
        </div>
        <div style="margin-right: 10%">
            <a href="/?logout=true" class="logout">Logout</a>
        </div>
    </div>
</nav>
<div class="divPlay">
    <h1 class="center-text">Список игр</h1>
    <div class="div-card">
        <div class="card card-games" style="width: 18rem;">
            <img src="/images/card-game.png" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">Вопросы</h5>
                <p class="card-text">Уровень легкий: пройти 10 вопросов</p>
                <a href="/start" class="btn btn-secondary">Играть</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
