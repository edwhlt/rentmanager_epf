<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/edit">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach var="reserv" items="${requestScope['listReservations']}" >
                                    <tr>
                                        <td>${reserv.id}</td>
                                        <td>${listVehicules.get(reserv.vehicleId).constructor} ${listVehicules.get(reserv.vehicleId).modele}</td>
                                        <td>${listClients.get(reserv.clientId).lastName} ${listClients.get(reserv.clientId).firstName}</td>
                                        <td>${reserv.start}</td>
                                        <td>${reserv.end}</td>
                                        <td>
                                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/details?id=${reserv.id}">
                                                <i class="fa fa-play"></i>
                                            </a>
                                            <a class="btn btn-success" href="${pageContext.request.contextPath}/rents/edit?id=${reserv.id}">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                            <form action="${pageContext.request.contextPath}/rents" method="post" style="display: inline-block;">
                                                <input type="hidden" name="id" value="${reserv.id}">
                                                <button type="submit" class="btn btn-danger">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
