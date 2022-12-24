package com.crisalis.services.interfaces;

import java.util.ArrayList;

import com.crisalis.models.Usuario;


public interface IUsuarioService {

	ArrayList<Usuario> getAllUsuarios();	
	
	Usuario saveUsuario(Usuario usuario);
}
