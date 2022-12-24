package com.crisalis.models;

import com.crisalis.constants.BasicsConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity(name="Producto")
@Table(name="Producto")
public class Producto extends GenericModel {
	
    @Column(name="anios", nullable=true)
	private Integer anios;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId
	private Pedido pedido;

	public Integer getAnios() {
		return anios;
	}

	public void setAnios(Integer anios) {
		this.anios = anios;
	}
	
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Double getPrecioGarantia() {
		return getPedido().getPrecioTotal() * BasicsConstants.PORCENTAJE_GARANTIA * getAnios();
	}
	
	public Double getPrecioTotalProducto() {
		return getPedido().getPrecioTotal() + getPrecioGarantia();
	}
	
	
}
