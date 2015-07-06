/**
 *Aqui todas las funciones que conectan las paginas jsp con servlet por medio de jQuery
 */
(function($, window, document) {

   $(function() {
	   
	 //Ocultar Notificacion
	   $('#notificacion').hide();
	 //para sumar al darse el evento de escribir la cantidad a ingresar
	   $("#cantidad").keyup(function(){
	   	sumarCantidad();
	   });
	 //Buscar los datos del codigo de producto ingresado al presionar enter
	   $("#codigoProducto").keydown(function(e){
	   	if(e.keyCode==13 || e.keyCode==9){
	   		var seccion = document.getElementById("seccionA");
	   		var estanteria = document.getElementById("estanteria");
	   		var bodega = document.getElementById("bodega");
	   		if(bodega.value == null || bodega.value == "" || seccion.value == null || seccion.value == "" || estanteria.value == null || estanteria.value == ""){
	   			$("#notificacion").text("Necesita seleccionar bodega, seccion y estanteria");
	   		}else{
	   			
	   			$.get('AutocompletarCodigo',{
	   				codP:$("#codigoProducto").val(),
	   				codb:$("#bodega").val(),
	   				code:$("#estanteria").val(),
	   				cods:$("#seccionA").val()
	   				},function(responseJson) {
	   				   if(responseJson!=null){
	   				       $.each(responseJson, function(key, value) {
	   				            $("#codigoProducto").val(value['codigoProducto']);
	   				            $("#descripcion").val(value['descripProducto']);
	   				            $("#unidad").val(value['unidadMedida']);
	   				            $("#cantidadActual").val(parseInt(value['cantidad']));
	   						    
	   				       });
	   				       sumarCantidad();
	   				       }
	   				   
	   				   });
	   			$("#cantidad").focus();
	   			$('#notificacion').hide();
	   		}
	   	}
	   });
	   /*Tomar conteo 1 con el evento Enter en campo cantidad*/
	   $("#cantidad").keydown(function (e){
			if(e.keyCode==13){
				if($('#cantidad').val() === ''){
					$('#notificacion').text("debe ingresar al menos cero 0");
				}else{
					agregarAInventario();
				}
			}
	   });
	   /*Tomar Conteo 1 con el boton de "Agregar" */
	   $('#tomarInventario').click(function (event){
			var total =  parseInt($('#cantidadTotal').val());
				agregarAInventario();
	   });
	   /**Permitir solo numeros y signo menos en el campo de cantidad*/
	   $("#cantidad").keydown(function (e) {
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 189]) !== -1 ||
	            (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || 
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 return;
	        }
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	  });
	  /**capturar el evento de cambio de estanterias en el combo y ejecutar la funcion EsMuestra*/
	  $('#estanteria').change(function(){
		 var opcion = $(this).find('option:selected');
		 var idEst = $(opcion).val();
		 $.get('EsMuestra', {
			codigoe: idEst
			}, function(responseText) {
				$('#cantidad').val(responseText);
			});
		 $.get('BuscaSeccion', {
				codigoe: idEst
			}, function(responseJson) {
				
				if(responseJson!=null){
					var select = $("#seccionA");
					select.empty();
					select.append("<option value=''>Seccion</option>");
					$.each(responseJson, function(key, value) {
						select.append("<option value='" + value['codigoS'] + "'>" + value['descS'] + "</option>");
			       });
				}
			});
	  });
	  /**Boton para abrir la ventana emerfente*/
	  $("#popAbrir").click(function(event){
			$("#popCodigoProducto").val("");
			$("#popDescripcionProducto").val("");
			$("table").remove();
			var seccion = document.getElementById("seccion");
			var estanteria = document.getElementById("estanteria");
			var bodega = document.getElementById("bodega");
			if(bodega.value == null || bodega.value == "" || seccion.value == null || seccion.value == "" || estanteria.value == null || estanteria.value == ""){
				
				$("#popBuscaProd").modal("toggle");
				$("#notificacion").text("las busquedas necesitan bodega, seccion y estanteria");
			}
			
			
		});
	  
	  /**Buscar coincidencias de producto o descripcion del mismo en ventana emergente*/
	  $("#popBuscarProducto").click(function(event){
		  $("#tablaPOP").empty();
		  var codP = $('#popCodigoProducto').val();
		  var descP = $('#popDescripcionProducto').val();
		  var codB = $('#bodega').val();
		  var codE = $('#estanteria').val();
		  var codS = $('#seccionA').val();
			  $.get('BuscaProducto',{
				  codigo:codP,
				  desc:descP,
				  codb:codB,
				  code:codE,
				  cods:codS
				  },function(responseJson) {
			   if(responseJson!=null){
//			       $("#popDatosBusqueda").find("tr:gt(0)").remove();
				   var contenedor = $("#tablaPOP");
			       var table1 = $("<table id='popDatosBusqueda' class='display'></table>");
			       var rowHead = $("<tr> <th></th> <th></th> " +
			       		"<th></th> <th></th> <th></th> </tr>");
			       var head = $("<thead></thead>");
			       var tbody = $("<tbody></tbody>");
			       rowHead.children().eq(0).text("Codigo Producto");
			       rowHead.children().eq(1).text("Descripcion Producto");
			       rowHead.children().eq(2).text("Unidad de Medida");
			       rowHead.children().eq(3).text("Descripcion Unidad");
			       rowHead.children().eq(4).text("Cantidad");
			       rowHead.appendTo(head);
			       table1.appendTo(contenedor);
			       head.appendTo(table1);
			       tbody.appendTo(table1);
			       $.each(responseJson, function(key,value) {
//			    	   $('#popDatosBusqueda > tbody:last').append('<tr><td><a href="">'+value['codigoProducto']+'</a></td> <td>'+value['descripcionProducto']+'</td> <td>'+value['unidadMedida']+'</td> <td>'+value['descripcionUnidad']+'</td> <td>'+value['cantidad']+'</td></tr>');
			            var rowNew = $('<tr> <td><a href="#"> </a></td> <td></td> <td></td> <td></td> <td></td> </tr>');
			               rowNew.children().children().eq(0).text(value['codigoProducto']);
			               rowNew.children().eq(1).text(value['descripcionProducto']);
			               rowNew.children().eq(2).text(value['unidadMedida']);
			               rowNew.children().eq(3).text(value['descripcionUnidad']);
			               rowNew.children().eq(4).text(parseInt(value['cantidad']));
			               rowNew.appendTo($("table tbody"));
			       });
//			       $("#popDatosBusqueda").tablesorter();
			       $('#popDatosBusqueda').dataTable( {
				        "language": {
				            "url": "//cdn.datatables.net/plug-ins/1.10.7/i18n/Spanish.json"   	
				        }
			       	
				    });
			       }
			   });
//			  $("#tablaPOP").show();
	  });
	  
	  /**Llenar los campos: cantidad Actual, codigo, descripcion con datos de la tabla*/
	  $jq("table[id$='popDatosBusqueda'] td:nth-child(1)").live('click',function(event) 
				{  
				//Para evitar que el link actue.  
				event.preventDefault();  
				var $td= $(this).closest('tr').children('td');
				
				$("#codigoProducto").val($td.eq(0).text()); 
				$("#descripcion").val($td.eq(1).text()); 
				$("#unidad").val($td.eq(3).text());
				$("#cantidadActual").val($td.eq(4).text());
				
				$("#popBuscaProd").modal("toggle");
				sumarCantidad();
				}
				
			);
	  
   });/**Fin el document.ready()*/

   
   //funcion para sumar cantidad actual con cantidad ingresada
   function sumarCantidad(){
		var cantidadActual = parseInt($('#cantidadActual').val());
		var cantidadIngresada;
		if($('#cantidad').val() ==  ''){
			cantidadIngresada=0;
		}else{
			cantidadIngresada = parseInt($('#cantidad').val());
		}
		var total = $('#cantidadTotal').val((cantidadActual + cantidadIngresada));
   }
   
   //funcion que toma todos los valores de la pantalla 
	function agregarAInventario(){
		var codigoP=$('#codigoProducto').val();
		var codigoB=$('#bodega').val();
		var codigoE=$('#estanteria').val();
		var codigoS=$('#seccionA').val();
		var descripP=$('#descripcion').val();
		var unidadM=$('#unidad').val();
		var cantidadT=$('#cantidadTotal').val();
		$.post('AgregarProducto', {
			codigop : codigoP,
			codigob: codigoB,
			codigoe: codigoE,
			codigos: codigoS,
			descrip: descripP,
			unidad: unidadM,
			cantidad: cantidadT
		}, function(responseText) {
			$('#notificacion').show();
			$('#notificacion').text(responseText);
			$('#codigoProducto').val("");
			$('#descripcion').val("");
			$('#unidad').val("");
			$('#cantidadTotal').val("");
			esMuestra();
			$('#cantidadActual').val("");
		});
		
		$("#codigoProducto").focus();
		
	}
	//funcion que verifica si la estanteria seleccionada es muestra o no
	function esMuestra(){
		var opcion = $('#estanteria');
		var estanteriaID = opcion.val();
		$.get('EsMuestra', {
			codigoe: estanteriaID
		}, function(responseText) {
			$("#cantidad").val(responseText);
		});
	}
   
  }(window.jQuery, window, document));



