package com.serverdevsu.service;

import java.util.List;
import java.util.Optional;

import com.serverdevsu.entity.Movimiento;
import com.serverdevsu.entity.ReportMovimiento;

public interface MovimientoService {
	
	public abstract Movimiento saveMovimiento(Movimiento obj);
	public abstract Optional<Movimiento> findMovimientoById(Integer id);
	public abstract List<Movimiento> listAllMovimiento();
	public abstract List<ReportMovimiento> listMovimientosByFechaAndCliente(String fecha_ini, String fecha_fin, Integer cliente);
	public abstract void deleteMovimiento(Integer id);
}
