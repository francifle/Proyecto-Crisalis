package com.crisalis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="OrdenVenta")
@Table(name="OrdenVenta")
public class OrdenVenta extends GenericModel{

	@Column(name="tipo", nullable=false)
	private Integer tipo;
	@Column(name="nombre", nullable=false)
	private String nombre; 
	@Column(name="importe", nullable=false)
	private Double importe;
	@Column(name="cargosDetalle", nullable=false)
	private String cargosDetalle;
	
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getCargosDetalle() {
		return cargosDetalle;
	}
	public void setCargosDetalle(String cargosDetalle) {
		this.cargosDetalle = cargosDetalle;
	}

	public String getDetalles() {
		return getNombre() + " : " + getImporte() + " con " + getCargosDetalle();
	}
	
}
