<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop"/>
    <meta name="author" content="Hỏi Dân IT"/>
    <title>Dashboard - Hỏi Dân IT</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const imageFile = $("#imageFile");
            const imagePreview = $("#imagePreview");
            imageFile.change(function (e) {
                const file = e.target.files[0];
                if (!file) {
                    console.log("No file selected.");
                    imagePreview.attr("src", "").css("display", "none");
                    return;
                }
                console.log(file);
                const imgURL = URL.createObjectURL(file);
                imagePreview.attr("src", imgURL).css("display", "block");
            });
        });

    </script>

</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp"/>
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp"/>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage Product</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                        <li class="breadcrumb-item active">Product</li>
                    </ol>
                    <div class="mt-5">
                        <div class="row">
                            <div class="col-12 mx-auto">
                                <div class="d-flex justify-content-between">
                                    <h3>User detail with id = ${product.id}</h3>
                                </div>
                                <hr>
                                <div class="card">
                                    <img style="width: fit-content;" src="/images/product/${product.image}" alt="${product.name}" class="card-img-top">
                                    <div class="card-header">
                                        User Information
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Id: ${product.id}</li>
                                        <li class="list-group-item">Name: ${product.name}</li>
                                        <li class="list-group-item">Price: ${product.price}</li>
                                    </ul>
                                </div>
                                <a href="/admin/product" class="btn btn-success mt-3">Back</a>
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