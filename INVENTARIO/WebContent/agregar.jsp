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
        <meta lang="es">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/inventario.ico">
        <title>Diferencias</title>
        <link type="text/css" rel="stylesheet" href="css/bootstrap.css">
        
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
        <link type="text/css" rel="stylesheet" href="css/style.css">
        
		
        
    </head>
    <body>
	<!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top" id="barraNav">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="">Tomar Inventario</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active" id="toma1"><a href="agregar.jsp" class="active" id="conteo1">Toma 1</a></li>
            <li ><a href="agregar2.jsp" id="conteo2">Toma 2</a></li>
            <li ><a href="buscar.jsp" id="inventario">Inventario</a></li>
            <li><a href="admin.jsp" id="auditoria">Auditor&iacute;a</a></li>
            <li><a href="diferencia.jsp" id="diferencia">Diferencias</a></li>
            <!---agegar mas <li> para agregar mas opciones--->
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a>Usuario Conectado: ${usuarioGlobal}</a></li>
            <li><a></a></li>
            <li><a href="Logout">Cerrar Sesión</a></li>
          </ul> 
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <center><img alt="Instalaciones Modernas" src="img/s.jpg"></center>
    	<h5 class="text-left" id="hoy"></h5>
        <h4 class="text-center" id="txtToma">${textoToma}</h4>
        <h4 class="text-center" id="txtNum"> No.: ${tomaGlobal} </h4>
        <div id="tope"></div>
    
    <div class="panel panel-default">
            <div class="panel-heading"></div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label for="bodega" class="text-right">Bodega</label>
                            <select id="bodega" class="form-control" class="required">
                                <option value="" selected="selected">Bodega</option>
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
                            <label for="estanteria" class="text-right">Estanteria</label>
                            <select id="estanteria" class="form-control" class="required">
				                    <option value="" selected="selected">Estanteria</option>
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
			                	<label for="seccionA" class="text-right">Seccion</label>
			                	<select id="seccionA" class="form-control" class="required">
				                    
			                	</select>
                            
                        </div>
                        <div class="col-sm-4 col-md-4">
                            
                            <label for="codigoProducto" class="text-right">Codigo Producto</label>
                            <input class="form-control" type="text" id="codigoProducto" name="codigoProducto" placeholder="Codigo Producto">
                            
                            <div class="col-sm-12 col-md-12">
                            	<button class="btn btn-default col-sm-6 col-md-6" id="popAbrir"data-toggle="modal" data-target="#popBuscaProd" style="background-color: #CCEEFF">Buscar Producto</button>
                            </div>
                            
                            
                            <label for="descripcion" class="text-right">Descripción</label>
                            <input class="form-control"  type="text" id="descripcion" name="descripcion" placeholder="Descripción" disabled>
                            <label for="unidad">Unidad de Medida</label>
                            <input class="form-control" type="text" id="unidad" name="unidad" placeholder="Unidad Medida" disabled>  
                            
                                
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label for="cantidadActual" class="text-right">Cantidad Actual</label>
                            <input class="form-control" type="text" id="cantidadActual" name="cantidad" placeholder="Cantidad Actual" disabled>
                            <label for="cantidad" class="text-right">Cantidad Producto</label>
                            <input class="form-control" type="number" id="cantidad" placeholder="*Cantidad" >
                            <label for="total" class="text-right">Total</label>
                            <input class="form-control" type="text" id="cantidadTotal" placeholder="*Cantidad" disabled>
                        </div>
                        <div class="col-sm-12 col-md-12">
                        	<button class="btn btn-default" type="button" id="tomarInventario" style="background-color: #CCEEFF">Agregar a Inventario</button>
                        </div>
                    </div>
                </div>
            <div class="panel-footer"><h4 id="notificacion" class="alert alert-info" role="alert"></h4></div>
        </div>
    
    <div class="modal fade " id="popBuscaProd">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" data-dismiss="modal">&times;</button>
				    <h4>Buscar Informacion del Producto</h4>
				</div><!-- Encabezado  -->
				<div class="modal-body">
					<label for="popCodigoProducto">Codigo Producto</label>
					<input id="popCodigoProducto" class="form-control">
					<label for="popDescripcionProducto">Descripcion de Producto</label>
				    <input id="popDescripcionProducto" class="form-control">
				   	<button type="button" class="btn btn-default" id="popBuscarProducto" name="popBuscarProducto">
                    	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                    <div id="tablaPOP">
	                    <table id="popDatosBusqueda" class="display">
	                    	<thead>
								<tr>
									<th  >Codigo Producto</th>
									<th  >Descripcion</th>
									<th >Unidad</th>
									<th >Unidad</th>
									<th >Cantidad</th>
								</tr>
							</thead>
							<tbody id="cuerpoDatosBusqueda">
							
							</tbody>
	                    </table>
	            	</div>
				</div><!-- Componentes  -->
			</div><!-- Contenido  -->
		</div><!-- Dialog  -->
	</div><!--Ventana Pop Up Buscar Producto -->
    
	<footer>
    	<h5 class="text-center"> ${usuarioGlobal}</h5>
    </footer>
    
    
    <script type="text/javascript" src="js/jquery-1.5.min.js"></script>
	<script type="text/javascript">
		var $jq = jQuery.noConflict();
	</script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	
	<script src="js/bootstrap.min.js"></script>
	<script src="js/scriptConteo1.js"></script>
	<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
	<script src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
</body>
</html>
		