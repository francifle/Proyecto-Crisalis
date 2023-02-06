//document.addEventListener('DOMContentLoaded', function y)
$(document).ready(function() {
	//document.getElementById('agregar').addEventListener('click', function x)
	$("#agregar").click(function() {
		let pedidoselect = $(".filter-option-inner-inner")[3].innerHTML;
		let cantidad = $("#cantidad").val();
		let pedido = pedidoselect.substring(0, pedidoselect.indexOf(" -")) + " x " + cantidad;
		let tipo = $('#tipoPedido').html().slice(0, -1);
		let cargos = $(".filter-option-inner-inner")[2].innerHTML;
		let importe = 0;
		let descuento = 0;
		let contador = $('#tablaticket >tbody >tr').length + 1;
		let persona = $("#persona").val();
		let cantidadCargo = $("#cantidadcargo").val();
		let btnDelete = '<button class="btn btn-sm delete-row btn-outline-danger" type="button"><small><i class="fas fa-times-circle delete-row-i"></i></small></buttons>';
		let checkCargo = (cargos.indexOf('Soporte') !== -1 || cargos.indexOf('Garantia') !== -1);

		if (checkCargo)
			cargos = cargos + '(' + cantidadCargo + ')'

		if (pedido != '' && cantidad != '' && persona != '' && (!checkCargo || cantidadCargo != '')) {
			let param = String($("#pedido").val() + "-" + persona + "-" + cargos);
			importe = (RefreshImportes(param).importe * cantidad).toFixed(2);
			descuento = RefreshImportes(param).descuento;
			$("#tablaticket").last().append('<tr>' +
				'<td class="contador">' + contador + '</td>' +
				'<td class="tipo">' + tipo + '</td>' +
				'<td class="pedidoCol"> ' + pedido + '</td>' +
				'<td class="importe">' + importe + '</td>' +
				'<td class="cargosCol">' + cargos + '</td>' +
				'<td class="descuento">' + descuento + '</td>' +
				'<td>' + btnDelete + '</td>' +
				'<input type="hidden" name="tipoOrden" value="' + tipo + '" >' +
				'<input type="hidden" name="nombreOrden" value="' + pedido + '" >' +
				'<input type="hidden" name="importeOrden" value="' + importe + '" >' +
				'<input type="hidden" name="cargoOrden" value="' + cargos + '" >' +
				'</tr>');
			ChangeColorBtnConfirmar()
			checkDescuentos();
			/*var $demo1 = $('tabla');
			$demo1.floatThead({
				scrollContainer: function($table) {
					return $table.closest('.wrapper');
				}
			});*/
		}
	});

	$('#razonsocial').on('change', function() {
		var empresaid = $(this).val();
		RefreshPersona(empresaid, "#persona");
		//RefreshTicket(0);
	})

	$('#persona').on('change', function() {
		//	RefreshTicket(0);
		checkDescuentosHistorico();
		checkDescuentos();
	})

	$('#btnCancelar').on('click', function() {
		RefreshTicket(1);
	})


	$('.dropdown-menu').on('click', 'button', function() {
		var check = 0;
		var cargoLabel = 'Años';
		//Texto del boton del dropdown 
		var text = $(this).html();
		// Texto de la opcion del dropdown
		var btnText = $(this).closest('.dropdown').find('.dropdown-toggle').text().trim();
		var htmlText = text + '';
		if (text === 'Servicios') {
			check = 1;
			cargoLabel = 'Meses';
		}
		$(this).closest('.dropdown').find('.dropdown-toggle').html(htmlText);
		$(this).html(btnText);
		$('#cant-cargo-label').html(cargoLabel);
		RefreshCargos(check, "#cargos");
		RefreshPedidos(check, "#pedido"); //0-productos 1-servicios
		$('#cantidadcargo').hide();
		$('#cant-cargo-label').hide();

		//Modal Update
		$('#cantidadCargoLabelEdit').html(cargoLabel);
		RefreshCargos(check, "#cargosEdit");
		RefreshPedidos(check, "#pedidoEdit"); //0-productos 1-servicios
		$('#cantidadCargoEdit').hide();
		$('#cantidadCargoLabelEdit').hide();
	});

	$('#cargos, #cargosEdit').change(function() {
		$('#cantidadcargo').hide();
		$('#cant-cargo-label').hide();
		$('#cantidadCargoEdit').hide();
		$('#cantidadCargoLabelEdit').hide();
		for (let i = 0; i < $(this)[0].options.length; i++) {
			if (($(this)[0].options[i].text == 'Garantia' || $(this)[0].options[i].text == 'Soporte') && $(this)[0].options[i].selected) {
				$('#cant-cargo-label').show();
				$('#cantidadcargo').show();
				$('#cantidadCargoLabelEdit').show();
				$('#cantidadCargoEdit').show();
			}
		}
	});

	$('#pedido').change(function() {
		$("#cantidad").val(1);
	});

	//-------------------------------------------------------Inicializar------------------------------------------------------------------------------
	ChangeColorBtnConfirmar();
	RefreshEmpresa('#razonsocial');
	RefreshPersona(0, "#persona");
	RefreshPedidos(0, "#pedido");
	RefreshCargos(0, "#cargos");
	$('#cantidadcargo').hide();
	$('#cant-cargo-label').hide();
	$('#tablaticket').on('click', 'button[type="button"]', function(e) {
		$(this).closest('tr').remove();
		for (var i = 0; i < $('.contador').length; i++) {
			$('.contador')[i].innerHTML = i + 1;
		}
		checkDescuentosHistorico();
		ChangeColorBtnConfirmar()
	})
});

