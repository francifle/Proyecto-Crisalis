package com.crisalis.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crisalis.constants.UtilsConstants;
import com.crisalis.models.Empresa;
import com.crisalis.models.PedidoVenta;
import com.crisalis.models.Persona;
import com.crisalis.models.Producto;
import com.crisalis.models.Servicio;
import com.crisalis.services.EmpresaService;
import com.crisalis.services.PedidoVentaService;
import com.crisalis.services.PersonaService;
import com.crisalis.services.ProductoService;
import com.crisalis.services.ServicioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/List")
public class ListController {

	@Autowired
	private final ProductoService productoService;
	@Autowired
	private final ServicioService servicioService;
	@Autowired
	private final PersonaService personaService;
	@Autowired
	private final EmpresaService empresaService;
	@Autowired
	private final PedidoVentaService pedidoVentaService;
	@Autowired
	private final ObjectMapper mapper;

	public ListController(ProductoService productoService, ServicioService servicioService,
			PersonaService personaService, EmpresaService empresaService, ObjectMapper mapper,
			PedidoVentaService pedidoVentaService) {
		this.productoService = productoService;
		this.servicioService = servicioService;
		this.personaService = personaService;
		this.empresaService = empresaService;
		this.pedidoVentaService = pedidoVentaService;
		this.mapper = mapper;

	}

	@GetMapping(value = "Productos")
	public ModelAndView listProducto() {
		ModelAndView mav = new ModelAndView("ListProductos");
		List<Producto> list = productoService.getAllProductos();
		mav.addObject("productos", list);
		return mav;
	}

	@GetMapping(value = "Servicios")
	public ModelAndView listServicio() {
		ModelAndView mav = new ModelAndView("ListServicios");
		List<Servicio> list = servicioService.getAllServicios();
		mav.addObject("servicios", list);
		return mav;
	}

	@GetMapping(value = "Empresas")
	public ModelAndView listEmpresa() {
		ModelAndView mav = new ModelAndView("ListEmpresas");
		List<Empresa> list = empresaService.getAllEmpresas();
		mav.addObject("empresas", list);
		return mav;
	}

	@GetMapping(value = "Personas")
	public ModelAndView listPersona() {
		ModelAndView mav = new ModelAndView("ListPersonas");
		List<Persona> list = personaService.getAllPersonas();
		mav.addObject("personas", list);
		return mav;
	}

	@GetMapping(value = "Pedidos")
	public ModelAndView listPedido() {
		ModelAndView mav = new ModelAndView("ListPedidos");
		List<PedidoVenta> list = pedidoVentaService.getAllPedidoVentas();
		mav.addObject("pedidos", list);
		return mav;
	}

	@GetMapping(value = "Informe")
	public ModelAndView listInforme() {
		ModelAndView mav = new ModelAndView("ListInforme");
		List<ObjectNode> list = new ArrayList<ObjectNode>();
		list.addAll(getDetalles());
		mav.addObject("informe", list);
		return mav;
	}

	private List<ObjectNode> getDetalles() {
		String cliente = null;
		List<ObjectNode> list = new ArrayList<ObjectNode>();
		List<String> checkCliente = new ArrayList<String>();
		for (Empresa e : empresaService.getAllEmpresas()) {
			for (Persona p : e.getIntegrantes()) {
				cliente = e.getRazonSocial() + UtilsConstants.SEPARADOR_CLIENTE + p.getNombreCompleto();
				if (!checkCliente.contains(cliente)) {
					list.add(getDetalle(cliente));
					checkCliente.add(cliente);
				}
			}
		}
		for (Persona p : personaService.getAllPersonas()) {
			cliente = p.getNombreCompleto();
			if (!checkCliente.contains(cliente)) {
				list.add(getDetalle(cliente));
				checkCliente.add(cliente);
			}
		}
		for (PedidoVenta pV: pedidoVentaService.getAllPedidoVentas()) {
			cliente = pV.getCliente();
			if (!checkCliente.contains(cliente)) {
				list.add(getDetalle(cliente));
				checkCliente.add(cliente);
			}
		}
		return list;
	}

	private ObjectNode getDetalle(String cliente) {
		ObjectNode obj = mapper.createObjectNode();
		String detalle = "";
		String tipo = "";
		Double descuento = 0.0;
		Double importe = 0.0;
		for (PedidoVenta p : pedidoVentaService.getAllPedidoVentas()) {
			if (cliente.equals(p.getCliente())) {
				detalle += p.getNombre() + "; ";
				descuento += p.getTotalDescuentos();
				importe += p.getImporteFinal();
			}
		}
		if (detalle.length() > 0)
		detalle = detalle.substring(0, detalle.length() - 2);
		if (cliente.contains(UtilsConstants.SEPARADOR_CLIENTE)) {
			tipo = UtilsConstants.PEDIDO_EMPRESA;
		}else {
			tipo = UtilsConstants.PEDIDO_PERSONA;
		}
		obj.put("cliente", cliente);
		obj.put("total_descuento", Math.round(descuento*100.0)/100.0);
		obj.put("total_importe", Math.round(importe*100.0)/100.0);
		obj.put("detalles", detalle);
		obj.put("tipo", tipo);
		return obj;
	}
}
