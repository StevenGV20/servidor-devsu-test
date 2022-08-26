package com.serverdevsu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.serverdevsu.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
	@Query(value = "select m from Movimiento m where m.fechaRegistro between :fecha_ini and :fecha_fin and m.cuenta.cliente.idpersona = :cliente")
	public abstract List<Movimiento> listMovimientosByFechaAndCliente(
			@Param("fecha_ini")Date fecha_ini,@Param("fecha_fin")Date fecha_fin,@Param("cliente")Integer cliente); 
}
