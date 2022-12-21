package com.crisalis.services;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.crisalis.exception.custom.EmptyElementException;
import com.crisalis.models.Usuario;
import com.crisalis.models.dto.UserDTO;
import com.crisalis.repositories.UsuarioRepository;

@Service
public class UsuarioService {

   private final UsuarioRepository usuarioRepository;
   
   public UsuarioService(UsuarioRepository usuarioRepository) {
  		this.usuarioRepository = usuarioRepository;
   	}
   
   public Usuario saveUser(UserDTO userDTO) {
	   if(checkUserDTO(userDTO)) {
		   	Usuario usuario = new Usuario(userDTO);
	   return this.usuarioRepository.save(usuario);
	   }
	return null;
	   
   	}
 
   private Boolean checkUserDTO(UserDTO userDTO) {
	   
	   if(StringUtils.isAllEmpty(userDTO.getNombre())) {
		   throw new EmptyElementException("Name is empty");
  		}
	   if(StringUtils.isAllEmpty(userDTO.getPassword())) {
		   throw new EmptyElementException("Password is empty");
  		}
	   if(StringUtils.isAllEmpty(userDTO.getUsername())) {
		   throw new EmptyElementException("Usernaname is empty");  
  		}
	return Boolean.TRUE;
	   
   	}
   
   }
   
