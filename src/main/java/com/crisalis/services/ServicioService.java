package com.crisalis.services;

import org.springframework.stereotype.Service;

import com.crisalis.repositories.ServicioRepository;

@Service
public class ServicioService {

	private final ServicioRepository servicioRepository;
	  
    public ServicioService(ServicioRepository servicioRepository) {
  		this.servicioRepository = servicioRepository;
   	}
}
