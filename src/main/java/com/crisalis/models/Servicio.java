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
	
	@Column(name="meses", nullable=true)
	private Integer meses;
	
	@Column(name="cargo_mensual", nullable=true)
	private Double cargoMensual;

	@ManyToOne(fetch = FetchType.LAZY)
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
	
	public Double getCargoMensual() {
		return cargoMensual;
	}

	public void setCargoMensual(Double cargoMensual) {
		this.cargoMensual = cargoMensual;
	}

	public Double getCostoMensual() {
		return getPedido().getPrecioTotal() * getCargoMensual() * getMeses();
	}
	
	public Double getPrecioTotalServicio() {
		return getPedido().getPrecioTotal() + getCostoMensual();
	}
	
}
