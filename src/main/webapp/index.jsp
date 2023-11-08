<html>
<body>
<h2>Start Quest</h2>
<% String logout = request.getParameter("logout");
   if (logout != null) request.getSession().invalidate();
%>
<jsp:forward page="WEB-INF/jsp/head-next.jsp"/>
</body>
</html>
