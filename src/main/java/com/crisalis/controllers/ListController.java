package com.crisalis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/List")
public class ListController {
 
	@GetMapping(value = "Productos")
	public String listProducto() {
		return "ListProducto";
	}
	
	@GetMapping(value = "Servicios")
	public String listServicio() {
		return "ListServicios";
	}
	
	@GetMapping(value = "Empresas")
	public String listEmpresas() {
		return "ListEmpresas";
	}
	
}
