package com.serverdevsu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente extends Persona implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Integer idcliente;
	@Column(name = "clave")
	private String clave;
	@Column(name = "estado")
	private boolean estado;
	
}
