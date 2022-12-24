package com.crisalis.models;

import com.crisalis.models.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "Usuario")
@Table(name="Usuario")
public class Usuario extends GenericModel{
	    
    @Column(name="username", nullable=false)
	private String mail;
    @Column(name="pass", nullable=false)
	private String pass;
    @Column(name="nombre", nullable=false)
	private String nombre;
    
    public Usuario () {
	}
    
	
	public Usuario(UserDTO userDTO) {
		super();
		this.mail = userDTO.getUsername();
		this.pass = userDTO.getPassword();
		this.nombre = userDTO.getNombre();
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
	public String toString() {
		return "Usuario [id=" + getId() + ", username=" + mail + ", pass=" + pass + ", nombre=" + nombre + "]";
	}

	
    
}