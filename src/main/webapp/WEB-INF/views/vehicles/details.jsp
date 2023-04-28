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
        Details du vehicule #${vehicle.id}
      </h1>
    </section>


    <section class="content">

      <div class="row">
        <div class="col-md-12">
          <div class="box">
            <div class="box-body no-padding">
              <table class="table table-striped">
                <tr>
                  <th>Constructeur</th>
                  <td>${vehicle.constructor}</td>
                </tr>
                <tr>
                  <th>Modele</th>
                  <td>${vehicle.modele}</td>
                </tr>
                <tr>
                  <th>Nombre de place</th>
                  <td>${vehicle.seats}</td>
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
