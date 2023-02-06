package com.crisalis.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.models.Empresa;
import com.crisalis.models.PedidoVenta;
import com.crisalis.models.Persona;
import com.crisalis.services.EmpresaService;
import com.crisalis.services.PersonaService;

@RestController
@RequestMapping("/PersonaRest")
public class PersonaRestController {

	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private PersonaService personaService;
	
	public PersonaRestController(EmpresaService empresaService, PersonaService personaService) {
		this.personaService = personaService;
		this.empresaService = empresaService;
	}
	
	@GetMapping(value = "checkIntegrantes/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> checkIntegrantes(@PathVariable Long id) {
		HashMap<String, String> map = new HashMap<>();
		ArrayList<Empresa> listEmpresa = empresaService.getAllEmpresas();
		Persona p = personaService.findPersonaByID(id);
		Integer i = 0;
		for (Empresa e : listEmpresa) {
			if (e.getIntegrantes().contains(p)) {
				map.put("empresa" + i, e.getRazonSocial());
				i++;
			}
		}
		if (map.isEmpty()) {
			map.put("SinEmpresa", null);
		}else {
			map.put("persona", p.getNombreCompleto());
		}
		
		return map;
	}
	
	@GetMapping(value = "changeEstado/{id}")
	public Persona updateEstado(@PathVariable Long id) {
		Persona persona = personaService.findPersonaByID(id);
		persona.setEstado(!persona.getEstado());
		return personaService.saveOrUpdatePersona(persona);
	}
	
}
