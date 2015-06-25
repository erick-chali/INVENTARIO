$(document).ready(function(){
	cargarTabla();
	var d = new Date();
    var month = d.getMonth()+1;
    var day = d.getDate();
    var output = 
        ((''+day).length<2 ? '0' : '') + day + '/' +
        ((''+month).length<2 ? '0' : '') + month + '/' +
        d.getFullYear();
	$("#hoy").text(output);
	//validar Campos
	
	
	
	$("#popAbrir").click(function(event){
		$("#popCodigoProducto").val("");
		$("#popDescripcionProducto").val("");
		var seccion = document.getElementById("seccion");
		var estanteria = document.getElementById("estanteria");
		var bodega = document.getElementById("bodega");
		if(bodega.value == null || bodega.value == "" || seccion.value == null || seccion.value == "" || estanteria.value == null || estanteria.value == ""){
			$('#popDatosBusqueda tr').each(function(){
				 $(this).remove();
			  });
			$("#popBuscaProd").modal("toggle");
			$("#notificacion").text("las busquedas necesitan bodega, seccion y estanteria");
		}
		
		
	});
	
	//Buscar datos en ventana POP
  $("#popBuscarProducto").click(function(event){
		  $('#popDatosBusqueda tr').each(function(){
				 $(this).remove();
			  });
		  var codP = $('#popCodigoProducto').val();
		  var descP = $('#popDescripcionProducto').val();
			  $.get('BuscaProducto',{codigo:codP,desc:descP},function(responseJson) {
			   if(responseJson!=null){
			       $("#popDatosBusqueda").find("tr:gt(0)").remove();
			       var table1 = $("#popDatosBusqueda");
			       var rowHead = $("<tr><th></th><th></th><th></th><th></th></tr>");
			       rowHead.children().eq(0).text("Codigo Producto");
			       rowHead.children().eq(1).text("Descripcion Producto");
			       rowHead.children().eq(2).text("Unidad de Medida");
			       rowHead.children().eq(3).text("Descripcion Unidad");
			       
			       rowHead.appendTo(table1);
			       $.each(responseJson, function(key,value) {
			            var rowNew = $("<tr><td><a href='#'></a></td><td></td><td></td><td></td></tr>");
			               rowNew.children().children().eq(0).text(value['codigoProducto']);
			               rowNew.children().eq(1).text(value['descripcionProducto']);
			               rowNew.children().eq(2).text(value['unidadMedida']);
			               rowNew.children().eq(3).text(value['descripcionUnidad']);
			               rowNew.appendTo(table1);
			       });
			       }
			   });
			  $("#tablaPOP").show();
});
  
  //cargar tabla al cargar pagina en buscar
//  $('#datosBusqueda tr').each(function(){
//		 $(this).remove();
//	  });
//  $.get('CargarProductos',function(responseJson) {
//	   if(responseJson!=null){
//	       $("#datosBusqueda").find("tr:gt(0)").remove();
//	       var table1 = $("#datosBusqueda");
//	       var rowHead = $("<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr>");
//	       rowHead.children().eq(0).text("Codigo Producto");
//	       rowHead.children().eq(1).text("Descripcion");
//	       rowHead.children().eq(2).text("Cantidad Existente");
//	       rowHead.children().eq(3).text("Unidad");
//	       rowHead.children().eq(4).text("Bodega");
//	       rowHead.children().eq(5).text("Seccion");
//	       rowHead.children().eq(6).text("Estanteria");
//	       rowHead.children().eq(7).text("Cod. Bod.");
//	       rowHead.children().eq(8).text("Cod. Sec.");
//	       rowHead.children().eq(9).text("Cod. Est.");
//	       
//	       rowHead.appendTo(table1);
//	       $.each(responseJson, function(key,value) {
//	            var rowNew = $("<tr><td><a href='#' data-toggle='modal' data-target='#popEditar'></a></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
//	               rowNew.children().children().eq(0).text(value['codP']);
//	               rowNew.children().eq(1).text(value['descP']);
//	               rowNew.children().eq(2).text(value['conteo']);
//	               rowNew.children().eq(3).text(value['unidad']);
//	               rowNew.children().eq(4).text(value['descB']);
//	               rowNew.children().eq(5).text(value['descS']);
//	               rowNew.children().eq(6).text(value['descE']);
//	               rowNew.children().eq(7).text(value['codB']);
//	               rowNew.children().eq(8).text(value['codS']);
//	               rowNew.children().eq(9).text(value['codE']);
//	               rowNew.appendTo(table1);
//	       });
//	       }
//	   });
  
  //para darle accion a la tabla que se carga automaticamente en busqueda
  $jq("table[id$='datosBusqueda'] td:nth-child(1)").live('click',function(event) 
		{  
		//prevenir que se ejecute la accion del link  
		event.preventDefault();  
		var $td= $(this).closest('tr').children('td');  
		var codProd = $td.eq(0).text();  
		var descProd = $td.eq(1).text();
		var conteo1 = $td.eq(2).text();
		var unidad = $td.eq(3).text();
		var bodegaProd = $td.eq(7).text();
		var seccionId = $td.eq(8).text();
		var estanteriaId = $td.eq(9).text();
		
		$("#codigoProducto").val(codProd); 
		$("#descripcion").val(descProd);
		$("#seccion option[value="+seccionId+"]").attr("selected",true);
		$("#estanteria option[value="+estanteriaId+"]").attr("selected",true);
		$("#bodega option[value="+bodegaProd+"]").attr("selected",true);
		$("#unidad").val(unidad);
		$("#cantidadActual").val(conteo1);
		}  
	);
	
  //tabla dentro de la ventana emergente
	$jq("table[id$='popDatosBusqueda'] td:nth-child(1)").live('click',function(event) 
			{  
			//Para evitar que el link actue.  
			event.preventDefault();  
			var $td= $(this).closest('tr').children('td');
			
			var codProd = $td.eq(0).text();  
			var descProd = $td.eq(1).text();
			var descUnidad = $td.eq(3).text();
			
			$("#codigoProducto").val(codProd); 
			$("#descripcion").val(descProd); 
			$("#unidad").val(descUnidad);

			var codEst = $("#estanteria").val();
			var codSec = $("#seccion").val();
			var codProd = $("#codigoProducto").val();
			var codBod = $("#bodega").val();
			$.get('CantidadActual',{codE:codEst,codS:codSec,codP:codProd,codB:codBod},function(responseJson) {
				if(responseJson!=null){
			       $.each(responseJson, function(key, value) {
			            $('#cantidadActual').val(value['conteo']);
			       });
				}
			   });
			$("#popBuscaProd").modal("toggle");
			}
			
		);
	//tomar codigo bodega y codigo producto tabla: datosDiferencia
	
	$jq("table[id$='datosDiferencia'] td:nth-child(1)").live('click',function(event) 
			{
			//Para evitar que el link actue.  
			event.preventDefault();  
			var $td= $(this).closest('tr').children('td');
			var $td2= $(this).closest('tr').children('td').children('div');
			
			var codProd = separarCodigo($td2.eq(0).text());  
			var codBodega = separarCodigo($td.eq(1).text());
			$('#popProductosDiferencias tr').each(function(){
				 $(this).remove();
			  });
			$.get('CargarProductosDiferencia',{codigoP:codProd,codigoB:codBodega},function(responseJson) {
				   if(responseJson!=null){
				       $("#popProductosDiferencias").find("tr:gt(0)").remove();
				       var table1 = $("#popProductosDiferencias");
				       var rowHead = $("<tr> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th></tr>");
				       rowHead.children().eq(0).text("Codigo Producto");
				       rowHead.children().eq(1).text("Descripcion Producto");
				       rowHead.children().eq(2).text("Unidad de Medida");
				       rowHead.children().eq(3).text("Cantidad");
				       rowHead.children().eq(4).text("Bodega");
				       rowHead.children().eq(5).text("Seccion");
				       rowHead.children().eq(6).text("Estanteria");
				       
				       rowHead.appendTo(table1);
				       $.each(responseJson, function(key,value) {
				            var rowNew = $("<tr> <td><a href='#'></a></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
				               rowNew.children().children().eq(0).text(value['codP']);
				               rowNew.children().eq(1).text(value['descP']);
				               rowNew.children().eq(2).text(value['uniP']);
				               rowNew.children().eq(3).text(value['cantidad']);
				               rowNew.children().eq(4).text(value['bodega']);
				               rowNew.children().eq(5).text(value['seccion']);
				               rowNew.children().eq(6).text(value['estanteria']);
				               rowNew.appendTo(table1);
				       });
				       }
				   });
				  $("#popProductosDiferencias").show();
			}
			
		);
	
	//editar de la tabla popProductosDiferencia
	
	$jq("table[id$='popProductosDiferencias'] td:nth-child(1)").live('click',function(event) 
			{
			//Para evitar que el link actue.  
			event.preventDefault();  
			var $td= $(this).closest('tr').children('td');
			var codProd = $td.eq(0).text();  
			var descProd = $td.eq(1).text();
			var unidad = $td.eq(2).text();
			var cantidad = $td.eq(3).text();
			var bodega = $td.eq(4).text();
			var seccion = $td.eq(5).text();
			var estanteria = $td.eq(6).text();
			
			$("#codigoProducto").val(codProd); 
			$("#descripcion").val(descProd);
			$("#seccion option[value="+seccion+"]").attr("selected",true);
			$("#estanteria option[value="+estanteria+"]").attr("selected",true);
			$("#bodega option[value="+bodega+"]").attr("selected",true);
			$("#unidad").val(unidad);
			$("#cantidadActual").val(cantidad);
			}
			
		);
	
	
	
	
	//mensaje cantidad sumada
	$("#cantidad").keyup(function(){
		var cantidadActual = parseInt($('#cantidadActual').val());
		var cantidadIngresada = parseInt($('#cantidad').val());
		var total = $('#cantidadTotal').val((cantidadActual + cantidadIngresada));
	});
	
	//funcion autocompletar al presionar tab
	$("#codigoProducto").keydown(function(e){
		if(e.keyCode==13 || e.keyCode==9){
			var seccion = document.getElementById("seccion");
			var estanteria = document.getElementById("estanteria");
			var bodega = document.getElementById("bodega");
			if(bodega.value == null || bodega.value == "" || seccion.value == null || seccion.value == "" || estanteria.value == null || estanteria.value == ""){
				$("#notificacion").text("Necesita seleccionar bodega, seccion y estanteria");
			}else{
				var cod = $("#codigoProducto").val();
				$('#codigoProducto').clone().prop('type','text').insertAfter('#codigoProducto').prev().remove();
				$.get('AutocompletarCodigo',{codP:cod},function(responseJson) {
					   if(responseJson!=null){
					       $.each(responseJson, function(key, value) {
					            $("#codigoProducto").val(value['codigoProducto']);
					            $("#descripcion").val(value['descripProducto']);
					            $("#unidad").val(value['unidadMedida']);
					       });
					       }
					   });
				var codEst = $("#estanteria").val();
				var codSec = $("#seccion").val();
				var codProd = $("#codigoProducto").val();
				var codBod = $("#bodega").val();
				$.get('CantidadActual',{codE:codEst,codS:codSec,codP:codProd,codB:codBod},function(responseJson) {
					   if(responseJson!=null){
					       $("#popDatosBusqueda").find("tr:gt(0)").remove();
					       var table1 = $("#popDatosBusqueda");
					       $.each(responseJson, function(key, value) {
					            $("#cantidadActual").val(value['conteo']);
					       });
					       }
					   $("#cantidad").focus();
					   });
				
			}
		}
	});
	//para ir a boton agregar
	$("#cantidad").keydown(function (e){
		if(e.keyCode==13){
			$("#tomarInventario").focus();
		}
	});
	
	//funcion autocompletar al presionar tab Buscar Producto
	$("#codigoProducto1").keydown(function(e){
		if(e.keyCode==13){
			var cod = $("#codigoProducto1").val();
			$.get('AutocompletarCodigo',{codP:cod},function(responseJson) {
				   if(responseJson!=null){
				       
				       $.each(responseJson, function(key, value) {
				            $("#codigoProducto").val(value['codigoProducto']);
				            $("#descripcion").val(value['descripProducto']);
				            $("#unidad").val(value['unidadMedida']);
				       });
				       }
				   });
		$("#popEditar").modal();
		}
	});
	//tomar Inventario
	$('#tomarInventario').click(function (event){
		var total =  parseInt($('#cantidadTotal').val());
		if(total<0){
			$('#notificacion').text('La cantidad total debe ser menor o igual a cero');
		}else{
			var codigoP=$('#codigoProducto').val();
			var codigoB=$('#bodega').val();
			var codigoE=$('#estanteria').val();
			var codigoS=$('#seccion').val();
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
				$('#notificacion').text(responseText);
				$('#codigoProducto').val("");
				$('#descripcion').val("");
				$('#unidad').val("");
				$('#cantidadTotal').val("");
				$('#cantidad').val("");
				$('#cantidadActual').val("");
			});
			$('#codigoProducto').clone().prop('type','number').insertAfter('#codigoProducto').prev().remove();
			$("#codigoProducto").focus();
			
		}
		
		
	});
	
	//Actualizar Inventario
	$('#actualizarProd').click(function (event){
		var total =  parseInt($("#cantidadTotal").val());
		if(total<0){
			$("#notificacion").text("La cantidad total debe ser menor o igual a cero");
		}else{
			var codigoP=$('#codigoProducto').val();
			var codigoB=$('#bodega').val();
			var codigoE=$('#estanteria').val();
			var codigoS=$('#seccion').val();
			var descripP=$('#descripcion').val();
			var unidadM=$('#unidad').val();
			var cantidadT=$('#cantidadTotal').val();
			$.post('ActualizarProducto', {
				codigop : codigoP,
				codigob: codigoB,
				codigoe: codigoE,
				codigos: codigoS,
				descrip: descripP,
				unidad: unidadM,
				cantidad: cantidadT
			}, function(responseText) {
				$('#notificacion').text(responseText);
				$('#codigoProducto').val("");
				$('#descripcion').val("");
				$('#unidad').val("");
				$('#cantidad').val("");
				$('#cantidadTotal').val("");
				$('#cantidadActual').val("");
				$("#bodega option[value="+""+"]").attr("selected",true);
				$("#estanteria option[value="+""+"]").attr("selected",true);
				$("#seccion option[value="+""+"]").attr("selected",true);
				
			});
			location.reload();
		}
		
	});
	function valoresCombo(){
		var bodega;
		var seccion;
		var estanteria;
	}
	function cargarTabla(){
		//limpiar y volver a cargar la tabla
		//cargar tabla al cargar pagina en buscar
		  $('#datosBusqueda tr').each(function(){
			  $(this).remove();
		  });
		  $.get('CargarProductos',function(responseJson) {
			   if(responseJson!=null){
			       $("#datosBusqueda").find("tr:gt(0)").remove();
			       var table1 = $("#datosBusqueda");
			       var rowHead = $("<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr>");
			       rowHead.children().eq(0).text("Codigo Producto");
			       rowHead.children().eq(1).text("Descripcion");
			       rowHead.children().eq(2).text("Cantidad");
			       rowHead.children().eq(3).text("Unidad");
			       rowHead.children().eq(4).text("Bodega");
			       rowHead.children().eq(5).text("Seccion");
			       rowHead.children().eq(6).text("Estanteria");
			       rowHead.children().eq(7).text("Cod. Bod.");
			       rowHead.children().eq(8).text("Cod. Sec.");
			       rowHead.children().eq(9).text("Cod. Est.");
			       
			       rowHead.appendTo(table1);
			       $.each(responseJson, function(key,value) {
			    	   var rowNew = $("<tr><td><a href='#' data-toggle='modal' data-target='#popEditar'></a></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
			               rowNew.children().children().eq(0).text(value['codP']);
			               rowNew.children().eq(1).text(value['descP']);
			               rowNew.children().eq(2).text(value['conteo']);
			               rowNew.children().eq(3).text(value['unidad']);
			               rowNew.children().eq(4).text(value['descB']);
			               rowNew.children().eq(5).text(value['descS']);
			               rowNew.children().eq(6).text(value['descE']);
			               rowNew.children().eq(7).text(value['codB']);
			               rowNew.children().eq(8).text(value['codS']);
			               rowNew.children().eq(9).text(value['codE']);
			               rowNew.appendTo(table1);
			       });
			       }
			   
			   });
		  
	}
	
	
	
	//funcion cuando autocompletar on focus
	$( "#descripcion" ).focus(function() {
		var cod = $("#codigoProducto").val();
		$.get('AutocompletarCodigo',{codP:cod},function(responseJson) {
			   if(responseJson!=null){
			       
			       $.each(responseJson, function(key, value) {
			            $("#codigoProducto").val(value['codigoProducto']);
			            $("#descripcion").val(value['descripProducto']);
			            $("#unidad").val(value['unidadMedida']);
			       });
			       }
			   });
		
		});
	//Funcion para validar solo numeros
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
	 
	//funcion split
	function separarCodigo(codigo){
		var cadena = codigo;
	    var particiones = cadena.split(' ');
	    
	    return particiones[0];
	}
	
	$( "input[type=text]" ).focus(function() {
		$( this ).css({'background-color':'F5FFD6'});
	});
});
	  