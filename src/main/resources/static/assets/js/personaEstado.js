$(document).ready(function() {
	$('.changeEstado').each(function(f) {
		let btn = $(this);
		ChangeEstadoStyle(btn, btn.text() == "Anular");
	});
	$('.changeEstado').click(function() {
		let personaId = $(this).closest('td').find("input[name='personaId']")[0].value;
		let btn = $(this);
		$.ajax({
			url: "/PersonaRest/changeEstado/" + personaId,
			type: "GET",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: true,
			success: function(data) {
				ChangeEstadoStyle(btn, data.estado);
			}
		});
	});
});

function ChangeEstadoStyle(btn, check) {
	if (check) {
		btn.closest("tr").find("td:eq(1)").css("text-decoration", "none");
		btn.closest("tr").find("td:eq(2)").css("text-decoration", "none");
		btn.closest("tr").addClass("text-success");
		btn.closest("tr").removeClass("text-danger");
		btn.text("Anular");
		btn.closest("div .btn-group").find("button").text("Activado");
	} else {
		btn.closest("tr").find("td:eq(1)").css("text-decoration", "line-through");
		btn.closest("tr").find("td:eq(2)").css("text-decoration", "line-through");
		btn.closest("tr").addClass("text-danger");
		btn.closest("tr").removeClass("text-success");
		btn.text("Activar");
		btn.closest("div .btn-group").find("button").text("Anulado");
	}
}