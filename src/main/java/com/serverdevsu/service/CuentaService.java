package com.serverdevsu.service;

import java.util.List;
import java.util.Optional;

import com.serverdevsu.entity.Cuenta;

public interface CuentaService{
	
	public abstract Cuenta saveCuenta(Cuenta obj);
	public abstract Optional<Cuenta> findCuentaById(Integer id);
	public abstract Optional<Cuenta> findCuentaByNroCuenta(String nro);
	public abstract List<Cuenta> listAll();
	public abstract void deleteCuenta(Integer id);
	public abstract void updateSaldo(Cuenta obj);
}
