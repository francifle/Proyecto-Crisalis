$(document).ready(function() {
	$("#confirmar").click(function() {
		let check = true;
		if ($("#persona").val() == "") {
			$("#sinPersonaModal").modal("show");
			check = false;
		}
		else if ($("#fecha").val() == "") {
			$("#sinFechaModal").modal("show");
			check = false;
		}
		else if ($("#tablaticket > tbody >tr").length === 0) {
			$("#sinItemModal").modal("show");
			check = false;
		}

		if (check) {
			$('#form').submit();
		}
	})

	$("#confirmarEdit").click(function() {
		let check = true;
		if ($("#personaEdit").val() == "") {
			$("#sinPersonaModal").modal("show");
			check = false;
		}
		else if ($("#fechaEdit").val() == "") {
			$("#sinFechaModal").modal("show");
			check = false;
		}
		else if ($("#tablaticketEdit > tbody >tr").length === 0) {
			$("#sinItemModal").modal("show");
			check = false;
		}
		if (check) {
			$('#formEdit').submit();
		}
	})

	/*-----------------------------------------------Empresa------------------------------------------------*/

	$("#agregarEmpresa").click(function() {
		let check = true;
		if ($("#razonSocial").val() == "" || $("#cuit").val() == "" || $("#fecha").val() == "" || $("#integrantes-new").val().length === 0) {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if ($("#cuit").val().length != 11 && isNaN($("#cuit").val())) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$('#empresaForm').submit();
		}
	})

	$(".agregarEmpresaEdit").click(function() {
		let check = true;
		let empresaId = $(this).closest("div .modal-content").find("input[name='razonsocial']").val();
		let cuit = $(this).closest("div .modal-content").find("input[name='cuit']").val();
		let fecha = $(this).closest("div .modal-content").find("input[name='fecha']").val();
		let select = $(this).closest("div .modal-content").find("select").val().length;
		if (empresaId == "" || cuit == "" || fecha == "" || select === 0) {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (cuit != 11 && isNaN(cuit)) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$(this).closest("div .modal-content").find("form").submit();

		}
	})

	/*-----------------------------------------------Persona------------------------------------------------*/

	$(".btn-delete-persona").click(function() {
		let id = $(this).closest('div .modal-footer').find("input[name='personaId']").val();
		let href = "http://localhost:8080/PersonaRest/checkIntegrantes/" + id;
		let result = null;
		let title = '<li class="list-group-item"><h6 class="m-0"><b>Empresas a las que pertenece:</b></h6></li>'
		$.ajax({
			url: href,
			type: 'get',
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: false,
			success: function(data) {
				result = data;
			}
		});
		if ("SinEmpresa" in result) {
			$(this).closest('div .modal-content').find("form").submit();
		} else {
			$("#checkEmpresa").empty();
			$("#checkEmpresa").append(title);
			for (var i = 0; i < Object.keys(result).length - 1; i++) {
				$("#checkEmpresa").append('<li class="list-group-item">' + result["empresa" + i] + '</li>');
			}
			$("#errorPersonaTitle").text(result.persona);
			$("#errorPersona").modal("show");

		}
	})

	$("#agregarPersona").click(function() {
		let check = true;
		if ($("#nombreModal").val() == "" || $("#dniModal").val() == "" || $("#apellidoModal").val() == ""){
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if ($("#dniModal").val().length != 8 && isNaN($("#dniModal").val())) {
			alert("DNI no valido")
			check = false;
		}
		if (check) {
			$('#personaForm').submit();
		}
	})

	$(".agregarPersonaEdit").click(function() {
		let check = true;
		let nombre = $(this).closest("div .modal-content").find("input[name='nombre']").val()
		let apellido = $(this).closest("div .modal-content").find("input[name='apellido']").val()
		let dni = $(this).closest("div .modal-content").find("input[name='dni']").val()
		if (nombre == "" || dni == "" || apellido == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (dni != 8 && isNaN(dni)) {
			alert("Dni no valido")
			check = false;
		}
		if (check) {
			$(this).closest("div .modal-content").find("form").submit();
		}
	})

	/*-----------------------------------------------Registro------------------------------------------------*/

	$("#agregarUsuario").click(function() {
		let check = true;
		if ($("#new-nombre").val() == "" || $("#new-username").val() == "" || $("#new-pass").val() == ""){
			$("#sinDatosModal").modal("show");
			check = false;
		}
		if (check) {
			$('#usuarioForm').submit();
		}
	})
	/*-----------------------------------------------Producto------------------------------------------------*/

	$("#agregarProducto").click(function() {
		let check = true;
		if ($("#productoModal").val() == "" || $("#fechaModal").val() == "" || $("#precioModal").val() == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$('#productoForm').submit();
		}
	})

	$(".agregarProductoEdit").click(function() {
		let check = true;
		let fecha = $(this).closest("div .modal-content").find("input[name='fecha']").val();
		let precioProducto = $(this).closest("div .modal-content").find("input[name='precio']").val();
		if (fecha == "" || precioProducto == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$(this).closest("div .modal-content").find("form").submit();
		}
	})

	/*-----------------------------------------------Servicio------------------------------------------------*/

	$("#agregarServicio").click(function() {
		let check = true;
		if ($("#nombreServicio").val() == "" || $("#fechaServicio").val() == "" || $("#precioServicio").val() == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$('#servicioForm').submit();
		}
	})

	$(".agregarServicioEdit").click(function() {
		let check = true;
		let fecha = $(this).closest("div .modal-content").find("input[name='fecha']").val();
		let precioServicio = $(this).closest("div .modal-content").find("input[name='precio']").val();
		if (fecha == "" || precioServicio == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$(this).closest("div .modal-content").find("form").submit();
		}
	})

	/*-----------------------------------------------Inicio------------------------------------------------*/
	$("#registerModal1").click(function() {
		($("#registerModal").modal("show"))
	})
})
