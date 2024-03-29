package com.crisalis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.crisalis.models.Producto;
import com.crisalis.repositories.ProductoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductoService {
	@PersistenceContext
	public EntityManager em;

	// Instanciar
	private final ProductoRepository productoRepository;
	private final PedidoService pedidoService;

	public ProductoService(ProductoRepository productoRepository, PedidoService pedidoService) {
		this.productoRepository = productoRepository;
		this.pedidoService = pedidoService;
	}

	public Producto saveOrUpdateProducto(Producto producto) {
		if (!producto.getPedido().getNombre().equals("")) {
			producto.setPedido(pedidoService.savePedido(producto.getPedido()));
			return this.productoRepository.save(producto);
		}
		return null;
	}

	public ArrayList<Producto> getAllProductos() {
		ArrayList<Producto> list = (ArrayList<Producto>) productoRepository.findAll();
		return list;

	}

	public Producto findProductoByID(Long id) {
		return this.productoRepository.getReferenceById(id);
	}

	public Producto findProductoByPedidoID(Long id) {
		for (Producto p : getAllProductos()) {
			if (p.getPedido().getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public void deleteProductoByID(Long id) {
		this.productoRepository.delete(findProductoByID(id));
	}

}
