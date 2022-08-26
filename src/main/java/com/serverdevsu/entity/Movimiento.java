package com.serverdevsu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movimientos")
public class Movimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movimiento_id")
	private Integer idmovimiento;
	
	@JsonFormat(pattern = "dd/mm/yyyy")
	@Timestamp(value = "dd/mm/yyyy")
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	@Column(name = "fecha_registro")
	private Date fechaRegistro = new Date();
	
	@Column(name = "tipo_movimiento")
	private String tipoMovimiento;
	
	private String valor;
	private Double saldo;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuenta_id")
	private Cuenta cuenta;
	
}
