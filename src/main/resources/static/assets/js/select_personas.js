$(document).ready(function () {
 	/*$('.my-select').select2({
		placeholder:'Agregar personas',
		multiple:true,
		ajax:{
			type:'GET',
			url:'../../ListRest/personas',
			dataType:'json',
			processResults: function(data){
				return{
					results: $.map(data, function(item){
						return {
							text: item.nombreCompleto,
							id: item.id
						}
					})
				};
			}
		}
	});*/
	$('#integrantes').empty();
	var href = "http://localhost:8080/ListRest/integrantes/0"
	$.get(href, function(personas, status) {
		for (var i = 0; i <= personas.length - 1; i++) {
			$('#integrantes').append('<option value="' + personas[i].id + '">' + personas[i].nombreFisico + '</option>');
			$('#integrantes').selectpicker('refresh');
		}
	})
});