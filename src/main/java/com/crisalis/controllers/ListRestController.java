package com.crisalis.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.models.Empresa;
import com.crisalis.models.Impuesto;
import com.crisalis.models.Pedido;
import com.crisalis.models.Persona;
import com.crisalis.models.Producto;
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

	
}
