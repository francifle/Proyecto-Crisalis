package com.crisalis.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.constants.BasicsConstants;
import com.crisalis.models.Impuesto;
import com.crisalis.models.Pedido;
import com.crisalis.models.Servicio;
import com.crisalis.services.ImpuestoService;
import com.crisalis.services.ServicioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("servicio")
public class ServicioController {

	@Autowired
	private final ServicioService servicioService;
	
	private final ImpuestoService impuestoService;
	
	public ServicioController(ServicioService servicioService, ImpuestoService impuestoService) {
		this.servicioService = servicioService;
		this.impuestoService = impuestoService;
	}
	
	@PostMapping(value = "save")
	public String saveServicio(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		//33-36 es lo que mando en los input//
		String servicio = req.getParameter("servicio");
		String fecha = req.getParameter("fecha");
		String precio = req.getParameter("precio");
		String[] select = req.getParameterValues("select");
		Set<Impuesto> impuestos = new HashSet<>();
		for (int i = 0; i < select.length; i++) {
			Long impuestoId = Long.valueOf(Arrays.asList(select).get(i));
			impuestos.add(impuestoService.findImpuestoByID(impuestoId));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); //Formateo para Java
		Date date = sdf.parse(fecha + " 00:00:00"); //Crea el date de Java
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Crea el date de SQL
		Pedido pedido = new Pedido();
		pedido.setNombre(servicio);
		pedido.setPrecio(Integer.valueOf(precio));
		pedido.setFecha(sqlDate);
		pedido.setImpuestos(impuestos);
		pedido.setTipo(BasicsConstants.TIPO_SERVICIO);
		Servicio newServicio = new Servicio();
		newServicio.setPedido(pedido);
		newServicio = this.servicioService.saveOrUpdateServicio(newServicio);
		return "redirect:../List/Servicios";
	}
	
	@PostMapping(value = "delete")
	public String deleteProducto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("productoId");
		this.servicioService.deleteServicioByID(Long.valueOf(id));
		return "redirect:../List/Servicios";
	}
	
	@PostMapping(value = "update")
	public String updateProducto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("servicioId");
		String fecha = req.getParameter("fecha");
		String precio = req.getParameter("precio");
		String[] select = req.getParameterValues("select");
		Set<Impuesto> impuestos = new HashSet<>();
		for (int i = 0; i < select.length; i++) {
			Long impuestoId = Long.valueOf(Arrays.asList(select).get(i));
			impuestos.add(impuestoService.findImpuestoByID(impuestoId));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date = sdf.parse(fecha + " 00:00:00");
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Servicio updatedServicio = this.servicioService.findServicioByID(Long.valueOf(id));
		updatedServicio.getPedido().setFecha(sqlDate);
		updatedServicio.getPedido().setPrecio(Integer.valueOf(precio));
		updatedServicio.getPedido().setImpuestos(impuestos);
		updatedServicio = this.servicioService.saveOrUpdateServicio(updatedServicio);
		return "redirect:../List/Servicios";
	}
	
}
