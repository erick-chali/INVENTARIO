(function($, window, document) {

  
 $(function() {
	 
   // The DOM is ready!
	 $('#btnInicia').click(function(){
			$.ajax({
				   type : 'POST',
				   url: 'ServletLogin',
				   dataType: 'text',
				   data: {
				      usuario : $('#usuario').val(),
				      clave : $('#clave').val(),
				      noToma : $('#noToma').val(),
				      texto : $("#noToma option:selected").text(),
				      
				   },
				   error: function(data) {
				      
				   },
				   success: function(data) {
					   if(data == "1"){
						   location.href = 'agregar.jsp'
					   }else if(data == '0'){
						   alert('Usuario o clave incorrectos');
					   }else if(data == '2'){
						   alert('Usuario no esta activo, avocarse con admin de sistema.');
					   }
				   }
				});
		});
 });
 //resto del codigo
}(window.jQuery, window, document));