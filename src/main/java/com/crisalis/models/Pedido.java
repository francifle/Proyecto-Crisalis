package com.crisalis.models;

import com.crisalis.constants.BasicsConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity(name="Pedido")
@Table(name="Pedido")
public class Pedido extends GenericModel{

    @Column(name="nombre", nullable=false)
	private String nombre;
    @Column(name="precio", nullable=false)
	private Integer precio;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	public Double getPrecioIVA() {
		return getPrecio() * BasicsConstants.IVA;
	}
	
	public Double getPrecioIIBB() {
		return getPrecio() * BasicsConstants.IIBB;
	}
	
	public Double getPrecioTotal() {
		return getPrecio() + getPrecioIVA() + getPrecioIIBB();
	}
}
