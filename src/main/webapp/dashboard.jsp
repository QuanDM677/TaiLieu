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
    <title>Dashboard - Academic Share</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Academic Share</a>
        <div class="d-flex">
            <a class="btn btn-light me-2" href="settings">Settings</a>
            <a class="btn btn-danger" href="logout">Logout</a>
        </div>
    </div>
</nav>

<div class="container mt-4">

    <!-- Thông báo -->
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success">${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <h2>Dashboard</h2>
    <p>Xin chào, <strong>${sessionScope.user.username}</strong>!</p>

    <!-- Filter form -->
    <div class="card mb-4">
        <div class="card-body">
            <h5>Filter Documents</h5>
            <form action="filter" method="post" class="row g-3">
                <div class="col-md-3">
                    <input type="text" class="form-control" name="major" placeholder="Ngành" value="${param.major}">
                </div>
                <div class="col-md-3">
                    <input type="text" class="form-control" name="school" placeholder="Trường" value="${param.school}">
                </div>
                <div class="col-md-3">
                    <select name="format" class="form-select">
                        <option value="">Định dạng</option>
                        <option value="pdf" <c:if test="${param.format=='pdf'}">selected</c:if>>PDF</option>
                        <option value="docx" <c:if test="${param.format=='docx'}">selected</c:if>>DOCX</option>
                        <option value="pptx" <c:if test="${param.format=='pptx'}">selected</c:if>>PPTX</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <button class="btn btn-primary w-100">Filter</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Filtered results -->
    <c:if test="${not empty filteredDocs}">
        <div class="card mb-4">
            <div class="card-body">
                <h5>Filtered Results</h5>
                <ul class="list-group">
                    <c:forEach var="doc" items="${filteredDocs}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                ${doc.title} (${doc.format})
                            <div>
                                <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Download</a>
                                <a href="delete?id=${doc.id}" class="btn btn-sm btn-danger">Delete</a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>

    <!-- Upload form -->
    <div class="card mb-4">
        <div class="card-body">
            <h5>Upload New Document</h5>
            <form action="upload" method="post" enctype="multipart/form-data" class="row g-3">
                <div class="col-md-4">
                    <input type="text" class="form-control" name="title" placeholder="Tên tài liệu" required>
                </div>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="major" placeholder="Ngành">
                </div>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="school" placeholder="Trường">
                </div>
                <div class="col-md-3">
                    <input type="file" class="form-control" name="file" required>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-success w-100">Tải lên</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <!-- Newest documents -->
        <div class="col-md-4">
            <h5>Newest Documents</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${newestDocs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${doc.title} (${doc.format})
                        <div>
                            <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Tải</a>
                            <a href="delete?id=${doc.id}" class="btn btn-sm btn-danger">Xóa</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Popular documents -->
        <div class="col-md-4">
            <h5>Popular Documents</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${popularDocs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${doc.title} (${doc.format}) - ${doc.downloadCount} downloads
                        <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Tải</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Recommended -->
        <div class="col-md-4">
            <h5>Recommended for You</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${recommendedDocs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${doc.title} (${doc.format})
                        <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Tải</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>
