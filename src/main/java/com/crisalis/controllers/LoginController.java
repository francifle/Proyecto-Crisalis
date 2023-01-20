package com.crisalis.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.models.Usuario;
import com.crisalis.repositories.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("")

public class LoginController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "")
    public String index(){
        return "index";
    }
	
	@GetMapping(value = "/home")
    public String home(){
        return "index";
    }
	
    @GetMapping ("/register")
    public String register(){
        return "register";
    }
    
    @PostMapping ("/home")
    public String home(HttpServletRequest req, HttpServletResponse resp){
		String mail = req.getParameter("mail");
		String pass = req.getParameter("pass");
		Usuario user = new Usuario();
		user.setMail(mail);
		user.setPass(pass);
    	ArrayList<Usuario> list = (ArrayList<Usuario>) usuarioRepository.findAll();
    	System.out.println(user);
        for(int i=0;i<list.size();i++) {
        	if(user.equals(list.get(i))) {
        		System.out.println(list.get(i));
        		return "home";
        	}
            	
        }
        
        return "loginerror";
    }
}

