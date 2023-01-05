$(document).ready(function () {
 	$('.my-select').select2({
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
	});
});