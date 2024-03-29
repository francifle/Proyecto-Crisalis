package com.crisalis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.models.dto.UserDTO;
import com.crisalis.services.UsuarioService;
import com.crisalis.utils.Encrypter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
		
	
	@PostMapping(value = "")
	public String saveUsuario(HttpServletRequest req, HttpServletResponse resp) {
		String nombre = req.getParameter("new-nombre");
		String pass = req.getParameter("new-pass");
		pass = Encrypter.hash(pass);
		String username = req.getParameter("new-username");
		UserDTO userDTO = new UserDTO(username, pass, nombre);
		usuarioService.saveUser(userDTO);
		return "index";
	}
	
}
