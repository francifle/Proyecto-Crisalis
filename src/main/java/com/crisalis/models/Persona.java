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
@Entity(name="Persona")
@Table(name="Persona")
public class Persona extends GenericModel {

	@Column(name="nombre", nullable=true)
	private String nombre;
	@Column(name="apellido", nullable=true)
	private String apellido;
	@Column(name="dni", nullable=true)
	private String dni;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombreCompleto() {
		return getNombre() + " " + getApellido();
	}
	
	public String getNombreFisico() {
		return getNombreCompleto() + " - DNI: " + getDniFormateado();
	}
	
	public String getDniFormateado() {
		return getDni().substring(0,2) + "." + getDni().substring(2,5) + "." + getDni().substring(5,8);
	}
	
}
