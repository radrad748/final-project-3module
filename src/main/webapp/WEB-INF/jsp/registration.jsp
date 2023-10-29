<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26.10.2023
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
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
    <form class="form-horizontal" id="form" method="post" action="/registration-check">
            <!-- Text input-->
            <div class="form-group" id="email-div">
                <label class="col-md-4 control-label" for="text-input-email">Email</label>
                <div class="col-md-4">
                    <input id="text-input-email" name="text-input-email-name" type="text" placeholder="email" class="form-control input-md">
                </div>
            </div>
            <!-- Text input-->
            <div class="form-group" id="name-div">
                <label class="col-md-4 control-label" for="text-input-name">Name</label>
                <div class="col-md-4">
                    <input id="text-input-name" name="text-input-name-name" type="text" placeholder="name" class="form-control input-md">
                </div>
            </div>
            <!-- Password input-->
            <div class="form-group" id="password-div">
                <label class="col-md-4 control-label" for="passwor-dinput">Password</label>
                <div class="col-md-4">
                    <input id="passwor-dinput" name="password-input-name" type="password" placeholder="password" class="form-control input-md">
                </div>
            </div><br>
        <input type="submit" value="Register" class="btn btn-secondary">
        <a href="/" style="margin-left: 10px;">Beck</a>
    </form>
</div>
<script src="/registration-js"></script>
</body>
</html>
