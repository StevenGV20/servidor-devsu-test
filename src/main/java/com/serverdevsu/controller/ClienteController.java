package com.serverdevsu.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverdevsu.entity.Cliente;
import com.serverdevsu.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class ClienteController {

	@Autowired
	private ClienteService serviceCliente;
	
	@GetMapping("/")
	public List<Cliente> listClientes(){
		return serviceCliente.listAllClientes();
	}
	
	@GetMapping("/{id}")
	public Optional<Cliente> findClienteById(@PathParam("id") Integer id){
		return Optional.ofNullable(serviceCliente.findClienteById(id).orElseThrow());
	}
	
	@GetMapping("/{name}")
	public List<Cliente> listClientesByName(@PathParam("name")String name){
		return serviceCliente.listClienteByName(name);
	}
	
	@PostMapping("/")
	public Cliente saveCliente(@RequestBody Cliente cliente) {
		return serviceCliente.saveCliente(cliente);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCliente(@PathParam("id") Integer id) {
		serviceCliente.deleteCliente(id);
	}
}
