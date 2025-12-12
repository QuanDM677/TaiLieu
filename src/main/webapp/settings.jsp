<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    com.fashion.site.model.User user = (com.fashion.site.model.User) session.getAttribute("user");
    if(user == null){ response.sendRedirect("login"); return; }
%>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_US'}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="settings.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container mt-5">
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success">${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <h3><fmt:message key="settings.title"/></h3>
    <form action="settings" method="post">
        <div class="mb-3">
            <label><fmt:message key="settings.major"/></label>
            <input type="text" class="form-control" name="major" value="${user.major != null ? user.major : ''}">
        </div>
        <div class="mb-3">
            <label><fmt:message key="settings.school"/></label>
            <input type="text" class="form-control" name="school" value="${user.school != null ? user.school : ''}">
        </div>
        <div class="mb-3">
            <label><fmt:message key="settings.preferredFormats"/></label>
            <select name="preferredFormats" class="form-select" multiple>
                <option value="pdf" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('pdf')}">selected</c:if>>PDF</option>
                <option value="docx" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('docx')}">selected</c:if>>DOCX</option>
                <option value="pptx" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('pptx')}">selected</c:if>>PPTX</option>
            </select>
        </div>
        <button class="btn btn-primary"><fmt:message key="settings.save"/></button>
    </form>
    <a href="dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
