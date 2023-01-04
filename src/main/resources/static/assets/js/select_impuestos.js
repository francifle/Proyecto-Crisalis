$(document).ready(function () {
 	$('.my-select').select2({
		placeholder:'Agregar impuestos',
		multiple:true,
		ajax:{
			type:'GET',
			url:'../../ListRest/impuestos',
			dataType:'json',
			processResults: function(data){
				return{
					results: $.map(data, function(item){
						console.log(item)
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