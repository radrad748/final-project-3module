<%@ page import="com.javarush.radik.entity.Question" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.10.2023
  Time: 17:59
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
                <a class="nav-link" href="#">Statistics</a>
                <a class="nav-link" href="#">List of games</a>
                <a class="nav-link" href="#">Settings</a>
            </div>
        </div>
        <div style="margin-right: 10%">
            <a href="/" class="logout">Logout</a>
        </div>
    </div>
</nav>
<div class="divPlay">
    <%
        HttpSession session1 = request.getSession();
        int indexQuestion = (int) session1.getAttribute("numberQuestion") - 1;
        List<Question> questions = (List<Question>) session1.getAttribute("questions");
        List<String> answers = questions.get(indexQuestion).getAnswers();
    %>
    <p>Вопрос <%= session1.getAttribute("numberQuestion") %> из 10</p>
    <h1 class="center-text"><%= questions.get(indexQuestion).getQuestion() %></h1>
    <div class="divImage">
        <img src="<%= questions.get(indexQuestion).getPathImage() %>" class="image-question">
    </div>
    <form method="get" action="#">
    <div class="divForm">
        <div class="divAnswers">
                <% for (int i = 0; i < answers.size(); i++) { %>
                <input type="radio" id="<%= "option" + i %>" name="group" value="<%= i %>">
                <label for="<%= "option" + i %>"><%= answers.get(i) %></label><br>
                <% } %>
        </div>
    </div>
     <div class="div-btn">
         <input type="submit" value="ОТВЕТИТЬ" class="btn btn-secondary btn-question">
     </div>
    </form>
</div>
</body>
</html>
