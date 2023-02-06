$(document).ready(function() {
	$("#agregarEdit").click(function() {
		let pedidoselect = $(".filter-option-inner-inner")[7].innerHTML;
		let cantidad = $("#cantidadEdit").val();
		let pedido = pedidoselect.substring(0, pedidoselect.indexOf(" -")) + " x " + cantidad;
		let tipo = $('#tipoPedidoEdit').html().slice(0, -1);
		let cargos = $(".filter-option-inner-inner")[6].innerHTML;
		let importe = 0;
		let descuento = 0;
		let contador = $('#tablaticketEdit >tbody >tr').length + 1;
		let persona = $("#personaEdit").val();
		let cantidadCargo = $("#cantidadCargoEdit").val();
		let btnDelete = '<button class="btn btn-sm delete-row btn-outline-danger" type="button"><small><i class="fas fa-times-circle delete-row-i"></i></small></buttons>';
		let checkCargo = (cargos.indexOf('Soporte') !== -1 || cargos.indexOf('Garantia') !== -1);

		if (checkCargo)
			cargos = cargos + '(' + cantidadCargo + ')'

		if (pedido != '' && cantidad != '' && persona != '' && (!checkCargo || cantidadCargo != '')) {
			let param = String($("#pedidoEdit").val() + "-" + persona + "-" + cargos);
			importe = (RefreshImportes(param).importe * cantidad).toFixed(2);
			descuento = RefreshImportes(param).descuento;
			$("#tablaticketEdit").last().append('<tr>' +
				'<td class="contadorEdit">' + contador + '</td>' +
				'<td class="tipoEdit">' + tipo + '</td>' +
				'<td class="pedidoColEdit"> ' + pedido + '</td>' +
				'<td class="importeEdit">' + importe + '</td>' +
				'<td class="cargosColEdit">' + cargos + '</td>' +
				'<td class="descuentoEdit">' + descuento + '</td>' +
				'<td>' + btnDelete + '</td>' +
				'<input type="hidden" name="tipoOrden" value="' + tipo + '" >' +
				'<input type="hidden" name="nombreOrden" value="' + pedido + '" >' +
				'<input type="hidden" name="importeOrden" value="' + importe + '" >' +
				'<input type="hidden" name="cargoOrden" value="' + cargos + '" >' +
				'</tr>');
			ChangeColorBtnEditConfirmar()
			checkDescuentosEdit();
		}
	});

	$('#razonSocialEdit').on('change', function() {
		var empresaid = $(this).val();
		RefreshPersona(empresaid, "#personaEdit");
		//RefreshTicket(0);
	});

	$('#personaEdit').on('change', function() {
		//	RefreshTicket(0);
		checkDescuentosHistoricoEdit();
		checkDescuentosEdit();
	})

	$('#pedidoEdit').change(function() {
		$("#cantidadEdit").val(1);
	});

	$('#tablaticketEdit').on('click', 'button[type="button"]', function(e) {
		$(this).closest('tr').remove();
		for (var i = 0; i < $('.contadorEdit').length; i++) {
			$('.contadorEdit')[i].innerHTML = i + 1;
		}
		ChangeColorBtnEditConfirmar()
		checkDescuentosHistoricoEdit();
	})

	$('#btnCancelarEdit').on('click', function() {
		RefreshTicketEdit(1);
	})
	
	//-------------------------------------------------------Inicializar------------------------------------------------------------------------------
	ChangeColorBtnEditConfirmar()
	RefreshEmpresa('#razonSocialEdit');
	RefreshPersona(0, "#personaEdit");
	RefreshPedidos(0, "#pedidoEdit");
	RefreshCargos(0, "#cargosEdit");
	$('#cantidadCargoEdit').hide();
	$('#cantidadCargoLabelEdit').hide();
});

$('.editarPedido').click(function() {
		let pedidoId = $(this).closest('td').find("input[name='pedidoVentaId']")[0].value;
		LoadEditModal(pedidoId);
		$("#editPedidoModal").modal("show");
	});


