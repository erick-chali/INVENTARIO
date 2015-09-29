<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.millenium.db.conectar.ConectarDB"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" lang="es">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Instalaciones Modernas | Inicio de Sesi�n</title>
        <link type="text/css" rel="stylesheet" href="css/bootstrap.css">
        <link type="text/css" rel="stylesheet" href="css/style.css">
        
	<script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/select.js"></script>
    </head>
    
    <body>
    <center><img alt="Instalaciones Modernas" src="img/s.jpg" align="middle" id="logo"></center>
    <% 
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try{
    		conn = new ConectarDB().getConnection();
        	StringBuilder sb = new  StringBuilder();
        	sb.append("{call stp_Seleccionartomas}");
        	ps = conn.prepareCall(sb.toString());
        	rs = ps.executeQuery();
    	}catch(SQLException sqlex){
    		sqlex.printStackTrace();
    	}
    %>

  	
    <div class="container">
    	
      <form >
      	
        <h2 class="form-signin-heading">Inicio de sesi�n</h2>
        <label for="ingresaUsuario" class="sr-only">Usuario</label>
        <input type="text" id="usuario" name="usuario" class="form-control" placeholder="Usuario" required>
        <label for="inputPassword" class="sr-only">Clave</label>
        <input type="password" id="clave" name="clave" class="form-control" placeholder="Clave" required>
        <select class="form-control" id="noToma" name="noToma"  required>
        	<option value="">Seleccione una opcion</option>
            <% 
            	while(rs.next()){
            		out.print("<option value=\"" + rs.getString("no_toma") + "\">"+ rs.getString("no_toma") + " "  + rs.getString("observaciones") + "</option>"); 
            	}
	            conn.close();
	        	ps.close();
	        	rs.close();
            %>
        </select>
        <label class="bg-danger"><h5 class="text-danger">${mensaje}</h5></label>
        <label class="bg-danger"><h5 class="text-danger">${msjSesion}</h5></label>
        <button type="button" id="btnInicia" class="btn btn-lg btn-primary btn-block">Iniciar Sesion</button>
<!--        <button class="btn btn-lg btn-primary btn-block" type="submit">Iniciar Sesi�n</button>-->
      </form>
      
    </div>
  </body>
</html>