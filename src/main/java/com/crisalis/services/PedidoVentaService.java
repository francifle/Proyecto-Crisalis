package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.PedidoVenta;
import com.crisalis.repositories.PedidoVentaRepository;

@Service
public class PedidoVentaService {

	private final PedidoVentaRepository pVRepository;

	public PedidoVentaService(PedidoVentaRepository pVRepository) {
		this.pVRepository = pVRepository;
	}

	public PedidoVenta saveOrUpdatePedidoVenta(PedidoVenta pedidoventa) {
		if (!pedidoventa.getNombre().equals("")) {
			return this.pVRepository.save(pedidoventa);
		}
		return null;
	}

	public ArrayList<PedidoVenta> getAllPedidoVentas() {
		ArrayList<PedidoVenta> list = (ArrayList<PedidoVenta>) pVRepository.findAll();
		return list;

	}

	public PedidoVenta findPedidoVentaByID(Long id) {
		return this.pVRepository.getReferenceById(id);
	}

	public void deletePedidoVentaByID(Long id) {
		this.pVRepository.delete(findPedidoVentaByID(id));

	}
}
