package com.serverdevsu.service;

import java.util.List;

import com.serverdevsu.entity.Movimiento;

public interface MovimientoService {
	
	public abstract Movimiento saveMovimiento(Movimiento obj);
	public abstract List<Movimiento> listAllMovimiento();
	public abstract void deleteMovimiento(Integer id);
}
