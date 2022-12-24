package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Long> {}