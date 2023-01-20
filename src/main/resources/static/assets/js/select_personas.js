$(document).ready(function() {
	$('#integrantes-new').empty();
	var href = "http://localhost:8080/ListRest/integrantes/0"
	$.get(href, function(personas, status) {
		for (var i = 0; i <= personas.length - 1; i++) {
			$('#integrantes-new').append('<option value="' + personas[i].id + '">' + personas[i].nombreFisico + '</option>');
			$('#integrantes-new').selectpicker('refresh');
		}
	});
	//$('#integrantes-new').empty();
	let listSelect = $('select');
	for (var i = 0; i <= listSelect.length - 1; i++) {
		var href = "http://localhost:8080/ListRest/integrantes/0"
		let select = $(listSelect[i]);
		if (select.hasClass('integrantesEdit')) {
			$.get(href, function(personas, status) {
				for (var i = 0; i <= personas.length - 1; i++) {
					//if (($("#integrantes-edit option[value=" + personas[i].id + "]").length === 0))
					select.append('<option value="' + personas[i].id + '">' + personas[i].nombreFisico + '</option>');
					select.selectpicker('refresh');
				}
			});
			select.change(function() {
				let values = '';
				let valuesSelected = $('option:selected', this);
				for (var i = 0; i <= valuesSelected.length - 1; i++) {
					values += valuesSelected[i].value + ";";
				}
				values = values.slice(0, -1);
				$(this).closest("div.form-group").find("input")[1].value = values

			})
			/*let values = '';
			let valuesSelected = $('option:selected', select);
			for (var i = 0; i <= valuesSelected.length - 1; i++) {
				values += valuesSelected[i].value + ";";
			}
			values = values.slice(0, -1);
			select.closest("div.form-group").find("input")[1].value = values*/

		}
	}
});
