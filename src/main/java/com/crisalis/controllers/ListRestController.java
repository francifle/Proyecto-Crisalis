package com.crisalis.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.constants.BasicsConstants;
import com.crisalis.models.Empresa;
import com.crisalis.models.Impuesto;
import com.crisalis.models.Pedido;
import com.crisalis.models.Persona;
import com.crisalis.models.Producto;
import com.crisalis.models.Servicio;
import com.crisalis.services.EmpresaService;
import com.crisalis.services.ImpuestoService;
import com.crisalis.services.PedidoService;
import com.crisalis.services.PersonaService;
import com.crisalis.services.ProductoService;
import com.crisalis.services.ServicioService;

@RestController
@RequestMapping("/ListRest")
public class ListRestController {

	private ImpuestoService impuestoService;
	private PersonaService personaService;
	private EmpresaService empresaService;
	private ProductoService productoService;
	private ServicioService servicioService;
	private PedidoService pedidoService;

	public ListRestController(ImpuestoService impuestoService, PersonaService personaService,
			EmpresaService empresaService, ProductoService productoService, ServicioService servicioService,
			PedidoService pedidoService) {
		this.impuestoService = impuestoService;
		this.personaService = personaService;
		this.empresaService = empresaService;
		this.productoService = productoService;
		this.servicioService = servicioService;
		this.pedidoService = pedidoService;

	}

	@SuppressWarnings("null")
	@GetMapping(value = "impuestos/{check}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<Impuesto> listImpuestos(@PathVariable Long check) {
		ArrayList<Impuesto> listImpuestos = new ArrayList<Impuesto>();
		for (Impuesto impuesto : impuestoService.getAllImpuestos()) {
			if (!impuesto.getNombre().equals("Soporte") && (check == 0 || check == null))
				listImpuestos.add(impuesto);
			if (!impuesto.getNombre().equals("Garantia") && check == 1)
				listImpuestos.add(impuesto);
		}
		return listImpuestos;
	}

	@GetMapping(value = "personas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<Persona> listPersonas(@RequestParam(value = "term", required = false) String term) {
		if (term == null) {
			return personaService.getAllPersonas();
		} else {
			return personaService.getAllPersonasByNombre(term);
		}
	}

	@GetMapping(value = "empresas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Empresa> listEmpresas() {
		return empresaService.getAllEmpresas();
	}

	@GetMapping(value = "integrantes/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Persona> getIntegrantes(@PathVariable Long id) {
		if (id == 0 || id == null)
			return personaService.getAllPersonas();
		return new ArrayList<>(empresaService.findEmpresaByID(id).getIntegrantes());
	}

	@GetMapping(value = "pedidos/{check}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Pedido> getPedidos(@PathVariable Long check) {
		ArrayList<Pedido> listPedidos = new ArrayList<Pedido>();
		if (check == 0 || check == null)
			productoService.getAllProductos().forEach((producto) -> listPedidos.add(producto.getPedido()));
		else
			servicioService.getAllServicios().forEach((servicio) -> listPedidos.add(servicio.getPedido()));
		return listPedidos;
	}

	@GetMapping(value = "productos/{check}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Producto> getProductos(@PathVariable Long check) {
		return productoService.getAllProductos();
	}

	@GetMapping(value = "importeTabla/{idParam}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> getImporteTabla(@PathVariable String idParam) {
		Integer cantidadCargo = 0;
		String[] ids = idParam.split("-");
		String[] cargos = ids[2].split(",");
		Pedido pedido = pedidoService.findPedidoByID(Long.valueOf(ids[0]));
		if (cargos.length == 3) {
			int inicioNum = cargos[2].indexOf("(") + 1;
			int finNum = cargos[2].indexOf(")");
			System.out.println(cargos[2]);
			cantidadCargo = Integer.valueOf(cargos[2].substring(inicioNum, finNum));
		} // Persona persona = personaService.findPersonaByID(Long.valueOf(ids[0]));
		HashMap<String, String> map = new HashMap<>();
		if (pedido.getTipo().equals(BasicsConstants.TIPO_PRODUCTO)) {
			Producto pedidoProducto = productoService.findProductoByPedidoID(pedido.getId());
			pedidoProducto = setNewImpuestos(pedidoProducto, cargos);
			map.put("importe", pedidoProducto.getPrecioTotalProducto(cantidadCargo).toString());
		}else {
			Servicio pedidoServicio = servicioService.findServicioByPedidoID(pedido.getId());
			pedidoServicio = setNewImpuestos(pedidoServicio, cargos);
			map.put("importe", pedidoServicio.getPrecioTotalServicio(cantidadCargo).toString());
		}
		/*
		 * if (pedido.getTipo().equals(BasicsConstants.TIPO_SERVICIO)) {
		 * map.put("descuento", "0"); }else { map.put("descuento",
		 * checkDescuentoByPersona(persona)); } map.put("value1", ids[0]);
		 * map.put("value2", ids[1]); map.put("value3", ids[2]);
		 */
		map.put("descuento", "0");
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

	private String checkDescuentoByPersona(Persona persona) {
		// TODO Auto-generated method stub
		return "0";
	}
}
