<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content="Dự án laptopshop"/>
    <meta name="author" content=""/>
    <title>Order Detail</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Manage Order</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="/admin/order">Order</a></li>
                    <li class="breadcrumb-item active">View</li>
                </ol>
                <div class="mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Order detail with id = ${order.id}</h3>
                            </div>
                            <hr>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Tên</th>
                                    <th>Giá</th>
                                    <th>Số lượng</th>
                                    <th>Thành tiền</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="orderDetail" items="${order.orderDetails}">
                                    <tr>
                                        <td><img src="/images/product/${orderDetail.product.image}" class="img-fluid me-5 rounded-circle" style="width: 80px; height: 80px;" alt=""></td>
                                        <td><a href="/admin/product/${orderDetail.product.id}">${orderDetail.product.name}</a></td>
                                        <td><fmt:formatNumber type="number" value="${orderDetail.price}"/> đ</td>
                                        <td>${orderDetail.quantity}</td>
                                        <td><fmt:formatNumber type="number" value="${orderDetail.price * orderDetail.quantity}"/> đ</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <a href="/admin/order" class="btn btn-success mt-3">Back</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
</body>

</html>