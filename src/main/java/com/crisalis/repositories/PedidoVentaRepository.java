package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.models.PedidoVenta;

@Repository
public interface PedidoVentaRepository extends JpaRepository <PedidoVenta, Long>{}
