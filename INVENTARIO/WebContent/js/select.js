(function($, window, document) {

  
 $(function() {

   // The DOM is ready!
	 $("#noToma").change(function() {
		 var mitexto = $("#noToma option:selected").text()
		 $("#texto").val($("#noToma option:selected").text());
		});
 });
 //resto del codigo
}(window.jQuery, window, document));