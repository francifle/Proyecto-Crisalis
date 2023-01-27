package com.crisalis.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.crisalis.constants.UtilsConstants;
import com.crisalis.repositories.PersonaRepository;
import com.crisalis.services.PersonaService;
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
@Entity(name = "PedidoVenta")
@Table(name = "PedidoVenta")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PedidoVenta extends GenericModel {

	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	@Column(name = "cliente", nullable = false)
	private String cliente;
	@Column(name = "importeTotal", nullable = false)
	private Double importeTotal;
	@Column(name = "totalDescuentos", nullable = false)
	private Double totalDescuentos;
	@Column(name = "estado", nullable = false)
	private Boolean estado;

	@ManyToMany
	private Set<OrdenVenta> ordenes;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Double getTotalDescuentos() {
		return totalDescuentos;
	}

	public void setTotalDescuentos(Double totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Set<OrdenVenta> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(Set<OrdenVenta> ordenes) {
		this.ordenes = ordenes;
	}

	public Double getImporteFinal() {
		Double value = getImporteTotal() - getTotalDescuentos();
		return Math.round(value * 100.0) / 100.0;
	}

	public Set<OrdenVenta> getOrdenesByTipo(Integer tipoOrden) {
		Set<OrdenVenta> value = new HashSet<>();
		for (OrdenVenta o : getOrdenes()) {
			if (o.getTipo().equals(tipoOrden))
				value.add(o);
		}
		return value;
	}

	public Set<OrdenVenta> getOrdenesProducto() {
		return getOrdenesByTipo(UtilsConstants.TIPO_PRODUCTO);
	}

	public Set<OrdenVenta> getOrdenesServicio() {
		return getOrdenesByTipo(UtilsConstants.TIPO_SERVICIO);
	}

	/*public Long getPersonaId() {
		String personaNombre = getCliente().split("//")[0].trim();
		PersonaRepository personaRepository = null;
		PersonaService personaService = new PersonaService(personaRepository);
		for (Persona p : personaService.getAllPersonas()) {
			if (p.getNombreCompleto().equals(personaNombre)) {
				return p.getId();
			}
		}
		return 0L;
	}*/

}
