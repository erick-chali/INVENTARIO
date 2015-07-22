(function($, window, document) {

    
   $(function() {
	   setTimeout(function(){
		   verificarP1();
	    }, 100);
	   setTimeout(function(){
		   verificarP2();
	    }, 200);
	   setTimeout(function(){
		   verificarP3();
	    }, 300);
	   setTimeout(function(){
		   verificarP4();
	    }, 400);
	   setTimeout(function(){
		   verificarP5();
	    }, 500);
	   $('#notificacion').hide();
	   /**Limpiar la caja de codigo de cualquier texto*/
	   $("#codigoToma2").focus(function (){
		   $(this).val("");
	   });
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
						            var rowNew = $("<tr> <td><a href='#'></a></td> <td></td> <td><input type='number' id='cantidad' value='0'><button  type='button' id='conteo'>Contar</button></td> <td></td> <td></td> <td></td></tr>");
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
						       $("#codigoToma2").focusout();
						       }
						   });
				}
			});
		   /**Darle accion al boton para actualizar cantidad*/
		   $jq("button[id$='conteo']").live('click',function(event){
				
			   
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
			   if ($.inArray(e.keyCode, [13, 8, 9, 27, 46, 110, 189]) !== -1 ||
			            (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || 
			            (e.keyCode >= 35 && e.keyCode <= 40)) {
			                 return;
			   }
			   if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
			            e.preventDefault();
			   }	
				});
		   /**Poder cambiar la cantidad al presionar enter*/
		   $jq("input[id$='cantidad']").live('focus',function(event){
				
			   $(this).val('');	
		   });
		   /**Poder cambiar la cantidad al presionar enter*/
		   $jq("input[id$='cantidad']").live('focusout',function(event){
			   if($("#cantidad").val()==null){
				   $(this).val('0');
			   }
			   	
		   });
		   

   });/**Fin de document.ready*/
 //funcion que verifica los permisos del usuario
   function verificarP1(id){
	   $.get('Permisos',{
			opcion:$('#conteo1').get(0).id
			},function(responseJson) {
				if(responseJson!=null){
					$.each(responseJson, function(key, value) { 
						if(parseInt(value['permiso'])==1){
							$('#conteo1').show();
						}else{
							$('#conteo1').hide();
						}
				    });
				}
		});
   }
   function verificarP2(id){
	   $.get('Permisos',{
			opcion:$('#conteo2').get(0).id
			},function(responseJson) {
				if(responseJson!=null){
					$.each(responseJson, function(key, value) {
						if(parseInt(value['permiso'])==1){
							$('#conteo2').show();
						}else{
							$('#conteo2').hide();
						}
				    });
				}
		});
   }
   function verificarP3(id){
	   $.get('Permisos',{
			opcion:$('#inventario').get(0).id
			},function(responseJson) {
				if(responseJson!=null){
					$.each(responseJson, function(key, value) {
						if(parseInt(value['permiso'])==1){
							$('#inventario').show();
						}else{
							$('#inventario').hide();
						}
				    });
				}
		});
   }
   function verificarP4(id){
	   $.get('Permisos',{
			opcion:$('#auditoria').get(0).id
			},function(responseJson) {
				if(responseJson!=null){
					$.each(responseJson, function(key, value) {
						if(parseInt(value['permiso'])==1){
							$('#auditoria').show();
						}else{
							$('#auditoria').hide();
						}
				    });
				}
		});
   }
   function verificarP5(id){
	   $.get('Permisos',{
			opcion:$('#diferencia').get(0).id
			},function(responseJson) {
				if(responseJson!=null){
					$.each(responseJson, function(key, value) {
						if(parseInt(value['permiso'])==1){
							
							$('#diferencia').show();
						}else{
							$('#diferencia').hide();
						}
				    });
				}
		});
   }
   //Funcion para separar el texto de la toma y obtener #bodega
   function separarTexto(codigo){
		var cadena = codigo;
	    var particiones = cadena.split(' ');
	    
	    return particiones[4];
	}
   

  }(window.jQuery, window, document));
