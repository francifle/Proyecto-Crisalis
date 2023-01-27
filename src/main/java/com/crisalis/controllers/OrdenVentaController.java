package com.crisalis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.crisalis.services.OrdenVentaService;

@Controller
public class OrdenVentaController {
	
	@Autowired
	private OrdenVentaService ordenVentaService;
	
	public OrdenVentaController(OrdenVentaService ordenVentaService) {
		this.ordenVentaService = ordenVentaService;
	}
}
