package com.serverdevsu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverdevsu.entity.Movimiento;
import com.serverdevsu.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{

	@Autowired
	private MovimientoRepository repository;
	@Override
	public Movimiento saveMovimiento(Movimiento obj) {
		return repository.save(obj);
	}

	@Override
	public List<Movimiento> listAllMovimiento() {
		return repository.findAll();
	}

	@Override
	public void deleteMovimiento(Integer id) {
		repository.deleteById(id);		
	}

}
