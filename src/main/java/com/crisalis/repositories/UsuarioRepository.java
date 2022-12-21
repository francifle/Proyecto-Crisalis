package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crisalis.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
	
	
	
}