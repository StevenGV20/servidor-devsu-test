package com.serverdevsu.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportMovimiento {
	private Date fechaRegistro;
	private String cliente;
	private String numeroCuenta;
	private String tipo;
	private boolean estado;
	private Double movimiento;
	private Double saldoDisponible;
}
