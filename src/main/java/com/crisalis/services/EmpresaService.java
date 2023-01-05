package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.Empresa;
import com.crisalis.repositories.EmpresaRepository;

@Service
public class EmpresaService {
	
	//Instanciar
		private final EmpresaRepository empresaRepository;

	    public EmpresaService(EmpresaRepository empresaRepository) {
	  		this.empresaRepository = empresaRepository;
	   	}
	    
	    public Empresa saveOrUpdateEmpresa(Empresa empresa) {
	  	   if(!empresa.getRazonSocial().equals("")){
	  		   return this.empresaRepository.save(empresa);
	  	   }
	  	return null;
	  	}
	     
	     public ArrayList<Empresa> getAllEmpresas(){
	     	ArrayList<Empresa> list = (ArrayList<Empresa>) empresaRepository.findAll();
	 		return list;
	     	
	     }
	     
	     public Empresa findEmpresaByID(Long id) {
	     	return this.empresaRepository.getReferenceById(id);
	     }
	     
	     public void deleteEmpresaByID(Long id) {
	     	this.empresaRepository.delete(findEmpresaByID(id));
	     
	     }

}
	

