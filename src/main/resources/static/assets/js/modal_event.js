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
		else if ($("#cuit").val().length != 11 && !isNaN($("#cuit").val())) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$('#empresaForm').submit();
		}
	})

	$("#agregarEmpresaEdit").click(function() {
		let check = true;
		if ($("#razonSocialEdit").val() == "" || $("#cuitEdit").val() == "" || $("#fechaEdit").val() == "" || $("#integrantesEdit").val().length === 0) {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if ($("#cuitEdit").val().length != 11 && !isNaN($("#cuitEdit").val())) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$('#empresaFormEdit').submit();
		}
	})

	/*-----------------------------------------------Persona------------------------------------------------*/

	$("#agregarPersona").click(function() {
		let check = true;
		if ($("#nombreModal").val() == "" || $("#dniModal").val() == "" || $("#apellidoModal").val()) {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if ($("#dniModal").val().length != 8 && !isNaN($("#cuit").val())) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$('#personaForm').submit();
		}
	})

	$("#agregarPersonaEdit").click(function() {
		let check = true;
		if ($("#nombrePersonaEdit").val() == "" || $("#dniPersonaEdit").val() == "" || $("#apellidoPersonaEdit").val()) {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if ($("#dniPersonaEdit").val().length != 8 && !isNaN($("#cuit").val())) {
			alert("Cuit no valido")
			check = false;
		}
		if (check) {
			$('#personaFormEdit').submit();
		}
	})

	/*-----------------------------------------------Registro------------------------------------------------*/

	$("#agregarUsuario").click(function() {
		let check = true;
		if ($("#new-nombre").val() == "" || $("#new-username").val() == "" || $("#new-pass").val()) {
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

	$("#agregarProductoEdit").click(function() {
		let check = true;
		if ($("#fechaProductoEdit").val() == "" || $("#precioProductoEdit").val() == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$('#productoFormEdit').submit();
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

	$("#agregarServicioEdit").click(function() {
		let check = true;
		if ($("#fechaServicioEdit").val() == "" || $("#precioServicioEdit").val() == "") {
			$("#sinDatosModal").modal("show");
			check = false;
		}
		else if (check) {
			$('#servicioEditForm').submit();
		}
	})

	/*-----------------------------------------------Inicio------------------------------------------------*/
	$("#registerModal1").click(function() {
		($("#registerModal").modal("show"))
	})
})
