<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <!DOCTYPE html>
      <html lang="vi">

      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tạo người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
      </head>

      <body>
        <div class="col-md-6 col-12 mx-auto mt-4">
          <h1 class="mb-4">Tạo người dùng mới</h1>

          <form:form method="post" action="/admin/user/create" modelAttribute="newUser">
            <div class="mb-4">
              <label for="email" class="form-label">Email</label>
              <form:input type="email" class="form-control" path="email" aria-required="" />
            </div>
            <div class="mb-4">
              <label for="password" class="form-label">Mật khẩu</label>
              <form:input type="password" class="form-control" path="password" aria-required="" />
            </div>
            <div class="mb-4">
              <label for="fullName" class="form-label">Họ và tên</label>
              <form:input type="text" class="form-control" path="fullName" aria-required="" />
            </div>
            <div class="mb-4">
              <label for="phone" class="form-label">Số điện thoại</label>
              <form:input type="text" class="form-control" path="phone" aria-required="" />
            </div>
            <div class="mb-4">
              <label for="address" class="form-label">Địa chỉ</label>
              <form:input type="text" class="form-control" path="address" aria-required="" />
            </div>
            <div class="mt-4 ">
              <button type="submit" class="btn btn-primary">Lưu</button>
              <a href="/" class="btn btn-outline-secondary">Hủy</a>
            </div>
          </form:form>
        </div>
      </body>

      </html>