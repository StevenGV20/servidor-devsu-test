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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverdevsu.entity.Movimiento;
import com.serverdevsu.service.CuentaService;
import com.serverdevsu.service.MovimientoService;
import com.serverdevsu.utils.Constantes;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class MovimientoController {

	@Autowired
	private MovimientoService serviceMovimiento;
	@Autowired
	private CuentaService serviceCuenta;
	
	@GetMapping("/")
	public ResponseEntity<?> listMovimientos(){
		try {
			List<Movimiento> list = serviceMovimiento.listAllMovimiento();
			if(list.size() < 1) {
				return Constantes.mensaje(HttpStatus.NOT_FOUND, "Error", "No hay ningun movimiento registrado");
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Movimiento> listMovimientoById(@PathVariable("id")Integer id){
		return serviceMovimiento.findMovimientoById(id);
	}
	
	@GetMapping("/reportes")
	public ResponseEntity<?> listMovimientosByFechaAndCliente(String fecha_ini,String fecha_fin, Integer cliente,String type){
		try {
			if(type==null) type = "JSON";
			if(type.toUpperCase().equals("PDF")) {
				return ResponseEntity.ok("Esto generar un pdf");
			}else {
				return ResponseEntity.ok(serviceMovimiento.listMovimientosByFechaAndCliente(fecha_ini, fecha_fin, cliente));
			}
		} catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
		
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveMovimiento(@RequestBody Movimiento obj) {
		try {
			if(obj.getCuenta().getSaldoDisponible()==null) {
				obj.setCuenta(serviceCuenta.findCuentaById(obj.getCuenta().getIdcuenta()).get());
			}
			if(Constantes.MOVIMIENTO_TIPO_RETIRO.equals(obj.getTipoMovimiento().toUpperCase())) {
				if(obj.getSaldo() > 0) {
					return Constantes.mensaje(HttpStatus.BAD_REQUEST, 
							"Error", "Debes retirar minimo un 1 dolar (ingresar un numero negativo).");
				}
				if(Constantes.MOVIMIENTO_VALOR_DEBITO.equals(obj.getValor().toUpperCase())) {
					if(obj.getCuenta().getSaldoDisponible() < Math.abs(obj.getSaldo()) || obj.getCuenta().getSaldoDisponible() == 0) {
						return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Saldo no Disponible");
					}				
				}
			}
			else if(Constantes.MOVIMIENTO_TIPO_DEPOSITO.equals(obj.getTipoMovimiento().toUpperCase())) {
				if(obj.getSaldo() < 1) {
					return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", 
							"Debes depositar minimo un 1 dolar (ingresar un numero mayor o igual a 1).");
				}
			}else {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "El tipo de Movimiento es: "+Constantes.MOVIMIENTO_TIPO_DEPOSITO+" o "+Constantes.MOVIMIENTO_TIPO_RETIRO);
			}
			if(!serviceCuenta.findCuentaById(obj.getCuenta().getIdcuenta()).isPresent()) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No esta registrada la cuenta: "+obj.getCuenta().getIdcuenta());
			}
			Movimiento mov = serviceMovimiento.saveMovimiento(obj);
			return ResponseEntity.ok(mov);
		} catch (Exception e) {
			e.printStackTrace();
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteMovimiento(Integer id) {
		try{
			if(id == null) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No ha especificado que movimiento eliminar");
			}
			if(serviceMovimiento.findMovimientoById(id).isPresent()) {
				serviceMovimiento.deleteMovimiento(id);			
				return Constantes.mensaje(HttpStatus.ACCEPTED, "Successful", "Se elimino correctamente el movimiento "+id);
			}else {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "No esta registrado este movimiento");
			}
		}catch (Exception e) {
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
	}
}
