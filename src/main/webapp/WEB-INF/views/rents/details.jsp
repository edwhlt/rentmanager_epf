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
    <!-- Main content -->
    <section class="content-header">
      <h1>
        Details de la reservation #${reservation.id}
      </h1>
    </section>

    <section class="content">

      <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-body no-padding">
              <table class="table table-striped">
                <tr>
                  <th>Vehicule Loue</th>
                  <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/cars/details?id=${reservation.vehicleId}">Afficher les details</a></td>
                </tr>
                <tr>
                  <th>Loueur</th>
                  <td>${client.lastName} ${client.firstName} (Age: ${age})</td>
                </tr>
                <tr>
                  <th>Date de debut</th>
                  <td>${reservation.start}</td>
                </tr>
                <tr>
                  <th>Date de fin</th>
                  <td>${reservation.end}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>

  <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
