package com.serverdevsu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverdevsu.entity.Cuenta;
import com.serverdevsu.repository.CuentaRepository;

@Service
public class CuentaServiceImpl implements CuentaService{

	@Autowired
	private CuentaRepository repository;
	
	@Override
	public Cuenta saveCuenta(Cuenta obj) {
		return repository.save(obj);
	}

	@Override
	public List<Cuenta> listAll() {
		return repository.findAll();
	}

	@Override
	public void deleteCuenta(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Cuenta> findCuentaById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Cuenta> findCuentaByNroCuenta(String nro) {
		return repository.findCuentByNroCuenta(nro);
	}

	@Override
	public void updateSaldo(Cuenta obj) {
		repository.updateSaldo(obj.getSaldoDisponible(), obj.getIdcuenta());
	}

}
