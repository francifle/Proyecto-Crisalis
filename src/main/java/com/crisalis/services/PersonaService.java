package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.Persona;
import com.crisalis.repositories.PersonaRepository;

@Service
public class PersonaService {
	//Instanciar
	private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
  		this.personaRepository = personaRepository;
   	}
    
    public Persona saveOrUpdatePersona(Persona persona) {
  	   if(!persona.getNombre().equals("") && !persona.getApellido().equals("")){
  		   return this.personaRepository.save(persona);
  	   }
  	return null;
  	}
     
     public ArrayList<Persona> getAllPersonas(){
     	ArrayList<Persona> list = (ArrayList<Persona>) personaRepository.findAll();
 		return list;
     	
     }
     
     public Persona findPersonaByID(Long id) {
     	return this.personaRepository.getReferenceById(id);
     }
     
     public void deletePersonaByID(Long id) {
     	this.personaRepository.delete(findPersonaByID(id));
     
     }
}
