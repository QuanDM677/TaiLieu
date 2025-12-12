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
    <title><fmt:message key="login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%@ include file="header.jsp" %>
<div class="container mt-5">
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-2">${error}</div>
    </c:if>
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success mt-2">${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow">
                <div class="card-body">
                    <h3 class="card-title text-center"><fmt:message key="login"/></h3>
                    <form action="login" method="post">
                        <div class="mb-3">
                            <label><fmt:message key="login"/></label>
                            <input type="text" class="form-control" name="username" value="${cookie.username.value}" required>
                        </div>
                        <div class="mb-3">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" required>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" name="remember" id="remember">
                            <label class="form-check-label" for="remember">Remember Me</label>
                        </div>
                        <button class="btn btn-primary w-100"><fmt:message key="login"/></button>
                    </form>
                    <hr>
                    <p class="text-center">
                        Chưa có tài khoản? <a href="register"><fmt:message key="register"/></a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
