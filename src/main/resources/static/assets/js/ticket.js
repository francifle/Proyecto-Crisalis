//document.addEventListener('DOMContentLoaded', function y)
$(document).ready(function() {
	//document.getElementById('agregar').addEventListener('click', function x)
	$("#agregar").click(function() {
		let pedidoselect = $(".filter-option-inner-inner")[3].innerHTML;
		let pedido = pedidoselect.substring(0, pedidoselect.indexOf(" -"));
		let tipo = $('#tipoPedido').html().slice(0, -1);
		let cantidad = $("#cantidad").val();
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

		let param = String($("#pedido").val() + "-" + persona + "-" + cargos);
		importe = RefreshImportes(param).importe;
		descuento = RefreshImportes(param).descuento;
		if (pedido != '' && cantidad != '' && persona != '' && (!checkCargo || cantidadCargo != '')) {
			$("#tablaticket").last().append('<tr>' +
				'<td class="contador">' + contador + '</td>' +
				'<td>' + tipo + '</td>' +
				'<td>' + pedido + '</td>' +
				'<td>' + cantidad + '</td>' +
				'<td>' + importe + '</td>' +
				'<td>' + cargos + '</td>' +
				'<td>' + descuento + '</td>' +
				'<td>' + btnDelete + '</td>' +
				'</tr>');
			/*var $demo1 = $('tabla');
			$demo1.floatThead({
				scrollContainer: function($table) {
					return $table.closest('.wrapper');
				}
			});*/
		}
	});

	$('#razonsocial').empty().append('<option value="0">-Sin Empresa-</option>');
	var href = "http://localhost:8080/ListRest/empresas"
	$.get(href, function(empresas, status) {
		for (var i = 0; i <= empresas.length - 1; i++) {
			$('#razonsocial').append('<option value="' + empresas[i].id + '">' + empresas[i].razonSocial + '</option>');
			$('#razonsocial').selectpicker('refresh');
		}
	})

	$('#razonsocial').on('change', function() {
		var empresaid = $(this).val();
		RefreshPersona(empresaid);
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
		RefreshCargos(check);
		RefreshPedidos(check); //0-productos 1-servicios
		$('#cantidadcargo').hide();
		$('#cant-cargo-label').hide();
	});

	$('#cargos').change(function() {
		$('#cantidadcargo').hide();
		$('#cant-cargo-label').hide();
		for (let i = 0; i < $(this)[0].options.length; i++) {
			if (($(this)[0].options[i].text == 'Garantia' || $(this)[0].options[i].text == 'Soporte') && $(this)[0].options[i].selected) {
				$('#cant-cargo-label').show();
				$('#cantidadcargo').show();
			}
		}
	});

	//-------------------------------------------------------Inicializar------------------------------------------------------------------------------
	RefreshPersona(0);
	RefreshPedidos(0);
	RefreshCargos(0);
	$('#cantidadcargo').hide();
	$('#cant-cargo-label').hide();
	$('#tablaticket').on('click', 'button[type="button"]', function(e) {
		$(this).closest('tr').remove();
		for (var i = 0; i < $('.contador').length; i++) {
			$('.contador')[i].innerHTML = i + 1;
		}
	})
});
function removeRow(button) {
	$(button).closest('tr').remove()
}

function RefreshCargos(check) {
	$('#cargos').empty();
	var href = "http://localhost:8080/ListRest/impuestos/" + check
	$.get(href, function(cargos, status) {
		for (var i = 0; i <= cargos.length - 1; i++) {
			if (cargos[i].nombre == 'IVA' || cargos[i].nombre == 'IIBB') {
				$('#cargos').append('<option selected disabled value="' + cargos[i].id + '" >' + cargos[i].nombre + '</option>');
			}
			else {
				$('#cargos').append('<option value="' + cargos[i].id + '" >' + cargos[i].nombre + '</option>');
			}
			$('#cargos').selectpicker('refresh');
		}
	})
}

function RefreshPersona(id) {
	$('#persona').empty().append('<option value="" selected>-Seleccionar Persona-</option>');
	var href = "http://localhost:8080/ListRest/integrantes/" + id
	$.get(href, function(personas, status) {
		for (var i = 0; i <= personas.length - 1; i++) {
			$('#persona').append('<option value="' + personas[i].id + '">' + personas[i].nombreFisico + '</option>');
			$('#persona').selectpicker('refresh');
		}
	})
}

function RefreshImportes(param) {
	var href = "http://localhost:8080/ListRest/importeTabla/" + param
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

function RefreshPedidos(check) {
	$('#pedido').empty().append('<option value="" selected>-Seleccionar Producto/Servicio-</option>');
	var href = "http://localhost:8080/ListRest/pedidos/" + check
	$.get(href, function(pedidos, status) {
		for (var i = 0; i <= pedidos.length - 1; i++) {
			$('#pedido').append('<option value="' + pedidos[i].id + '">' + pedidos[i].nombre + ' - ' + pedidos[i].precio + '</option > ');
			$('#pedido').selectpicker('refresh');
		}
	})
}