//CheckDescuentosEdit
function checkDescuentosEdit() {
	let checkServicio = 0;
	let textImporteTotal = "Importe Total : ";
	let textDescuentoTotal = "Descuento Total : ";
	let textImporteFinal = "Importe Final : ";
	let sumImporteTotal = 0;
	let sumDescuentoTotal = 0;
	let sumImporteFinal = 0;
	let descuento = 0;

	for (var i = 0; i < $('.tipoEdit').length; i++) {
		let tipo = $('.tipoEdit')[i].innerHTML;
		if (tipo === "Servicio" || (tipo === "Producto" && $('.descuentoEdit')[i].innerHTML !== "0")) {
			checkServicio = 1;
			break;
		}
	}
	for (var i = 0; i < $('.descuentoEdit').length; i++) {
		if ($('.tipoEdit')[i].innerHTML === "Producto") {
			descuento = Number(($('.importeEdit')[i].innerHTML * 0.1).toFixed(2));
			if (sumDescuentoTotal + descuento < 2500) {
				sumDescuentoTotal += descuento * checkServicio;
			} else {
				descuento = (2500 - sumDescuentoTotal).toFixed(2);
				sumDescuentoTotal = 2500 * checkServicio;;
			}
			if (descuento != 0) {
				$('.descuentoEdit')[i].innerHTML = descuento * checkServicio;
			} else {
				$('.descuentoEdit')[i].innerHTML = "DESCUENTO EXCEDIDO";
			}
		}
		sumImporteTotal += Number($('.importeEdit')[i].innerHTML);
	}
	sumImporteTotal = sumImporteTotal.toFixed(2);
	sumDescuentoTotal = sumDescuentoTotal.toFixed(2);
	sumImporteFinal = (sumImporteTotal - sumDescuentoTotal).toFixed(2);

	$('#importeTotalEdit').html(textImporteTotal + sumImporteTotal);
	$('#importeDescuentoEdit').html(textDescuentoTotal + sumDescuentoTotal);
	$('#importeFinalEdit').html(textImporteFinal + sumImporteFinal);
	$('#impTotalEdit').val(sumImporteTotal);
	$('#descTotalEdit').val(sumDescuentoTotal);
	ChangeColorBtnEditConfirmar()
}


function checkDescuentosHistoricoEdit() {
	let persona = $("#personaEdit").val();
	let param = "";
	let cantidad = "";
	let pedido = "";
	let cargos = "";
	let pedidoCol = "";
	let descuento = "";
	let importe = "";
	for (var i = 0; i < $('.pedidoColEdit').length; i++) {
		pedidoCol = $(".pedidoColEdit")[i].innerHTML;
		pedido = pedidoCol.substring(0, pedidoCol.indexOf(" x")).trim();
		cantidad = pedidoCol.substring(pedidoCol.indexOf("x ")).replace("x ", "").trim()
		cargos = $(".cargosColEdit")[i].innerHTML;
		param = String(pedido + "-" + persona + "-" + cargos);
		importe = (RefreshImportes(param).importe * cantidad).toFixed(2);
		descuento = RefreshImportes(param).descuento;
		$('.descuentoEdit')[i].innerHTML = descuento;
		$('.importeEdit')[i].innerHTML = importe;
	}
}

function RefreshTicketEdit(check) {
	if (check === 1) {
		RefreshEmpresa('#razonSocialEdit');
		RefreshPersona(0, "#personaEdit");
	}
	$("#tablaticketEdit tbody").empty();
	$("#fechaEdit").val("");
	RefreshPedidos(0, "#pedidoEdit");
	RefreshCargos(0, "#cargosEdit");
	$('#cantidadEdit').val("");
	$('#cantidadCargoEdit').val("");
	let btnDropdown = $('.dropdown-menu button');
	var cargoLabel = 'Años';
	btnDropdown.closest('.dropdown').find('.dropdown-toggle').html("Productos");
	btnDropdown.html("Servicios");
	$('#cantidadCargoLabelEdit').html(cargoLabel);
	$('#cantidadCargoEdit').hide();
	$('#cantidadCargoLabelEdit').hide();
	$('#importeTotalEdit').html("Importe Total : 0");
	$('#importeDescuentoEdit').html("Descuento Total : 0");
	$('#importeFinalEdit').html("Importe Final : 0");
	$('#impTotalEdit').val(0);
	$('#descTotalEdit').val(0);
}

