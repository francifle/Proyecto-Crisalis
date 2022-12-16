package com.crisalis.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crisalis.models.Usuario;
import com.crisalis.repositories.UsuarioRepository;

@CrossOrigin(origins="*")
@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    @GetMapping
    public String index(){
        return "index";
    }
    @GetMapping ("/register")
    public String register(){
        return "register";
    }
    @GetMapping ("/home")
    public String home(){
    	ArrayList<Usuario> list = (ArrayList<Usuario>) usuarioRepository.findAll();
        for(int i=0;i<list.size();i++) {
        System.out.println(list.get(i));
        }
    	return "home";
    }
}

