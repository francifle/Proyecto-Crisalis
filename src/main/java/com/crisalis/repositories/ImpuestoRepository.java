package com.crisalis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crisalis.models.Impuesto;

@Repository
public interface ImpuestoRepository extends JpaRepository <Impuesto, Long> {
	
	@Query("from Impuestos")
	public List<Impuesto> getAll();
	
	@Query("from Impuestos where nombre like %:nombre_ingresado%")
	public List<Impuesto> getAllImpuestosByNombre(@Param("nombre_ingresado") String nombreIngresado);
	
}