function LoadEditModal(id) {
	RefreshTicketEdit(0);
	let persona = "";
	let empresa = "";
	let orden = "";
	let contador = "";
	let tipo = "";
	let btnDelete = '<button class="btn btn-sm delete-row btn-outline-danger" type="button"><small><i class="fas fa-times-circle delete-row-i"></i></small></buttons>';
	var href = "http://localhost:8080/PedidoVentaRest/getPedidoVenta/" + id
	$.get(href, function(pedido, status) {
		$("#modalEditTitle").text("Modificar " + pedido.nombre);
		$("#fechaEdit").val(pedido.fecha);
		$('#importeTotalEdit').html("Importe Total : " + pedido.importeTotal);
		$('#importeDescuentoEdit').html("Descuento Total : " + pedido.totalDescuentos);
		$('#importeFinalEdit').html("Importe Final : " + pedido.importeFinal);
		$('#impTotalEdit').val(pedido.importeTotal);
		$('#descTotalEdit').val(pedido.totalDescuentos);
		$('#pedidoVentaidEdit').val(pedido.id);
		if (pedido.cliente.split("//").length === 2) {
			//Parte izquiera empresa, parte derecha Persona
			empresa = pedido.cliente.split("//")[0].trim();
			persona = pedido.cliente.split("//")[1].trim();
		} else {
			//Sin Empresa
			persona = pedido.cliente;
		}
		$('select[id=razonSocialEdit]').val(getClienteId("#razonSocialEdit", empresa));
		$('select[id=personaEdit]').val(getClienteId("#personaEdit", persona));
		$('.selectpicker').selectpicker('refresh');
		for (var i = 0; i < pedido.ordenes.length; i++) {
			orden = pedido.ordenes[i];
			contador = $('#tablaticketEdit >tbody >tr').length + 1;
			if (orden.tipo === 1) {
				tipo = "Producto";
			} else {
				tipo = "Servicio";
			}
			$("#tablaticketEdit").last().append('<tr>' +
				'<td class="contadorEdit">' + contador + '</td>' +
				'<td class="tipoEdit">' + tipo + '</td>' +
				'<td class="pedidoColEdit"> ' + orden.nombre + '</td>' +
				'<td class="importeEdit">' + orden.importe + '</td>' +
				'<td class="cargosColEdit">' + orden.cargosDetalle + '</td>' +
				'<td class="descuentoEdit">' + 0 + '</td>' +
				'<td>' + btnDelete + '</td>' +
				'<input type="hidden" name="tipoOrden" value="' + tipo + '" >' +
				'<input type="hidden" name="nombreOrden" value="' + orden.nombre + '" >' +
				'<input type="hidden" name="importeOrden" value="' + orden.importe + '" >' +
				'<input type="hidden" name="cargoOrden" value="' + orden.cargosDetalle + '" >' +
				'</tr>');
			checkDescuentosEdit();
		}
	})
}

function getClienteId(selectId, nombre) {
	let selectOption = '';
	for (var i = 0; i < $(selectId + ' option').length; i++) {
		selectOption = $(selectId + " option")[i];
		if (selectOption.innerHTML.includes(nombre)) {
			return selectOption.value;
		}
	}
	if (selectId.includes("persona"))
		return null;
	return 0;
}

function ChangeColorBtnEditConfirmar() {
	if ($("#tablaticketEdit > tbody >tr").length === 0) {
		$("#confirmarEdit").removeClass("btn-success");
		$("#confirmarEdit").addClass("btn-danger");
	} else {
		$("#confirmarEdit").removeClass("btn-danger");
		$("#confirmarEdit").addClass("btn-success");
	}
}
