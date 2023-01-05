package com.crisalis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisalis.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository <Empresa, Long> {}
