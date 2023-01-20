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
				'<td> ' + pedido + '</td>' +
				'<td class="importe">' + importe + '</td>' +
				'<td>' + cargos + '</td>' +
				'<td class="descuento">' + descuento + '</td>' +
				'<td>' + btnDelete + '</td>' +
				'<input type="hidden" name="tipoOrden" value="' + tipo + '" >' +
				'<input type="hidden" name="nombreOrden" value="' + pedido + '" >' +
				'<input type="hidden" name="importeOrden" value="' + importe + '" >' +
				'<input type="hidden" name="cargoOrden" value="' + cargos + '" >' +
				'</tr>');
			checkDescuentos();
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

	$('#pedido').change(function() {
		$("#cantidad").val(1);
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
		checkDescuentos();
	})

	$('.hiddentext').each(function(f) {

		var newstr = $(this).text().substring(0, 37);
		if ($(this).text().length > 37)
			newstr += "..."
		$(this).text(newstr);

	});
	
	$('.changeEstado').each(function(f) {
		let btn = $(this);
		if (btn.text() == "Anular") {
			btn.closest("tr").css("text-decoration", "none")
		} else {
				btn.closest("tr").css("text-decoration", "line-through")
		}

	});
	$('.changeEstado').click(function() {
		let pedidoVentaId = $(this).closest('td').find("input[name='pedidoVentaId']")[0].value;

		/*$.ajax({
			type: "GET",
			url: "/pedidoventa/changeEstado/" + pedidoVentaId,
			processData: false,
			contentType: 'application/json',
			//data: JSON.stringify(data),
			success: function(r) {
				console.log("actualizado")
			}
		});*/
		let btn = $(this);
		$.ajax({
			url: "/PedidoVentaRest/changeEstado/" + pedidoVentaId,
			type: "GET",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: true,
			success: function(data) {
				/*$(this).closest("button.btn-outline-danger").hide();
				$(this).closest("button.btn-outline-success").hide();*/
				if (data.estado) {
					btn.addClass("btn-outline-danger");
					btn.removeClass("btn-outline-success");
					btn.text("Anular")
					btn.closest("tr").css("text-decoration", "none")
					//$(this).closest("button.btn-outline-danger").show();
				} else {
					btn.addClass("btn-outline-success");
					btn.removeClass("btn-outline-danger");
					btn.text("Activar")
					btn.closest("tr").css("text-decoration", "line-through")
					//$(this).closest("button.btn-outline-success").show();
				}
			}
		});
	})
});

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