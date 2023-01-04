package com.crisalis.services;

import org.springframework.stereotype.Service;

import com.crisalis.models.Pedido;
import com.crisalis.repositories.PedidoRepository;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	  
    public PedidoService(PedidoRepository pedidoRepository) {
  		this.pedidoRepository = pedidoRepository;
   	}
    
    public Pedido savePedido(Pedido pedido) {
 	   if(!pedido.getNombre().equals("")) {
 	   return this.pedidoRepository.save(pedido);
 	   }
 	return null;
 	}
}