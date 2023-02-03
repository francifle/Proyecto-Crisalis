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

import com.crisalis.models.Empresa;
import com.crisalis.models.OrdenVenta;
import com.crisalis.models.PedidoVenta;
import com.crisalis.models.Persona;
import com.crisalis.services.EmpresaService;
import com.crisalis.services.PersonaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("empresa")
public class EmpresaController {

	@Autowired
	private final EmpresaService empresaService;
	@Autowired
	private final PersonaService personaService;
	
	public EmpresaController(EmpresaService empresaService, PersonaService personaService) {
		this.empresaService = empresaService;
		this.personaService = personaService;
	}
	
	@PostMapping(value = "save")
	public String saveEmpresa(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String fecha = req.getParameter("fecha");
		String razonsocial = req.getParameter("razonsocial");
		String cuit = req.getParameter("cuit");
		String[] select = req.getParameterValues("select");
		Set<Persona> personas = new HashSet<>();
		for (int i = 0; i < select.length; i++) {
			Long personaId = Long.valueOf(Arrays.asList(select).get(i));
			personas.add(personaService.findPersonaByID(personaId));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); //Formateo para Java
		Date date = sdf.parse(fecha + " 00:00:00"); //Crea el date de Java
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Crea el date de SQL
		Empresa newEmpresa = new Empresa(); //Se realiza una instancia vacia del objeto
		newEmpresa.setFecha(sqlDate);
		newEmpresa.setRazonSocial(razonsocial);
		newEmpresa.setCuit(cuit);
		newEmpresa.setIntegrantes(personas);
		newEmpresa = this.empresaService.saveOrUpdateEmpresa(newEmpresa);
		return "redirect:../List/Empresas";
	}
	
	@PostMapping(value = "delete")
	public String deleteEmpresa(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("empresaId");
		this.empresaService.deleteEmpresaByID(Long.valueOf(id));
		return "redirect:../List/Empresas";
	}
	
	@PostMapping(value = "update")
	public String updateEmpresa(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("empresaId");
		String fecha = req.getParameter("fecha");
		String razonsocial = req.getParameter("razonsocial"); 
		String cuit = req.getParameter("cuit");
		String valuesSelect = req.getParameter("selectInput");
		String[] select = valuesSelect.split(";");
		Set<Persona> personas = new HashSet<>();
		for (int i = 0; i < select.length; i++) {
			Long personaId = Long.valueOf(Arrays.asList(select).get(i));
			personas.add(personaService.findPersonaByID(personaId));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date = sdf.parse(fecha + " 00:00:00");
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Empresa updatedEmpresa = this.empresaService.findEmpresaByID(Long.valueOf(id));
		updatedEmpresa.setFecha(sqlDate);
		updatedEmpresa.setRazonSocial(String.valueOf(razonsocial));
		updatedEmpresa.setCuit(String.valueOf(cuit));
		updatedEmpresa.setIntegrantes(personas);
		updatedEmpresa = this.empresaService.saveOrUpdateEmpresa(updatedEmpresa);
		return "redirect:../List/Empresas";
	}
	
	@PostMapping(value = "deleteMultiple")
	public String deleteMultipleEmpresas(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String idsParam = req.getParameter("ids");
		if (!idsParam.trim().equals("")) {
			String[] ids = idsParam.split(";");
			for (String id : ids) {				
				this.empresaService.deleteEmpresaByID(Long.valueOf(id));
			}
		}
		return "redirect:../List/Empresas";
	}
	
}
