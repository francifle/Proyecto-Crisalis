package com.crisalis.services;

import org.springframework.stereotype.Service;

import com.crisalis.repositories.ProductoRepository;

@Service
public class ProductoService {

	private final ProductoRepository productoRepository;
	  
    public ProductoService(ProductoRepository productoRepository) {
  		this.productoRepository = productoRepository;
   	}
}
