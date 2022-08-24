package com.serverdevsu.service;

import java.util.List;

import com.serverdevsu.entity.Cuenta;

public interface CuentaService{
	
	public abstract Cuenta saveCuenta(Cuenta obj);
	public abstract List<Cuenta> listAll();
	public abstract void deleteCuenta(Integer id);
}
