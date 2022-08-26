package com.serverdevsu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverdevsu.entity.Cliente;
import com.serverdevsu.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public Cliente saveCliente(Cliente obj) {
		return repository.save(obj);
	}

	@Override
	public List<Cliente> listAllClientes() {
		return repository.findAll();
	}

	@Override
	public List<Cliente> listClienteByName(String name) {
		return null;
	}

	@Override
	public void deleteCliente(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Cliente> findClienteById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Cliente> findClienteByIdentificacion(String ide, Integer id) {
		return repository.findCuentByIdentificacion(ide, id);
	}

}
