package com.crisalis.controllers;

import java.util.List;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.models.Impuesto;
import com.crisalis.services.ImpuestoService;

@RestController
@RequestMapping("/ListRest")
public class ListRestController {

	
	private ImpuestoService impuestoService;
	
	public ListRestController(ImpuestoService impuestoService) {
		this.impuestoService = impuestoService;
	}
	
	@GetMapping(value = "impuestos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<Impuesto> listImpuestos(@RequestParam(value = "term",required = false) String term) {
		if (term == null) {
			return impuestoService.getAllImpuestos();
		}else {
			return impuestoService.getAllImpuestosByNombre(term);
		}
	}
}