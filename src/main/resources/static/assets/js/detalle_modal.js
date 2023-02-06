//$(document).ready(function() {
	$(".detalleBtn").click(function() {
		let cliente = $(this).closest('td').find("input[name='clienteInf']").val();
		let detalles = $(this).closest('td').find("input[name='detalleInf']").val().split("; ");
		let title = '<li class="list-group-item"><h6 class="m-0"><b>Pedidos Adquiridos:</b></h6></li>'
		$("#listPedido").empty();
		$("#listPedido").append(title);
		for (var i = 0; i < detalles.length; i++) {
			$("#listPedido").append('<li class="list-group-item">' + detalles[i] + '</li>');
		}
		$("#detalleTitleInf").text(cliente);
		//$("#DetalleInf").modal("show");
	})	
//})