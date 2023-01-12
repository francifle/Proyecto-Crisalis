$(document).ready(function () {
 	$('.my-select').select2({
		placeholder:'Agregar impuestos',
		multiple:true,
		theme: 'bootstrap',
		ajax:{
			type:'GET',
			url:'../../ListRest/impuestos',
			dataType:'json',
			processResults: function(data){
				return{
					results: $.map(data, function(item){
						return {
							text: item.nombre,
							id: item.id
						}
					})
				};
			}
		}
	});
});