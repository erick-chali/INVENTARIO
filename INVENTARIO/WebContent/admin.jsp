<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.concurrent.Callable"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.millenium.db.conectar.ConectarDB"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/inventario.ico">
        <title>Toma de Inventario</title>
        <link type="text/css" rel="stylesheet" href="css/bootstrap.css">
        <link type="text/css" rel="stylesheet" href="css/bootstrap-table.css">
        <link type="text/css" rel="stylesheet" href="css/style.css">
        
    </head>
    
    <body>

	<%
	Connection con = null;
	ResultSet rs = null;
	CallableStatement stmt = null;
	
	%>

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
          <a class="navbar-brand" href="">Toma de inventario</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="agregar.jsp">Toma Inventario</a></li>
            <li ><a href="buscar.jsp">Inventario</a></li>
            <li class="active"><a href="admin.jsp">Inventario por Toma</a></li>
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
		<table data-toggle="table" data-classes="table" data-striped="true">
		<thead>
			<tr>
				<th data-sortable="true" >No.</th>
				<th data-sortable="true">Codigo</th>
				<th data-sortable="true">Descripcion</th>
				<th data-sortable="true">Cantidad</th>
				<th data-sortable="true">Unidad</th>
				<th data-sortable="true">Bodega</th>
				<th data-sortable="true">Seccion</th>
				<th data-sortable="true">Estanteria</th>
				<th data-sortable="true">Usuario</th>
				<th data-sortable="true">Fecha</th>
			</tr>
		</thead>
		<tbody>
				<% 
					try{
						con = new ConectarDB().getConnection();
						stmt = con.prepareCall("{call stp_ConsultaProductosAuditor(?)}");
						stmt.setString(1, (String) request.getSession().getAttribute("tomaGlobal"));
						System.out.println("Numero de toma de inventario:" + (String) request.getSession().getAttribute("tomaGlobal"));
						rs = stmt.executeQuery();
						
						int x = 0;
						while(rs.next()){
							x++;
							//String fechaoriginal = rs.getString("fecha_toma");
							//Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaoriginal);
							//sString newString = new SimpleDateFormat("dd/MM/yyyy").format(date);
							out.println("<tr>");
							out.println("<td>"+ x +"</td>");
							out.println("<td>"+ rs.getString("codigo_producto") +"</td>");
							out.println("<td>"+ rs.getString("descripcion_larga") +"</td>");
							out.println("<td>"+ rs.getString("conteo1") +"</td>");
							out.println("<td>"+ rs.getString("unidad") +"</td>");
							out.println("<td>"+ rs.getString("bodega") +"</td>");
							out.println("<td>"+ rs.getString("seccion") +"</td>");
							out.println("<td>"+ rs.getString("estanteria") +"</td>");
							out.println("<td>"+ rs.getString("UserName") +"</td>");
							out.println("<td>"+ rs.getString("fecha_toma") +"</td>");
							out.println("</tr>");
						}
						con.close();
						stmt=null;
						rs=null;
					}catch(SQLException e ){
						System.out.println("Error: " + e);
					}
				%>
			</tbody>
		</table>
	</div>
	<footer>
    	<h5 class="text-center"> ${usuarioGlobal}</h5>    
    </footer>
    
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-table.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    
</body>
</html>