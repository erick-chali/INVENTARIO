<%@page import="java.sql.SQLException"%>
<%@page import="com.millenium.db.conectar.ConectarDB"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta lang="es">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/inventario.ico">
        <title>Conteo2</title>
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
        
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
        <link type="text/css" rel="stylesheet" href="css/style.css">
		
        
    </head>
    <body>
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
        <h5 class="text-left" id="hoy"></h5>
        <h4 class="text-center" id="txtToma">${textoToma}</h4>
        <h4 class="text-center" id="txtNum"> No.: ${tomaGlobal} </h4>
        <div id="tope"></div>
        
	<div class="panel panel-default">
            <div class="panel-heading"></div>
                <div class="panel-body ">
                    <div class="row">
                        
                        <div class="col-sm-4 col-md-4">
                            <label for="codigoProducto" class="text-right">Codigo Producto</label>
                            <input class="form-control" type="text" id="codigoToma2" name="codigoProducto" placeholder="Codigo Producto">
                              
                        </div>
                        <div class="col-sm-4 col-md-4">
                            
                            <label for="descripcion" class="text-right">Descripción</label>
                            <input class="form-control"  type="text" id="descripcion" name="descripcion" placeholder="Descripción" readonly>
                              
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label for="unidad">Unidad de Medida</label>
                            <input class="form-control" type="text" id="unidad" name="unidad" placeholder="Unidad Medida" readonly>  
                        </div>
                    </div>
                </div>
            <div class="panel-footer"><h4 id="notificacion" class="alert alert-info" role="alert"></h4></div>
        </div>
	
	<div id="tablaDatosToma2" >
	</div>
	
<footer>
    	<h5 class="text-center"> ${usuarioGlobal}</h5>
    </footer>
    
    
    <script type="text/javascript" src="js/jquery-1.5.min.js"></script>
	<script type="text/javascript">
	    	var $jq = jQuery.noConflict();
	</script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	    
	<script src="js/bootstrap.min.js"></script>
	    
	<script src="js/scriptConteo2.js"></script>
	    
	<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
	<script src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
</body>
</html>