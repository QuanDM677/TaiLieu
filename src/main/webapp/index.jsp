<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    Object userObj = session.getAttribute("user");
    if(userObj != null){
        response.sendRedirect("dashboard");
        return;
    }
%>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_US'}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="welcome.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container text-center mt-5">
    <h1><fmt:message key="welcome.title"/></h1>
    <p class="lead"><fmt:message key="welcome.description"/></p>
    <a href="login.jsp" class="btn btn-primary"><fmt:message key="login"/></a>
    <a href="register.jsp" class="btn btn-success"><fmt:message key="register"/></a>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
