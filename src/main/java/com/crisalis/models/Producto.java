package com.crisalis.models;

import com.crisalis.constants.UtilsConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Producto")
@Table(name="Producto")
public class Producto extends GenericModel {
    
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Double getPrecioGarantia(Integer anios) {
		return getPedido().getPrecioTotal() * UtilsConstants.PORCENTAJE_GARANTIA * anios ;
	}
	
	public Double getPrecioTotalProducto(Integer anios) {
		Double value = getPedido().getPrecioTotal() + getPrecioGarantia(anios);
		return Math.round(value*100.0)/100.0;
	}
	
	
}
