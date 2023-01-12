package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.Producto;
import com.crisalis.models.Servicio;
import com.crisalis.repositories.ServicioRepository;

@Service
public class ServicioService {
	
	//Instanciar
	private final ServicioRepository servicioRepository;
	private final PedidoService pedidoService;

    public ServicioService(ServicioRepository servicioRepository, PedidoService pedidoService) {
  		this.servicioRepository = servicioRepository;
  		this.pedidoService = pedidoService;
   	}
    
    public Servicio saveOrUpdateServicio(Servicio servicio) {
 	   if(!servicio.getPedido().getNombre().equals("")) {
 		  servicio.setPedido(pedidoService.savePedido(servicio.getPedido()));
 	   return this.servicioRepository.save(servicio);
 	   }
 	return null;
 	}
    
    public ArrayList<Servicio> getAllServicios(){
    	ArrayList<Servicio> list = (ArrayList<Servicio>) servicioRepository.findAll();
		return list;
    	
    }
    
    public Servicio findServicioByID(Long id) {
    	return this.servicioRepository.getReferenceById(id);
    }
    
    public void deleteServicioByID(Long id) {
    	this.servicioRepository.delete(findServicioByID(id));
    }

	public Servicio findServicioByPedidoID(Long id) {
		for (Servicio s : getAllServicios()) {
			if (s.getPedido().getId().equals(id)) {
				return s;
			}
		}
		return null;
	}
    
}
