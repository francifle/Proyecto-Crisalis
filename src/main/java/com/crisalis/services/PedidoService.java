package com.crisalis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.models.Pedido;
import com.crisalis.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private final PedidoRepository pedidoRepository;

	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	public Pedido savePedido(Pedido pedido) {
		if (!pedido.getNombre().equals("")) {
			return this.pedidoRepository.save(pedido);
		}
		return null;
	}

	public List<Pedido> getAllPedidos() {
		return this.pedidoRepository.findAll();
	}

	public Pedido findPedidoByID(Long id) {
		return this.pedidoRepository.getReferenceById(id);
	}

	public Pedido findPedidoByNombre(String nombre) {
		for (Pedido p : getAllPedidos()) {
			if (p.getNombre().equals(nombre)) {
				return p;
			}
		}
		return null;
	}

}