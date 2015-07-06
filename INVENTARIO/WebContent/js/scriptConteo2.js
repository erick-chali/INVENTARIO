(function($, window, document) {

    
   $(function() {
	   $('#notificacion').hide();
		/**Ir a buscar datos del codigo ingresado en toma 2*/
		   $("#codigoToma2").keydown(function(e){

				
				if(e.keyCode==13 || e.keyCode==9){
					$('#tablaDatosToma2').empty();
					var codP = $(this).val();
					var texto = $("#txtToma").text();
					var codB = separarTexto(texto);
					$.get('CargarProductosDiferencia',
							{codigoP:codP,codigoB:codB},
							function(responseJson) {
						   if(responseJson!=null){
							   
							   var contenedor = $("#tablaDatosToma2");
							   var table1 = $("<table id='datosToma2' class='table table-striped table-bordered table-condensed'></table>");
						       var thead = $("<thead></thead>");
						       var tbody = $("<tbody></tbody>");
						       var rowHead = $("<tr> <th></th> <th></th> <th></th> <th></th> <th></th> <th></th> </tr>");
						       rowHead.children().eq(0).text("Codigo Producto");
						       rowHead.children().eq(1).text("Cantidad Actual");
						       rowHead.children().eq(2).text("Cantidad");
						       rowHead.children().eq(3).text("Bodega");
						       rowHead.children().eq(4).text("Estanteria");
						       rowHead.children().eq(5).text("Seccion");
						       
						       rowHead.appendTo(thead);
						       table1.appendTo(contenedor);
						       
						       thead.appendTo(table1);
						       tbody.appendTo(table1);
						       
						       $.each(responseJson, function(key,value) {
						    	   
						    	   $("#codigoToma2").val(value['codP'])
					               $("#descripcion").val(value['descP']);
					               $("#unidad").val(value['uniP']);
						            var rowNew = $("<tr> <td><a href='#'></a></td> <td></td> <td><input type='number' id='cantidad' value='0'><button  type='button' id='conteo2'>Contar</button></td> <td></td> <td></td> <td></td></tr>");
						               rowNew.children().children().eq(0).text(value['codP']);
						               rowNew.children().eq(1).text(parseFloat(value['cantidad']).toFixed(1));
						               rowNew.children().eq(3).text(value['bodega']);
						               rowNew.children().eq(4).text(value['estanteria']);
						               rowNew.children().eq(5).text(value['seccion']);
						               rowNew.appendTo($("table tbody"));
						               
						               
						       });
						       
						       $('#datosToma2').dataTable( {
						    	   "scrollY" : 200,
						    	   "scrollX" : true,
							        "language": {
							            "url": "//cdn.datatables.net/plug-ins/1.10.7/i18n/Spanish.json"   	
							        }
						       	
							    });
						       
						       }
						   });
				}
			});
		   /**Darle accion al boton para actualizar cantidad*/
		   $jq("button[id$='conteo2']").live('click',function(event){
				
			   
				   var $td= $(this).closest('tr').children('td');
					  var $td2= $(this).closest('tr').children('td').children('input');
					  $.post('Conteo2', {
							codigop: $td.eq(0).text(),
							codigob: $td.eq(3).text(),
							codigoe: $td.eq(4).text(),
							codigos: $td.eq(5).text(),
							unidad: $('#unidad').val(),
							cantidad: $td2.eq(0).val()
						}, function(responseText) {
							$td.eq(1).text(responseText)
							$td2.eq(0).val('0');
						});
			   
				  
					
				});
		   /**Poder cambiar la cantidad al presionar enter*/
		   $jq("input[id$='cantidad']").live('keydown',function(e){
				
			   if(e.keyCode==13){
				  var $td= $(this).closest('tr').children('td');
				  var $td2= $(this).closest('tr').children('td').children('input');
				  $.post('Conteo2', {
						codigop: $td.eq(0).text(),
						codigob: $td.eq(3).text(),
						codigoe: $td.eq(4).text(),
						codigos: $td.eq(5).text(),
						unidad: $('#unidad').val(),
						cantidad: $td2.eq(0).val()
					}, function(responseText) {
						$td.eq(1).text(responseText)
						$td2.eq(0).val('0');
					});
		   		}
					
				});
		   /**Poder cambiar la cantidad al presionar enter*/
		   $jq("input[id$='cantidad']").live('focus',function(event){
				
			   $(this).val('');	
		   });
		   /**Poder cambiar la cantidad al presionar enter*/
		   $jq("input[id$='cantidad']").live('focusout',function(event){
				
			   $(this).val('0');	
		   });
		   

   });/**Fin de document.ready*/
   
   //Funcion para separar el texto de la toma y obtener #bodega
   function separarTexto(codigo){
		var cadena = codigo;
	    var particiones = cadena.split(' ');
	    
	    return particiones[4];
	}
   

  }(window.jQuery, window, document));
