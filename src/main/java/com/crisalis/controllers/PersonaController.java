package com.crisalis.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.models.Persona;
import com.crisalis.services.PersonaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("persona")
public class PersonaController {

	@Autowired
	private final PersonaService personaService;
	
	public PersonaController(PersonaService personaService) {
		this.personaService = personaService;
	}
	
	@PostMapping(value = "save")
	public String savePersona(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String nombre = req.getParameter("nombre");
		String apellido = req.getParameter("apellido");
		String dni = req.getParameter("dni");
		Persona newPersona = new Persona();
		newPersona.setNombre(nombre);
		newPersona.setApellido(apellido);
		newPersona.setDni(dni);
		newPersona = this.personaService.saveOrUpdatePersona(newPersona);
		return "redirect:../List/Personas";
	}
	
	@PostMapping(value = "delete")
	public String deletePersona(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("personaId");
		this.personaService.deletePersonaByID(Long.valueOf(id));
		return "redirect:../List/Personas";
	}
	
	@PostMapping(value = "update")
	public String updatePersona(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("personaId");
		String nombre = req.getParameter("nombre");
		String apellido = req.getParameter("apellido"); 
		String dni = req.getParameter("dni"); 
		Persona updatedPersona = this.personaService.findPersonaByID(Long.valueOf(id));
		updatedPersona.setNombre(String.valueOf(nombre));
		updatedPersona.setApellido(String.valueOf(apellido));
		updatedPersona.setDni(String.valueOf(dni));
		updatedPersona = this.personaService.saveOrUpdatePersona(updatedPersona);
		return "redirect:../List/Personas";
	}
	
}
