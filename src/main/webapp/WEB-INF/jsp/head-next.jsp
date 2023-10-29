<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.10.2023
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start Quest</title>
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
<div class="head-next">
    <img src="/images/article.png">
    <div class="head-next-article">
        <p>Привет!</p>
        <p>Сейчас вы сыграете в игру под названием “Реальная Мозгокачка”. В нем будет 10 интересных вопросов,
            мало кому удалось пройти весь путь без ошибок. Попробуй свои силы, будет интересно!!! </p>
        <form method="post" action="/login">
            <div class="form-floating mb-3">
                <input type="email" class="form-control" id="floatingInput" name="email" placeholder="name@example.com">
                <label for="floatingInput">Email address</label>
            </div>
            <div class="form-floating" id="login-button">
                <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password">
                <label for="floatingPassword">Password</label><br>
                <input type="submit" value="Login" class="btn btn-secondary">
                <a href="/registration" style="margin-left: 10px;">Registration</a>
            </div>
        </form>
    </div>
</div>
<script src="/my-js"></script>
</body>
</html>
