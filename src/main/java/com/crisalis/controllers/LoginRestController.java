package com.crisalis.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.models.Usuario;
import com.crisalis.repositories.UsuarioRepository;

@RestController
@RequestMapping("LoginRest")
public class LoginRestController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	//Se usa para el metodo AJAX para el modal de los datos
	
	@GetMapping(value = "checkUser/{param}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean home(@PathVariable String param) {
		if(param.trim().equals("-"))
			return false;
		String[] params = param.split("-");
		String mail = params[0].trim();
		String pass = params[1].trim();
		Usuario user = new Usuario();
		user.setMail(mail);
		user.setPass(pass);
		ArrayList<Usuario> list = (ArrayList<Usuario>) usuarioRepository.findAll();
		System.out.println(user);
		for (int i = 0; i < list.size(); i++) {
			if (user.equals(list.get(i))) {
				return true;
			}

		}
		return false;
	}

}
