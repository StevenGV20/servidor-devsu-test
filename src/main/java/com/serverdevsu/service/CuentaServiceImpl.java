package com.serverdevsu.service;

import java.util.List;

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

}
