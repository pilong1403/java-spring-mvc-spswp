<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Table users</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </head>

        <body>
            <div class="container mt-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3 class="mb-0">Table users</h3>
                    <a href="<c:url value='/admin/user/create'/>" class="btn btn-primary">Create a user</a>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Email</th>
                                <th scope="col">Full Name</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}" varStatus="st">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.email}</td>
                                    <td>${u.fullName}</td>
                                    <td>
                                        <a href="#" class="btn btn-sm btn-success me-1">View</a>
                                        <a href="#" class="btn btn-sm btn-warning me-1">Update</a>
                                        <a href="#" class="btn btn-sm btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </body>

        </html>