$('.changeEstado').each(function(f) {
	let btn = $(this);
	ChangeEstadoStyle(btn, btn.text() == "Anular");
});
$('.changeEstado').click(function() {
	let pedidoVentaId = $(this).closest('td').find("input[name='pedidoVentaId']")[0].value;
	let btn = $(this);
	$.ajax({
		url: "/PedidoVentaRest/changeEstado/" + pedidoVentaId,
		type: "GET",
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		async: true,
		success: function(data) {
			ChangeEstadoStyle(btn, data.estado);
		}
	});
});

$('.hiddentext').each(function(f) {
	var newstr = $(this).text().substring(0, 37);
	if ($(this).text().length > 37)
		newstr += "..."
	$(this).text(newstr);

});

function RefreshCargos(check, idSelect) {
	$(idSelect).empty();
	var href = "http://localhost:8080/ListRest/impuestos/" + check
	$.get(href, function(cargos, status) {
		for (var i = 0; i <= cargos.length - 1; i++) {
			if (cargos[i].nombre == 'IVA' || cargos[i].nombre == 'IIBB') {
				$(idSelect).append('<option selected disabled value="' + cargos[i].id + '" >' + cargos[i].nombre + '</option>');
			}
			else {
				$(idSelect).append('<option value="' + cargos[i].id + '" >' + cargos[i].nombre + '</option>');
			}
			$(idSelect).selectpicker('refresh');
		}
	})
}

function RefreshPersona(id, selectId) {
	$(selectId).empty().append('<option value="" selected>-Seleccionar Persona-</option>');
	$(selectId).selectpicker('refresh');
	var href = "http://localhost:8080/ListRest/integrantes/" + id
	$.get(href, function(personas, status) {
		for (var i = 0; i <= personas.length - 1; i++) {
			if (personas[i].estado) {
				$(selectId).append('<option value="' + personas[i].id + '">' + personas[i].nombreFisico + '</option>');
				$(selectId).selectpicker('refresh');
			}
		}
	})
}

function checkDescuentosHistorico() {
	let persona = $("#persona").val();
	let param = "";
	let cantidad = "";
	let pedido = "";
	let cargos = "";
	let pedidoCol = "";
	let descuento = "";
	let importe = "";
	for (var i = 0; i < $('.pedidoCol').length; i++) {
		pedidoCol = $(".pedidoCol")[i].innerHTML;
		pedido = pedidoCol.substring(0, pedidoCol.indexOf(" x")).trim();
		cantidad = pedidoCol.substring(pedidoCol.indexOf("x ")).replace("x ", "").trim()
		cargos = $(".cargosCol")[i].innerHTML;
		param = String(pedido + "-" + persona + "-" + cargos);
		importe = (RefreshImportes(param).importe * cantidad).toFixed(2);
		descuento = RefreshImportes(param).descuento;
		$('.descuento')[i].innerHTML = descuento;
		$('.importe')[i].innerHTML = importe;
	}
}

function checkDescuentos() {
	let checkServicio = 0;
	let textImporteTotal = "Importe Total : ";
	let textDescuentoTotal = "Descuento Total : ";
	let textImporteFinal = "Importe Final : ";
	let sumImporteTotal = 0;
	let sumDescuentoTotal = 0;
	let sumImporteFinal = 0;
	let descuento = 0;

	for (var i = 0; i < $('.tipo').length; i++) {
		let tipo = $('.tipo')[i].innerHTML;
		if (tipo === "Servicio" || (tipo === "Producto" && $('.descuento')[i].innerHTML !== "0")) {
			checkServicio = 1;
			break;
		}
	}
	for (var i = 0; i < $('.descuento').length; i++) {
		if ($('.tipo')[i].innerHTML === "Producto") {
			descuento = Number(($('.importe')[i].innerHTML * 0.1).toFixed(2));
			if (sumDescuentoTotal + descuento < 2500) {
				sumDescuentoTotal += descuento * checkServicio;
			} else {
				descuento = (2500 - sumDescuentoTotal).toFixed(2);
				sumDescuentoTotal = 2500 * checkServicio;;
			}
			if (descuento != 0) {
				$('.descuento')[i].innerHTML = descuento * checkServicio;
			} else {
				$('.descuento')[i].innerHTML = "DESCUENTO EXCEDIDO";
			}
		}
		sumImporteTotal += Number($('.importe')[i].innerHTML);
	}
	sumImporteTotal = sumImporteTotal.toFixed(2);
	sumDescuentoTotal = sumDescuentoTotal.toFixed(2);
	sumImporteFinal = (sumImporteTotal - sumDescuentoTotal).toFixed(2);

	$('#importe_total_label').html(textImporteTotal + sumImporteTotal);
	$('#importe_descuento_label').html(textDescuentoTotal + sumDescuentoTotal);
	$('#importe_final_label').html(textImporteFinal + sumImporteFinal);
	$('#impTotal').val(sumImporteTotal);
	$('#descTotal').val(sumDescuentoTotal);


}

