<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    com.fashion.site.model.User user = (com.fashion.site.model.User) session.getAttribute("user");
    if(user == null){ response.sendRedirect("login"); return; }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Academic Share</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body.dark-mode { background-color: #121212; color: #e0e0e0; }
        .dark-mode .card { background-color: #1e1e1e; color: #e0e0e0; }
        .dark-mode .list-group-item { background-color: #1e1e1e; color: #e0e0e0; border-color: #333; }
    </style>
    <script>
        function toggleDarkMode() {
            document.body.classList.toggle('dark-mode');
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Academic Share</a>
        <div class="d-flex">
            <button class="btn btn-light me-2" onclick="toggleDarkMode()">Dark Mode</button>
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
    <p>Xin chào, <strong>${user.username}</strong>!</p>

    <!-- Danh sách tài liệu với phân trang -->
    <div class="card mb-4">
        <div class="card-body">
            <h5>All Documents</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${pagedDocs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span>${doc.title} (${doc.format})</span>
                        <div>
                            <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Download</a>
                            <a href="delete?id=${doc.id}" class="btn btn-sm btn-danger">Delete</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <!-- Nút phân trang -->
            <c:if test="${totalPages > 1}">
                <nav class="mt-3">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="dashboard?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>

    <!-- Các danh sách đặc biệt -->
    <div class="row">
        <div class="col-md-4">
            <h5>Newest Documents</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${newestDocs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${doc.title} (${doc.format})
                        <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Tải</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

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
