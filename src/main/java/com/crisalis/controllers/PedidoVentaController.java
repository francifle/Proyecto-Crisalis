package com.crisalis.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.constants.UtilsConstants;
import com.crisalis.models.Empresa;
import com.crisalis.models.OrdenVenta;
import com.crisalis.models.PedidoVenta;
import com.crisalis.models.Persona;
import com.crisalis.services.EmpresaService;
import com.crisalis.services.OrdenVentaService;
import com.crisalis.services.PedidoVentaService;
import com.crisalis.services.PersonaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("pedidoventa")
public class PedidoVentaController {

	@Autowired
	private PersonaService personaService;
	@Autowired
	private EmpresaService empresaService;
	// @Autowired
	// private PedidoService pedidoService;
	@Autowired
	private PedidoVentaService pedidoVentaService;
	@Autowired
	private OrdenVentaService ordenVentaService;

	public PedidoVentaController(PersonaService personaService, EmpresaService empresaService,
			PedidoVentaService pedidoVentaService, OrdenVentaService ordenVentaService) {
		this.personaService = personaService;
		this.empresaService = empresaService;
		this.pedidoVentaService = pedidoVentaService;
		this.ordenVentaService = ordenVentaService;
	}

	@PostMapping(value = "save")
	public String savePedidoVenta(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		PedidoVenta pedidoVenta = new PedidoVenta();
		Double impTotal = Double.valueOf(req.getParameter("impTotal"));
		Double descTotal = Double.valueOf(req.getParameter("descTotal"));
		Long personaId = Long.valueOf(req.getParameter("persona"));
		Long empresaId = Long.valueOf(req.getParameter("nombre"));
		String fecha = req.getParameter("fecha");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); // Formateo para Java
		Date date = sdf.parse(fecha + " 00:00:00"); // Crea el date de Java
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Crea el date de SQL
		String[] listTipoOrden = req.getParameterValues("tipoOrden");
		String[] listNombreOrden = req.getParameterValues("nombreOrden");
		String[] listImporteOrden = req.getParameterValues("importeOrden");
		String[] listCargoOrden = req.getParameterValues("cargoOrden");
		String cliente = getClienteNombre(personaId, empresaId);
		Set<OrdenVenta> ordenes = getOrdenes(listTipoOrden, listNombreOrden, listImporteOrden, listCargoOrden);
		pedidoVenta.setCliente(cliente);
		pedidoVenta.setEstado(UtilsConstants.ESTADO_ACTIVO);
		pedidoVenta.setFecha(sqlDate);
		pedidoVenta.setImporteTotal(impTotal);
		pedidoVenta.setNombre("Nuevo pedido");
		pedidoVenta.setOrdenes(ordenes);
		pedidoVenta.setTotalDescuentos(descTotal);
		pedidoVenta = pedidoVentaService.saveOrUpdatePedidoVenta(pedidoVenta);
		pedidoVenta.setNombre(UtilsConstants.PEDIDO_VENTA_NOMBRE + " - " + pedidoVenta.getId());
		pedidoVenta = pedidoVentaService.saveOrUpdatePedidoVenta(pedidoVenta);
		return "redirect:../List/Pedidos";
	}
	
	@PostMapping(value = "update")
	public String updatePedidoVenta(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		Long id = Long.valueOf(req.getParameter("pedidoVentaId"));
		PedidoVenta pedidoVenta = pedidoVentaService.findPedidoVentaByID(id );
		Double impTotal = Double.valueOf(req.getParameter("impTotal"));
		Double descTotal = Double.valueOf(req.getParameter("descTotal"));
		Long personaId = Long.valueOf(req.getParameter("persona"));
		Long empresaId = Long.valueOf(req.getParameter("nombre"));
		String fecha = req.getParameter("fecha");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); // Formateo para Java
		Date date = sdf.parse(fecha + " 00:00:00"); // Crea el date de Java
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Crea el date de SQL
		String[] listTipoOrden = req.getParameterValues("tipoOrden");
		String[] listNombreOrden = req.getParameterValues("nombreOrden");
		String[] listImporteOrden = req.getParameterValues("importeOrden");
		String[] listCargoOrden = req.getParameterValues("cargoOrden");
		String cliente = getClienteNombre(personaId, empresaId);
		Set<OrdenVenta> ordenes = getOrdenes(listTipoOrden, listNombreOrden, listImporteOrden, listCargoOrden);
		pedidoVenta.setCliente(cliente);
		pedidoVenta.setFecha(sqlDate);
		pedidoVenta.setImporteTotal(impTotal);
		pedidoVenta.setOrdenes(ordenes);
		pedidoVenta.setTotalDescuentos(descTotal);
		pedidoVenta = pedidoVentaService.saveOrUpdatePedidoVenta(pedidoVenta);
		return "redirect:../List/Pedidos";
	}

	private Set<OrdenVenta> getOrdenes(String[] listTipoOrden, String[] listNombreOrden, String[] listImporteOrden,
			String[] listCargoOrden) {
		Set<OrdenVenta> value = new HashSet<>();
		OrdenVenta ordenVenta = null;
		for (int i = 0; i < listTipoOrden.length; i++) {
			ordenVenta = new OrdenVenta();
			String tipo = Arrays.asList(listTipoOrden).get(i);
			String nombre = Arrays.asList(listNombreOrden).get(i);
			Double importe = Double.valueOf(Arrays.asList(listImporteOrden).get(i));
			String cargos = Arrays.asList(listCargoOrden).get(i);
			if (tipo.equals("Producto")) {
				ordenVenta.setTipo(UtilsConstants.TIPO_PRODUCTO);
			} else {
				ordenVenta.setTipo(UtilsConstants.TIPO_SERVICIO);
			}
			ordenVenta.setCargosDetalle(cargos);
			ordenVenta.setImporte(importe);
			ordenVenta.setNombre(nombre);
			value.add(ordenVentaService.saveOrUpdateOrdenVenta(ordenVenta));
		}
		return value;
	}

	private String getClienteNombre(Long personaId, Long empresaId) {
		String value = "";
		Persona p = personaService.findPersonaByID(personaId);
		if (empresaId == 0L) {
			value = p.getNombreCompleto();
		} else {
			Empresa e = empresaService.findEmpresaByID(empresaId);
			value += e.getRazonSocial();
			value += UtilsConstants.SEPARADOR_CLIENTE;
			value += p.getNombreCompleto();
		}
		return value;
	}

	@PostMapping(value = "deleteMultiple")
	public String deleteMultiplePedidos(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String idsParam = req.getParameter("ids");
		if (!idsParam.trim().equals("")) {
			String[] ids = idsParam.split(";");
			ArrayList<Long> idsItem =  new ArrayList<Long>();
			PedidoVenta p = null;
			//Separo los ids de la lista
			for (String id : ids) {
				idsItem.clear();
				p = pedidoVentaService.findPedidoVentaByID(Long.valueOf(id));
				for (OrdenVenta o : p.getOrdenes()) {
					 idsItem.add(o.getId());
				}
				//Delete pedido_venta y pedido_venta_ordenes
				pedidoVentaService.deletePedidoVentaByID(Long.valueOf(id));
				for (Long idItem : idsItem) {
					//Delete orden_venta
					ordenVentaService.deleteOrdenVentaByID(idItem);
				}
			}
		}
		return "redirect:../List/Pedidos";
	}
	
}