function RefreshEmpresa(idEmpresasSelect) {
	$(idEmpresasSelect).empty().append('<option value="0">-Sin Empresa-</option>');
	var href = "http://localhost:8080/ListRest/empresas"
	$.get(href, function(empresas, status) {
		for (var i = 0; i <= empresas.length - 1; i++) {
			$(idEmpresasSelect).append('<option value="' + empresas[i].id + '">' + empresas[i].razonSocial + '</option>');
			$(idEmpresasSelect).selectpicker('refresh');
		}
	})
}

function RefreshImportes(param) {
	var href = "http://localhost:8080/PedidoVentaRest/importeTabla/" + param
	let result = null;
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
	//$.get(href, function(values, status) {
	return result;
	//})
}

function RefreshPedidos(check, idSelector) {
	$(idSelector).empty().append('<option value="" selected>-Seleccionar Producto/Servicio-</option>');
	var href = "http://localhost:8080/ListRest/pedidos/" + check
	$.get(href, function(pedidos, status) {
		for (var i = 0; i <= pedidos.length - 1; i++) {
			$(idSelector).append('<option value="' + pedidos[i].id + '">' + pedidos[i].nombre + ' - ' + pedidos[i].precio + '</option > ');
			$(idSelector).selectpicker('refresh');
		}
	})
}

function RefreshTicket(check) {
	if (check === 1) {
		RefreshEmpresa('#razonsocial');
		RefreshPersona(0, "#persona");
	}
	$("#tablaticket tbody").empty();
	$("#fecha").val("");
	RefreshPedidos(0, "#pedido");
	RefreshCargos(0, "#cargos");
	$('#cantidad').val("");
	$('#cantidadcargo').val("");
	let btnDropdown = $('.dropdown-menu button');
	var cargoLabel = 'Años';
	btnDropdown.closest('.dropdown').find('.dropdown-toggle').html("Productos");
	btnDropdown.html("Servicios");
	$('#cant-cargo-label').html(cargoLabel);
	$('#cantidadcargo').hide();
	$('#cant-cargo-label').hide();
	$('#importe_total_label').html("Importe Total : 0");
	$('#importe_descuento_label').html("Descuento Total : 0");
	$('#importe_final_label').html("Importe Final : 0");
	$('#impTotal').val(0);
	$('#descTotal').val(0);
}

function ChangeEstadoStyle(btn, check) {
	if (check) {
		btn.closest("tr").find("td:eq(1)").css("text-decoration", "none");
		btn.closest("tr").find("td:eq(2)").css("text-decoration", "none");
		btn.closest("tr").find("td:eq(3)").css("text-decoration", "none");
		btn.closest("tr").find("td:eq(4)").css("text-decoration", "none");
		btn.closest("div .dropdown-menu").find("a.dropdown-item.editarPedido").show();
		btn.closest("tr").addClass("text-success");
		btn.closest("tr").removeClass("text-danger");
		btn.text("Anular");
		btn.closest("div .btn-group").find("button").text("Activado");
	} else {
		btn.closest("tr").find("td:eq(1)").css("text-decoration", "line-through");
		btn.closest("tr").find("td:eq(2)").css("text-decoration", "line-through");
		btn.closest("tr").find("td:eq(3)").css("text-decoration", "line-through");
		btn.closest("tr").find("td:eq(4)").css("text-decoration", "line-through");
		btn.closest("div .dropdown-menu").find("a.dropdown-item.editarPedido").hide();
		btn.closest("tr").addClass("text-danger");
		btn.closest("tr").removeClass("text-success");
		btn.text("Activar");
		btn.closest("div .btn-group").find("button").text("Anulado");
	}
}

function ChangeColorBtnConfirmar() {
	if ($("#tablaticket > tbody >tr").length === 0) {
		$("#confirmar").removeClass("btn-success");
		$("#confirmar").addClass("btn-danger");
	} else {
		$("#confirmar").removeClass("btn-danger");
		$("#confirmar").addClass("btn-success");
	}
}