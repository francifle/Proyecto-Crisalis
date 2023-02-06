package com.crisalis.models;

import java.util.Objects;

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
	@Column(name = "estado", nullable = true)
	private Boolean estado;
	
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
	
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(apellido, dni, nombre);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(dni, other.dni)
				&& Objects.equals(nombre, other.nombre);
	}
	
}
