package com.serverdevsu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cuenta")
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cuenta_id")
	private Integer idcuenta;
	
	@Column(name = "numero_cuenta", unique = true)
	private String numeroCuenta;
	
	@Column(name = "tipo_cuenta")
	private String tipoCuenta;
	
	@Column(name = "saldo_inicial")
	private Double saldoInicial;
	
	@Column(name = "saldo_disponible")
	private Double saldoDisponible;
	
	private Boolean estado;

	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
}
