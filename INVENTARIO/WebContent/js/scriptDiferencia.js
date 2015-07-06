(function($, window, document) {

   
   $(function() {
	   /**Darle accion a los productos y que los muestre en todas las ubicaciones*/
	   $jq("table[id$='datosDiferencia'] td:nth-child(1)").live('click',function(event) 
				{
				//Para evitar que el link actue.  
				event.preventDefault();  
				var $td= $(this).closest('tr').children('td');
				
				var codProd = $td.eq(0).text();  
				var codBodega =$td.eq(3).text();
				$('#popProductosDiferencias tr').each(function(){
					 $(this).remove();
				  });
				$.get('CargarProductosDiferencia',
						{codigoP:codProd,codigoB:codBodega},
						function(responseJson) {
					   if(responseJson!=null){
					       $("#popProductosDiferencias").find("tr:gt(0)").remove();
					       var table1 = $("#popProductosDiferencias");
					       var rowHead = $("<tr> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th></tr>");
					       rowHead.children().eq(0).text("Codigo Producto");
					       rowHead.children().eq(1).text("Descripcion Producto");
					       rowHead.children().eq(2).text("Unidad de Medida");
					       rowHead.children().eq(3).text("Cantidad");
					       rowHead.children().eq(4).text("Bodega");
					       rowHead.children().eq(5).text("Seccion");
					       rowHead.children().eq(6).text("Estanteria");
					       rowHead.children().eq(6).text("C. S.");
					       rowHead.children().eq(6).text("C. E.");
					       
					       rowHead.appendTo(table1);
					       $.each(responseJson, function(key,value) {
					            var rowNew = $("<tr> <td><a href='#'></a></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
					               rowNew.children().children().eq(0).text(value['codP']);
					               rowNew.children().eq(1).text(value['descP']);
					               rowNew.children().eq(2).text(value['uniP']);
					               rowNew.children().eq(3).text(parseInt(value['cantidad']));
					               rowNew.children().eq(4).text(value['bodega']);
					               rowNew.children().eq(5).text(value['seccion']);
					               rowNew.children().eq(6).text(value['estanteria']);
					               rowNew.children().eq(7).text(value['cs']);
					               rowNew.children().eq(8).text(value['ce']);
					               rowNew.appendTo(table1);
					       });
					       }
					   });
					  $("#popProductosDiferencias").show();
				}
				
			);
	   /**Pasar los valores de la tabla de la ventana emergente a los campos*/
	   $jq("table[id$='popProductosDiferencias'] td:nth-child(1)").live('click',function(event) 
				{
				//Para evitar que el link actue.  
				event.preventDefault();  
				var $td= $(this).closest('tr').children('td');
				
				
				$("#codigoProducto").val($td.eq(0).text()); 
				$("#descripcion").val($td.eq(1).text());
				$("#seccion").val($td.eq(7).text());
				$("#estanteria").val($td.eq(8).text());
				$("#bodega").val($td.eq(4).text());
				
				$("#unidad").val($td.eq(2).text());
				
				$("#cantidadActual").val($td.eq(3).text());
				
				sumarCantidad();
				
				}
				
			);
	   /**Sumar al presionar numero*/
	   $("#cantidad").keyup(function(){
			sumarCantidad();
		});
	   /**Validar solo numeros*/
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
	 /**Actualizar Cantidad con boton*/
		$('#actualizarProd').click(function (event){
			var total =  parseInt($("#cantidadTotal").val());
			if(total<0){
				$("#notificacion").text("La cantidad total debe ser menor o igual a cero");
			}else{
				ActualizarProd();
			}
			
		});
		/**Actualizar Cantidad con enter en campo cantidad*/
		$("#cantidad").keydown(function (e){
			if(e.keyCode==13){
				ActualizarProd();
				
			}
		});
	   
   });/**Fin de document.ready()*/
   
   /**FUNCIONES UTLIZADAS*/
   //funcion de sumar cantidades
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
   //actualizar la cantidad de lo seleccionado
   function ActualizarProd(){
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
	}
   
  }(window.jQuery, window, document));