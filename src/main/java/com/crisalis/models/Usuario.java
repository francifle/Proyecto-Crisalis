package com.crisalis.models;

import java.util.Objects;

import com.crisalis.models.dto.UserDTO;
import com.crisalis.utils.Encrypter;

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
@Entity(name = "Usuario")
@Table(name="Usuario")
public class Usuario extends GenericModel{
	    
    @Column(name="username", nullable=false)
	private String mail;
    @Column(name="pass", nullable=false)
	private String pass;
    @Column(name="nombre", nullable=false)
	private String nombre;

	public Usuario(UserDTO userDTO) {
		super();
		this.mail = userDTO.getUsername();
		this.pass = userDTO.getPassword();
		this.nombre = userDTO.getNombre();
	}
	
	public Usuario() {
		super();
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String username) {
		this.mail = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	

	
	
	@Override 
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(mail, nombre, pass);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (this.getMail().equals(other.getMail()) && this.getPass().equals(other.getPass()))
			return true;
		if (this.getMail().equals(other.getMail()) && Encrypter.match(this.getPass(), other.getPass()))
			return true;
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + getId() + ", username=" + mail + ", pass=" + pass + ", nombre=" + nombre + "]";
	}

}