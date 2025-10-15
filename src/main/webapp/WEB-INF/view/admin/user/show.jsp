<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>User Detail ${id}</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </head>

        <body>
            <div class="container mt-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3 class="mb-0">User Detail with ID: ${id}</h3>
                </div>
                <hr />
                <div class="card" style="width: 50%;">
                    <div class="card-header">
                        <h5 class="card-title">User Information</h5>
                    </div>
                    <div class="card-body">
                        <ul>
                            <li class="card-text"><strong>ID:</strong> ${user.id}</li>
                            <li class="card-text"><strong>Email:</strong> ${user.email}</li>
                            <li class="card-text"><strong>Full Name:</strong> ${user.fullName}</li>
                            <li class="card-text"><strong>Phone Number:</strong> ${user.phone}</li>
                            <li class="card-text"><strong>Address:</strong> ${user.address}</li>
                        </ul>
                    </div>

                </div>
                <a href="/admin/user" class="btn btn-success m-3">Back </a>

            </div>
        </body>

        </html>