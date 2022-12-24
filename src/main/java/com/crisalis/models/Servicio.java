package com.crisalis.models;

import com.crisalis.constants.BasicsConstants;

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
@Entity(name="Servicio")
@Table(name="Servicio")
public class Servicio extends GenericModel {

	private Integer meses;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId
	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}
	
	public Double getCostoMensual() {
		return getPedido().getPrecioTotal() * BasicsConstants.CARGO_MENSUAL * getMeses();
	}
	
	public Double getPrecioTotalServicio() {
		return getPedido().getPrecioTotal() + getCostoMensual();
	}
	
}
