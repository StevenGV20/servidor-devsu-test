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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverdevsu.entity.Cuenta;
import com.serverdevsu.service.ClienteService;
import com.serverdevsu.service.CuentaService;
import com.serverdevsu.utils.Constantes;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*", 
	methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.PATCH,RequestMethod.DELETE})
public class CuentaController {

	@Autowired
	private CuentaService serviceCuenta;
	
	@Autowired
	private ClienteService serviceCliente;
	
	@GetMapping("/")
	public ResponseEntity<?> listCuentas(){
		try {
			List<Cuenta> list = serviceCuenta.listAll();
			if(list.size() < 1) {
				return Constantes.mensaje(HttpStatus.NOT_FOUND, "Error", "No hay ninguna cuenta registrada");
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findCuentaById(@PathVariable("id")Integer id){
		try {
			Optional<Cuenta> cuenta = serviceCuenta.findCuentaById(id);
			if(!cuenta.isPresent()) {
				return Constantes.mensaje(HttpStatus.NOT_FOUND, "Error", "No hay ninguna cuenta registrada con el id "+id);
			}
			return ResponseEntity.ok(cuenta);
		} catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveCuenta(@RequestBody Cuenta obj) {
		try {
			if(!serviceCliente.findClienteById(obj.getCliente().getIdpersona()).isPresent()) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - No existe", "No se encontro ningun cliente con el id "+obj.getCliente().getIdpersona());
			}
			if(obj.getIdcuenta()==null) obj.setSaldoDisponible(obj.getSaldoInicial());
			return ResponseEntity.ok(serviceCuenta.saveCuenta(obj));
		}
		catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateCuenta(Integer id,@RequestBody Cuenta obj) {
		try {
			if(id==null) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No has especificado a que cuenta desea modificar");
			}
			return ResponseEntity.ok(serviceCuenta.saveCuenta(obj));
		}
		catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCuenta(@PathVariable("id")Integer id) {
		try{
			if(id == null) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No ha especificado que movimiento eliminar");
			}
			Optional<Cuenta> cuenta = serviceCuenta.findCuentaById(id);
			if(cuenta.isPresent()) {
				serviceCuenta.deleteCuenta(id);		
				return Constantes.mensaje(HttpStatus.ACCEPTED, "Successful", "Se elimino correctamente la cuenta "+cuenta.get().getNumeroCuenta());
			}else {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No esta registrado esta cuenta");
			}
		}catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	
}
