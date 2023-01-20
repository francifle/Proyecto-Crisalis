package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.models.OrdenVenta;

@Repository
public interface OrdenVentaRepository extends JpaRepository <OrdenVenta, Long> {}
