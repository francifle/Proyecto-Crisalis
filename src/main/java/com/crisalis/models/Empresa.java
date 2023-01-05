package com.crisalis.models;

import java.sql.Date;
import java.util.Set;

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
@Entity(name="Empresa")
@Table(name="Empresa")
public class Empresa extends GenericModel{

	@Column(name="fecha", nullable=true)
	private Date fecha;
	@Column(name="razonsocial", nullable=true)
	private String razonSocial;
	@Column(name="cuit", nullable=true)
	private String cuit;
	
	@ManyToMany
    private Set<Persona> integrantes;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public Set<Persona> getIntegrantes() {
		return integrantes;
	}
	public void setIntegrantes(Set<Persona> integrantes) {
		this.integrantes = integrantes;
	}
	
}
	

