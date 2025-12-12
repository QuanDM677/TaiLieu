<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    com.fashion.site.model.User user = (com.fashion.site.model.User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("login");
        return;
    }

    // Helper map cho icon theo định dạng
    java.util.Map<String,String> icons = new java.util.HashMap<>();
    icons.put("pdf","icons/pdf.png");
    icons.put("docx","icons/docx.png");
    icons.put("pptx","icons/pptx.png");
    icons.put("xlsx","icons/xlsx.png");
    icons.put("txt","icons/txt.png");
    icons.put("odt","icons/odt.png");
    icons.put("default","icons/file.png");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Academic Share</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Dark mode */
        .dark-mode { background-color: #121212; color: #e0e0e0; }
        .dark-mode .card { background-color: #1e1e1e; color: #e0e0e0; }
        .dark-mode .btn-light { background-color: #333; color: #fff; }
        .file-icon { width:32px; height:32px; margin-right:10px; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Academic Share</a>
        <div class="d-flex">
            <button id="toggleDark" class="btn btn-secondary me-2">Dark Mode</button>
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
                        <option value="xlsx" <c:if test="${param.format=='xlsx'}">selected</c:if>>XLSX</option>
                        <option value="txt" <c:if test="${param.format=='txt'}">selected</c:if>>TXT</option>
                        <option value="odt" <c:if test="${param.format=='odt'}">selected</c:if>>ODT</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <button class="btn btn-primary w-100">Filter</button>
                </div>
            </form>
        </div>
    </div>

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

    <!-- Document list with icon + pagination -->
    <div class="card mb-4">
        <div class="card-body">
            <h5>Documents</h5>
            <ul class="list-group">
                <c:forEach var="doc" items="${docs}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div class="d-flex align-items-center">
                            <img class="file-icon" src="${icons[fn:toLowerCase(doc.format)] != null ? icons[fn:toLowerCase(doc.format)] : icons['default']}" alt="icon">
                                ${doc.title} (${doc.format})
                        </div>
                        <div>
                            <a href="download?id=${doc.id}" class="btn btn-sm btn-primary">Tải</a>
                            <c:if test="${doc.uploaderId == sessionScope.user.id}">
                                <a href="delete?id=${doc.id}" class="btn btn-sm btn-danger">Xóa</a>
                            </c:if>
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <!-- Pagination -->
            <div class="mt-3">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a class="btn btn-sm ${i == currentPage ? 'btn-primary' : 'btn-light'}" href="dashboard?page=${i}">${i}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
    // Dark mode toggle
    const btn = document.getElementById('toggleDark');
    btn.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
        localStorage.setItem('darkMode', document.body.classList.contains('dark-mode'));
    });
    if(localStorage.getItem('darkMode') === 'true'){
        document.body.classList.add('dark-mode');
    }
</script>
</body>
</html>
