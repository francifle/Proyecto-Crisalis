package com.crisalis.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crisalis.constants.UtilsConstants;
import com.crisalis.models.Pedido;
import com.crisalis.models.Producto;
import com.crisalis.services.PedidoService;
import com.crisalis.services.ProductoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("producto")
public class ProductoController {
	
	@Autowired
	private final ProductoService productoService;
	@Autowired
	private final PedidoService pedidoService;
	
	public ProductoController(ProductoService productoService, PedidoService pedidoService) {
		this.productoService = productoService;
		this.pedidoService = pedidoService;
	}

	@PostMapping(value = "save")
	public String saveProducto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		//33-36 es lo que mando en los input//
		String producto = req.getParameter("producto");
		String fecha = req.getParameter("fecha");
		String precio = req.getParameter("precio");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); //Formateo para Java
		Date date = sdf.parse(fecha + " 00:00:00"); //Crea el date de Java
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Crea el date de SQL
		Pedido pedido = new Pedido();
		pedido.setNombre(producto);
		pedido.setPrecio(Integer.valueOf(precio));
		pedido.setFecha(sqlDate);
		pedido.setTipo(UtilsConstants.TIPO_PRODUCTO);
		Producto newProducto = new Producto();
		newProducto.setPedido(pedido);
		newProducto = this.productoService.saveOrUpdateProducto(newProducto);
		return "redirect:../List/Productos";
	}
	
	@PostMapping(value = "delete")
	public String deleteProducto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("productoId");
		Producto p = this.productoService.findProductoByID(Long.valueOf(id));
		Long pedidoId = p.getPedido().getId();
		this.productoService.deleteProductoByID(Long.valueOf(id));
		this.pedidoService.deletePedidoByID(pedidoId);
		return "redirect:../List/Productos";
	}
	
	@PostMapping(value = "update")
	public String updateProducto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String id = req.getParameter("productoId");
		String fecha = req.getParameter("fecha");
		String precio = req.getParameter("precio"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date date = sdf.parse(fecha + " 00:00:00");
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Producto updatedProducto = this.productoService.findProductoByID(Long.valueOf(id));
		updatedProducto.getPedido().setFecha(sqlDate);
		updatedProducto.getPedido().setPrecio(Integer.valueOf(precio));
		updatedProducto = this.productoService.saveOrUpdateProducto(updatedProducto);
		return "redirect:../List/Productos";
	}
	
	@PostMapping(value = "deleteMultiple")
	public String deleteMultipleProductos(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		String idsParam = req.getParameter("ids");
		if (!idsParam.trim().equals("")) {
			String[] ids = idsParam.split(";");
			for (String id : ids) {
				Producto p = this.productoService.findProductoByID(Long.valueOf(id));
				Long pedidoId = p.getPedido().getId();
				this.productoService.deleteProductoByID(Long.valueOf(id));
				this.pedidoService.deletePedidoByID(pedidoId);
			}
		}
		return "redirect:../List/Productos";
	}
	
}
