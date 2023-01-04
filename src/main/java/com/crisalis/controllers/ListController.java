package com.crisalis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crisalis.models.Persona;
import com.crisalis.models.Producto;
import com.crisalis.models.Servicio;
import com.crisalis.services.PersonaService;
import com.crisalis.services.ProductoService;
import com.crisalis.services.ServicioService;

@Controller
@RequestMapping("/List")
public class ListController {
 
	@Autowired
	private final ProductoService productoService;
	@Autowired
	private final ServicioService servicioService;
	@Autowired
	private final PersonaService personaService;
	
	public ListController(ProductoService productoService, ServicioService servicioService, PersonaService personaService) {
		this.productoService = productoService;
		this.servicioService = servicioService;
		this.personaService = personaService;
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
	public String listEmpresa() {
		return "ListEmpresas";
	}
	
	@GetMapping(value = "Personas")
	public ModelAndView listPersona() {
		ModelAndView mav = new ModelAndView("ListPersonas");
		List<Persona> list = personaService.getAllPersonas();
		mav.addObject("personas", list);
		return mav;
	}	
	
}
