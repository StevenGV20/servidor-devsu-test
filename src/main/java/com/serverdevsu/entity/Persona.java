package com.serverdevsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="persona")
public abstract class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "persona_id")
	private Integer idpersona;
	private String nombres;
	private String genero;
	private int edad;
	@Column(unique = true)
	private String identificacion;
	private String direccion;
	private String telefono;
	
}
