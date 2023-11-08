<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 31.10.2023
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/my.css">
    <link rel="stylesheet" type="text/css" href="/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="/css/play.css">
    <link rel="stylesheet" type="text/css" href="/css/settings.css">

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
<div class="main-div">
    <div class="accord-div">
        <div class="accordion accordion-flush" id="accordionFlushExample">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                        Изменить имя
                    </button>
                </h2>
                <div id="flush-collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">
                        <form id="formName">
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="name-input">Новое имя</label>
                            <div class="col-md-5" id="wrong-name">
                                <input id="name-input" name="nameinput" type="text" placeholder="new name" class="form-control input-md" maxlength="100"><br>
                                <button type="submit" class="save-button">
                                    <img src="/images/save.png" alt="save" class="save-image">
                                </button>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
                        Изменить Email
                    </button>
                </h2>
                <div id="flush-collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">
                        <form id="formEmail">
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="email-input">Новый емейл</label>
                            <div class="col-md-5" id="wrong-email">
                                <input id="email-input" name="emailinput" type="email" placeholder="new email" class="form-control input-md" maxlength="200"><br>
                                <button type="submit" class="save-button">
                                    <img src="/images/save.png" alt="save" class="save-image">
                                </button>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
                        Изменить пароль
                    </button>
                </h2>
                <div id="flush-collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">
                        <form id="formPassword">
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="old-password">Текущий пароль</label>
                            <div class="col-md-5">
                                <input id="old-password" name="oldpassword" type="password" placeholder="password" class="form-control input-md">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="new-password">Новый пароль</label>
                            <div class="col-md-5" id="wrong-password">
                                <input id="new-password" name="newpassword" type="password" placeholder="new password" class="form-control input-md"><br>
                                <button type="submit" class="save-button">
                                    <img src="/images/save.png" alt="save" class="save-image">
                                </button>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <p>Удалить профиль</p>
    <button type="button" class="btn-close" aria-label="Close" id="deleteProfileButton"></button>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<% ServletContext context = request.getServletContext();
    context.setAttribute("pathJs", "WEB-INF/js/sattings.js"); %>
<script src="/all-js"></script>
</body>
</html>
