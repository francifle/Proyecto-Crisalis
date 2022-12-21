package com.crisalis.models;

import com.crisalis.models.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name="Usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
    
    @Column(name="username", nullable=false)
	private String mail;
    @Column(name="pass", nullable=false)
	private String pass;
    @Column(name="nombre", nullable=false)
	private String nombre;
    
    public Usuario () {
	}
    
	public Usuario(String username, String pass, String nombre) {
		super();
		this.mail = username;
		this.pass = pass;
		this.nombre = nombre;
	}
	
	public Usuario(UserDTO userDTO) {
		super();
		this.mail = userDTO.getUsername();
		this.pass = userDTO.getPassword();
		this.nombre = userDTO.getNombre();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		return "Usuario [id=" + id + ", username=" + mail + ", pass=" + pass + ", nombre=" + nombre + "]";
	}

	
    
}