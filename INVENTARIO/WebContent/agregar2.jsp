<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="includes/header.jsp" %>
<!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="">Editar</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="agregar.jsp">Primera Toma</a></li>
            <li class="active"><a href="agregar.jsp">Segunda Toma</a></li>
            <li><a href="buscar.jsp">Inventario</a></li>
            <li><a href="admin.jsp">Inventario por Toma</a></li>
            <li><a href="diferencia.jsp">Diferencias</a></li>
            <!---agegar mas <li> para agregar mas opciones--->
          </ul>
          <ul class="nav navbar-nav navbar-right">
          	<li><a></a></li>
            <li><a>Usuario Conectado: ${usuarioGlobal} </a></li>
            <li><a></a></li>
            <li><a href="Logout">Cerrar Sesión</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <center><img alt="Instalaciones Modernas" src="img/s.jpg" align="middle" id="logo"></center>
    	<h5 id="hoy" class="text-right"></h5>
        <h3 class="text-center">Inventario de Productos</h3>
        <div id="tope"></div>
        
	
	
	<div class="table-responsive col-sm-12 col-md-12 col-lg-12">
		<table  id="datosBusqueda" class="table table-bordered table-striped">
		<!-- <table id="datosBusqueda" data-toggle="table" data-classes="table table-hover table-bordered" data-striped="true" data-row-style="rowStyle" data-search="true" data-pagination="true" data-show-columns="true"> -->
		</table>
	</div>

<%@ include file="includes/footer.jsp" %>