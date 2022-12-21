package com.crisalis.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


 public class UserDTO {

	public String getUsername() {
		return Username;
	}
	public UserDTO(String username, String password, String nombre) {
		super();
		Username = username;
		Password = password;
		Nombre = nombre;
	}
	public void setUsername(String username) {
		Username = username;
	}
	@Override
	public String toString() {
		return "UserDTO [Username=" + Username + ", Password=" + Password + ", Nombre=" + Nombre + "]";
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	@JsonProperty("username")
	private String Username;
	@JsonProperty("pass")
	private String Password;
	@JsonProperty("nombre")
	private String Nombre; 
	
}
