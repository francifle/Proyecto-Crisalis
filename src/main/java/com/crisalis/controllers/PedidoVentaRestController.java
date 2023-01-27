package com.crisalis.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.constants.UtilsConstants;
import com.crisalis.models.Impuesto;
import com.crisalis.models.Pedido;
import com.crisalis.models.PedidoVenta;
import com.crisalis.models.Persona;
import com.crisalis.models.Producto;
import com.crisalis.models.Servicio;
import com.crisalis.services.ImpuestoService;
import com.crisalis.services.PedidoService;
import com.crisalis.services.PedidoVentaService;
import com.crisalis.services.PersonaService;
import com.crisalis.services.ProductoService;
import com.crisalis.services.ServicioService;

@RestController
@RequestMapping("/PedidoVentaRest")
public class PedidoVentaRestController {

	@Autowired
	private PedidoVentaService pedidoVentaService;
	@Autowired
	private ImpuestoService impuestoService;
	@Autowired
	private PersonaService personaService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ServicioService servicioService;
	@Autowired
	private PedidoService pedidoService;

	public PedidoVentaRestController(PedidoVentaService pedidoVentaService, ImpuestoService impuestoService,
			PersonaService personaService, ProductoService productoService, ServicioService servicioService,
			PedidoService pedidoService) {

		this.pedidoVentaService = pedidoVentaService;
		this.impuestoService = impuestoService;
		this.personaService = personaService;
		this.productoService = productoService;
		this.servicioService = servicioService;
		this.pedidoService = pedidoService;
	}
	
	@GetMapping(value = "getPedidoVenta/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public PedidoVenta getPedidoVenta(@PathVariable Long id) {
		return pedidoVentaService.findPedidoVentaByID(id);
	}
	
	@GetMapping(value = "changeEstado/{id}")
	public PedidoVenta updateEstado(@PathVariable Long id) {
		PedidoVenta pedidoVenta = pedidoVentaService.findPedidoVentaByID(id);
		pedidoVenta.setEstado(!pedidoVenta.getEstado());
		return pedidoVentaService.saveOrUpdatePedidoVenta(pedidoVenta);
	}

	@GetMapping(value = "importeTabla/{idParam}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> getImporteTabla(@PathVariable String idParam) {
		Integer cantidadCargo = 0;
		String[] ids = idParam.split("-");
		String[] cargos = ids[2].split(",");
		Pedido pedido = null;	
		if (StringUtils.isNumeric(ids[0])) {
			pedido = pedidoService.findPedidoByID(Long.valueOf(ids[0]));
		}else {
			pedido = pedidoService.findPedidoByNombre(ids[0]);
		}
		if (cargos.length == 3) {
			int inicioNum = cargos[2].indexOf("(") + 1;
			int finNum = cargos[2].indexOf(")");
			cantidadCargo = Integer.valueOf(cargos[2].substring(inicioNum, finNum));
		}
		Persona persona = personaService.findPersonaByID(Long.valueOf(ids[1]));
		HashMap<String, String> map = new HashMap<>();
		if (pedido.getTipo().equals(UtilsConstants.TIPO_PRODUCTO)) {
			Producto pedidoProducto = productoService.findProductoByPedidoID(pedido.getId());
			pedidoProducto = setNewImpuestos(pedidoProducto, cargos);
			Double importe = pedidoProducto.getPrecioTotalProducto(cantidadCargo);
			map.put("importe", importe.toString());
			map.put("descuento", checkDescuentoByPersona(persona.getNombreCompleto(), importe));
		} else {
			Servicio pedidoServicio = servicioService.findServicioByPedidoID(pedido.getId());
			pedidoServicio = setNewImpuestos(pedidoServicio, cargos);
			map.put("importe", pedidoServicio.getPrecioTotalServicio(cantidadCargo).toString());
			map.put("descuento", "0");
		}
		return map;
	}

	private Servicio setNewImpuestos(Servicio pedidoServicio, String[] impuestos) {
		pedidoServicio.getPedido().getImpuestos().clear();
		Impuesto newImpuesto = null;
		for (int i = 0; i < impuestos.length; i++) {
			List<Impuesto> listImpuestos = impuestoService.getAllImpuestosByNombre(impuestos[i].trim());
			if (listImpuestos != null && !listImpuestos.isEmpty()) {
				newImpuesto = impuestoService.getAllImpuestosByNombre(impuestos[i].trim()).get(0);
				if (newImpuesto != null)
					pedidoServicio.getPedido().getImpuestos().add(newImpuesto);
			}
		}
		return pedidoServicio;
	}

	private Producto setNewImpuestos(Producto pedidoProducto, String[] impuestos) {
		pedidoProducto.getPedido().getImpuestos().clear();
		Impuesto newImpuesto = null;
		for (int i = 0; i < impuestos.length; i++) {
			List<Impuesto> listImpuestos = impuestoService.getAllImpuestosByNombre(impuestos[i].trim());
			if (listImpuestos != null && !listImpuestos.isEmpty()) {
				newImpuesto = impuestoService.getAllImpuestosByNombre(impuestos[i].trim()).get(0);
				if (newImpuesto != null)
					pedidoProducto.getPedido().getImpuestos().add(newImpuesto);
			}
		}
		return pedidoProducto;
	}

	private String checkDescuentoByPersona(String persona, Double importe) {
		ArrayList<PedidoVenta> listaPedidos = pedidoVentaService.getAllPedidoVentas();
		for (PedidoVenta p : listaPedidos) {
			if(p.getCliente().contains(persona) && p.getEstado() && !p.getOrdenesServicio().isEmpty()) {
				//System.out.println(p.getEstado());
				Double value = importe * UtilsConstants.DESC_BENEFICIO;
				return String.valueOf(Math.round(value * 100.0) / 100.0);
			}
		}
	
		
		return "0";
	}
	
}
