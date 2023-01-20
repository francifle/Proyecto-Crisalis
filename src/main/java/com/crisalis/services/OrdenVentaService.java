package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.OrdenVenta;
import com.crisalis.repositories.OrdenVentaRepository;

@Service
public class OrdenVentaService {

	private final OrdenVentaRepository oVRepository;

	public OrdenVentaService(OrdenVentaRepository oVRepository) {
		this.oVRepository = oVRepository;
	}

	public OrdenVenta saveOrUpdateOrdenVenta(OrdenVenta ordenventa) {
		if (!ordenventa.getNombre().equals("")) {
			return this.oVRepository.save(ordenventa);
		}
		return null;
	}

	public ArrayList<OrdenVenta> getAllOrdenVentas() {
		ArrayList<OrdenVenta> list = (ArrayList<OrdenVenta>) oVRepository.findAll();
		return list;

	}

	public OrdenVenta findOrdenVentaByID(Long id) {
		return this.oVRepository.getReferenceById(id);
	}

	public void deleteOrdenVentaByID(Long id) {
		this.oVRepository.delete(findOrdenVentaByID(id));

	}
	
}
