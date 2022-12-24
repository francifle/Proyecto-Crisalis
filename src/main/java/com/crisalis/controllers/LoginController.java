package com.crisalis.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.constants.BasicsConstants;
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
    	ArrayList<Usuario> list = (ArrayList<Usuario>) usuarioRepository.findAll();
        for(int i=0;i<list.size();i++) {
        	if(mail.equals(list.get(i).getMail()) && pass.equals(list.get(i).getPass()))
            	return "home";
        }
        
        return "loginerror";
    }
}

