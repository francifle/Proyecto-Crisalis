package com.crisalis.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crisalis.models.Impuesto;
import com.crisalis.repositories.ImpuestoRepository;

@Service
public class ImpuestoService {

	//Instanciar
	   private final ImpuestoRepository impuestoRepository;
	   
	   public ImpuestoService(ImpuestoRepository impuestoRepository) {
	  		this.impuestoRepository = impuestoRepository;
	   	}
	
    public List<Impuesto> getAllImpuestos(){
    	List<Impuesto> list = impuestoRepository.getAll();
		return list;
    	
    }
    
    public Impuesto findImpuestoByID(Long id) {
    	return this.impuestoRepository.getReferenceById(id);
    }

	public List<Impuesto> getAllImpuestosByNombre(String term) {
		return this.impuestoRepository.getAllImpuestosByNombre(term);
	}
	
}
