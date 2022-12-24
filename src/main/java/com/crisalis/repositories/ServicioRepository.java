package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.models.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository <Servicio, Long> {}