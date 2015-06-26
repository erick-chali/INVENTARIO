<%@page import="java.util.concurrent.Callable"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.millenium.db.conectar.ConectarDB"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
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
            <li><a href="agregar.jsp">Toma Inventario</a></li>
            <li class="active"><a href="buscar.jsp">Inventario</a></li>
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
	
	
	
	<div class="modal fade" id="popEditar">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
				    <h4>Editar Informacion de Producto</h4>
				</div><!-- Encabezado  -->
				<div class="modal-body">
					<div class="panel panel-default">
            <div class="panel-heading">Editar Cantidad</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-6 col-sm-4 col-md-4">
                            <label for="bodega" class="text-right">Bodega</label>
                            <select id="bodega" class="form-control" disabled>
                                <option value="">Bodega</option>
                                <% 
										Connection connB = null;
										CallableStatement psB = null;
										ResultSet rsB = null;
										try{
											
											connB = new ConectarDB().getConnection();
											StringBuilder sbB = new  StringBuilder();
											psB = connB.prepareCall("{call stp_udtf_consultabodega(?)}");
											psB.setString(1, (String) request.getSession().getAttribute("tomaGlobal"));
											rsB = psB.executeQuery();
											if(rsB!=null){
									            while(rsB.next()){
								            		out.print("<option value=\"" + rsB.getString("codigo_bodega") + "\">"  + rsB.getString("descripcion") + "</option>"); 
								            	}
									            connB.close();
									        	psB.close();
									        	rsB.close();
								            }else{
								            	out.print("<option>"+"No hay Bodegas Disponibles"+"</option>");
								            }
										}catch(SQLException sqlex){
											sqlex.printStackTrace();
										}
									%>
                            </select>
                            <label for="codigoProducto" class="text-right">Codigo Producto</label>
                            <input class="form-control" type="text" id="codigoProducto" name="codigoProducto" placeholder="Codigo Producto" disabled>
                            <label for="descripcion" class="text-right">Descripción</label>
                            <input class="form-control"  type="text" id="descripcion" name="descripcion" placeholder="Descripción" disabled>
                            <label for="unidad">Unidad de Medida</label>
                            <input class="form-control" type="text" id="unidad" name="unidad" placeholder="Unidad Medida" disabled>
                        </div>
                        <div class="col-xs-6 col-sm-4 col-md-4">
                            <label for="estanteria" class="text-right">Estanteria</label>
                                <select id="estanteria" class="form-control" disabled>
				                    <option value="">Estanteria</option>
				                    <% 
										Connection connE = null;
										CallableStatement psE = null;
										ResultSet rsE = null;
										try{
											
											connE = new ConectarDB().getConnection();
											psE = connE.prepareCall("{call stp_buscaEstanteria}");
											rsE = psE.executeQuery();
											if(rsE!=null){
									            while(rsE.next()){
								            		out.print("<option value=\"" + rsE.getString("Estanteria_Id") + "\">"  + rsE.getString("descripcion") + "</option>"); 
								            	}
									            connE.close();
									            psE.close();
									            rsE.close();
								            }else{
								            	out.print("<option>"+"No hay Bodegas Disponibles"+"</option>");
								            }
										}catch(SQLException sqlex){
											sqlex.printStackTrace();
										}
									%>
			                	</select>
                            <label for="seccion" class="text-right">Seccion</label>
                                <select id="seccion" class="form-control" disabled>
				                    <option value="">Seccion</option>
				                    <% 
										Connection connS = null;
										CallableStatement psS = null;
										ResultSet rsS = null;
										try{
											
											connS = new ConectarDB().getConnection();
											psS = connS.prepareCall("{call stp_buscaseccion}");
											rsS = psS.executeQuery();
											if(rsS!=null){
									            while(rsS.next()){
								            		out.print("<option value=\"" + rsS.getString("Seccion_Id") + "\">"  + rsS.getString("descripcion") + "</option>"); 
								            	}
									            connS.close();
									            psS.close();
									            rsS.close();
								            }else{
								            	out.print("<option>"+"No hay Bodegas Disponibles"+"</option>");
								            }
										}catch(SQLException sqlex){
											sqlex.printStackTrace();
										}
									%>
			                	</select>
                        </div>
                        <div class="col-xs-7 col-sm-4 col-md-4">
                            <label for="cantidadActual" class="text-right">Cantidad Actual</label>
                            <input class="form-control" type="text" id="cantidadActual" name="cantidad" placeholder="Cantidad Actual" disabled>
                            <label for="cantidad" class="text-right">Cantidad Producto</label>
                            <input class="form-control" type="text" id="cantidad" placeholder="*Cantidad" >
                            <label for="total" class="text-right">Total</label>
                            <input class="form-control" type="text" id="cantidadTotal" placeholder="*Cantidad" disabled>
                        </div>
                        <div class="col-xs-12 col-sm-12 col-md-12">
                        	<button class="btn btn-default" type="button" id="actualizarProd">Actualizar Producto</button>
                        </div>
                    </div>
                </div>
            <div class="panel-footer"><h4 id="notificacion"></h4></div>
        </div>
				</div><!-- Modal Body  -->
			</div><!-- Modal Content  -->
		</div><!-- Dialog  -->
	</div><!--Ventana Pop Up Buscar Producto -->
	
        
<%@ include file="includes/footer.jsp" %>