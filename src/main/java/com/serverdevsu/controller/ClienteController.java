package com.serverdevsu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverdevsu.entity.Cliente;
import com.serverdevsu.service.ClienteService;
import com.serverdevsu.utils.Constantes;

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
	public Optional<Cliente> findClienteById(@PathVariable("id") Integer id){
		return Optional.ofNullable(serviceCliente.findClienteById(id).orElseThrow());
	}
	
	@GetMapping("/findByName")
	public ResponseEntity<?> listClientesByName(String name){
		try {
			List<Cliente> lista = serviceCliente.listClienteByName(name);
			if(lista.size()<1) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No se encontro ningun resultado para el nombre "+name);
			}
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		try {
			return ResponseEntity.ok(serviceCliente.saveCliente(cliente));
		}
		catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateCliente(Integer id,@RequestBody Cliente cliente) {
		try {
			if(id==null) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No has especificado a que cliente quieres modificar");
			}
			if(serviceCliente.findClienteByIdentificacion(cliente.getIdentificacion(),id).isPresent()) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Ya existe un cliente con identificacion "+cliente.getIdentificacion());
			}
			return ResponseEntity.ok(serviceCliente.saveCliente(cliente));
		}
		catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCliente(Integer id) {
		
		try{
			if(id == null) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No ha especificado que movimiento eliminar");
			}
			Optional<Cliente> cliente = serviceCliente.findClienteById(id);
			if(cliente.isPresent()) {
				serviceCliente.deleteCliente(id);
				return Constantes.mensaje(HttpStatus.ACCEPTED, "Successful", "Se elimino correctamente el cliente "+cliente.get().getIdentificacion());
			}else {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No esta registrado este cliente");
			}
		}catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
}
