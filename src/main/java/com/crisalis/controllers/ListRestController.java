package com.crisalis.controllers;

import java.util.List;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.models.Impuesto;
import com.crisalis.models.Persona;
import com.crisalis.services.ImpuestoService;
import com.crisalis.services.PersonaService;

@RestController
@RequestMapping("/ListRest")
public class ListRestController {

	
	private ImpuestoService impuestoService;
	private PersonaService personaService;
	
	public ListRestController(ImpuestoService impuestoService, PersonaService personaService ) {
		this.impuestoService = impuestoService;
		this.personaService = personaService;
	}
	
	@GetMapping(value = "impuestos", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<Impuesto> listImpuestos(@RequestParam(value = "term",required = false) String term) {
		if (term == null) {
			return impuestoService.getAllImpuestos();
		}else {
			return impuestoService.getAllImpuestosByNombre(term);
		}
	}
	
	@GetMapping(value = "personas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<Persona> listPersonas(@RequestParam(value = "term",required = false) String term) {
		if (term == null) {
			return personaService.getAllPersonas();
		}else {
			return personaService.getAllPersonasByNombre(term);
		}
	}
}