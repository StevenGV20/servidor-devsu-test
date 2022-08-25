package com.serverdevsu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
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
//@PrimaryKeyJoinColumn(referencedColumnName = "persona_id")
public class Cliente extends Persona implements Serializable{
	/*@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@PrimaryKeyJoinColumn(referencedColumnName = "cliente_id")
	@Column(name = "persona_id")
	private Integer idcliente;*/
	@Column(name = "clave")
	private String clave;
	@Column(name = "estado")
	private boolean estado;
	
}
