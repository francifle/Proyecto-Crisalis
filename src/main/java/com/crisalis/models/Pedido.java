package com.crisalis.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.crisalis.constants.UtilsConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Pedido")
@Table(name="Pedido")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pedido extends GenericModel{

    @Column(name="nombre", nullable=false)
	private String nombre;
    @Column(name="precio", nullable=false)
	private Integer precio;
    @Column(name="fecha", nullable=false)
    private Date fecha;
    @Column(name="tipo", nullable=false)
	private Integer tipo;
    
    @ManyToMany
    private Set<Impuesto> impuestos;

	public Set<Impuesto> getImpuestos() {
		return impuestos;
	}
	
	public List<Impuesto> getImpuestosList() {
		return new ArrayList<>(getImpuestos());
	}

	public void setImpuestos(Set<Impuesto> impuestos) {
		this.impuestos = impuestos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getPrecioIVA() {
		Double value =  getPrecio() * UtilsConstants.IVA;
		return Math.round(value*100.0)/100.0;
	}
	
	public Double getPrecioIIBB() {
		Double value = getPrecio() * UtilsConstants.IIBB;
		return Math.round(value*100.0)/100.0;
	}
	
	public Double getPrecioImpuestos() {
		Double value = 0.0;
		for (int i = 0; i < getImpuestosList().size(); i++) {
			Impuesto impuesto = getImpuestosList().get(i);
			if(impuesto.getNombre().equals("IVA"))
				value += getPrecioIVA();
			if(impuesto.getNombre().equals("IIBB"))
				value += getPrecioIIBB();
		}
		return Math.round(value*100.0)/100.0;
	}
	
	public Double getPrecioTotal() {
		Double value = getPrecio() + getPrecioImpuestos();
		return Math.round(value*100.0)/100.0;
	}
}
