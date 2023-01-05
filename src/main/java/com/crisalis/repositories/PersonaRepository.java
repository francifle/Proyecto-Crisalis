package com.crisalis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crisalis.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository <Persona, Long> {

	@Query("from Persona where nombre like %:nombre_ingresado% or apellido like %:nombre_ingresado%")
	List<Persona> getAllPersonasByNombre(@Param("nombre_ingresado") String nombreIngresado);
	
}
