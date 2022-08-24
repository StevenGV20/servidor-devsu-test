package com.serverdevsu.service;

import java.util.List;
import java.util.Optional;

import com.serverdevsu.entity.Cliente;

public interface ClienteService {

	public abstract Cliente saveCliente(Cliente obj);
	public abstract Optional<Cliente> findClienteById(Integer id);
	public abstract List<Cliente> listAllClientes();
	public abstract List<Cliente> listClienteByName(String name);
	public abstract void deleteCliente(Integer id);
}
