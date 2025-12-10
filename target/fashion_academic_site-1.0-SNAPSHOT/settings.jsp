<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    com.fashion.site.model.User user = (com.fashion.site.model.User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Settings - Academic Share</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container mt-5">
    <h3>Account Settings</h3>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <form action="settings" method="post">
        <div class="mb-3">
            <label>Major</label>
            <input type="text" class="form-control" name="major" value="${user.major != null ? user.major : ''}">
        </div>
        <div class="mb-3">
            <label>School</label>
            <input type="text" class="form-control" name="school" value="${user.school != null ? user.school : ''}">
        </div>
        <div class="mb-3">
            <label>Preferred Formats (Ctrl+Click to select multiple)</label>
            <select name="preferredFormats" class="form-select" multiple>
                <option value="pdf" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('pdf')}">selected</c:if>>PDF</option>
                <option value="docx" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('docx')}">selected</c:if>>DOCX</option>
                <option value="pptx" <c:if test="${user.preferredFormats != null && user.preferredFormats.contains('pptx')}">selected</c:if>>PPTX</option>
            </select>
        </div>
        <button class="btn btn-primary">Save Settings</button>
    </form>
    <a href="dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
