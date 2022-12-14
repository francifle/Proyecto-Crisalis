package com.crisalis.models;

import com.crisalis.constants.BasicsConstants;

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
@Entity(name="Servicio")
@Table(name="Servicio")
public class Servicio extends GenericModel {

	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	

	public Double getCostoSoporte(Integer meses) {
		return getPedido().getPrecioTotal() * BasicsConstants.CARGO_SOPORTE * meses;
	}
	
	public Double getPrecioTotalServicio(Integer meses) {
		Double value = getPedido().getPrecioTotal() + getCostoSoporte(meses);
		return Math.round(value*100.0)/100.0;
	}
	
}
