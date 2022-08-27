package com.serverdevsu.controller;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverdevsu.entity.Movimiento;
import com.serverdevsu.service.CuentaService;
import com.serverdevsu.service.MovimientoService;
import com.serverdevsu.utils.Constantes;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*", 
	methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.PATCH,RequestMethod.DELETE})
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
			if(!fecha_ini.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - fechaIni", "La fecha debe tener formato dd/MM/yyyy");
			}
			if(!fecha_fin.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - fechaFin", "La fecha debe tener formato dd/MM/yyyy");
			}
			SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaInicioDate = date.parse(fecha_ini);
			Date fechaFinalDate = date.parse(fecha_fin);
			if(fechaFinalDate.before(fechaInicioDate)) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - fechaFin", "La fecha final no puede ser antes que la fecha inicial.");
			}
			
			if(type==null) type = "JSON";
			if(type.toUpperCase().equals("PDF")) {
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(serviceMovimiento.listMovimientosByFechaAndCliente(fecha_ini, fecha_fin, cliente));
				JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reportMovimientos.jrxml"));
				HashMap<String, Object> map = new HashMap<>();
				JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
				//JasperExportManager.exportReportToPdfFile(report,"reporteMovimientos.pdf");
				byte[] data = JasperExportManager.exportReportToPdf(report);
				HttpHeaders headers = new HttpHeaders();
				headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=reporteMovimientos.pdf");
				
				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
			}else {
				return ResponseEntity.ok(serviceMovimiento.listMovimientosByFechaAndCliente(fecha_ini, fecha_fin, cliente));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error", "Surgio un error: "+e.getMessage());
		}
		
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveMovimiento(@RequestBody Movimiento obj) {
		try {
			if(obj.getFechaRegistro()==null) obj.setFechaRegistro(new Date());
			if(obj.getCuenta().getSaldoDisponible()==null) {
				obj.setCuenta(serviceCuenta.findCuentaById(obj.getCuenta().getIdcuenta()).get());
			}
			if(Constantes.MOVIMIENTO_TIPO_RETIRO.equals(obj.getTipoMovimiento().toUpperCase())) {
				if(obj.getSaldo() > 0) {
					return Constantes.mensaje(HttpStatus.BAD_REQUEST, 
							"Error - saldo", "Debes retirar minimo un 1 dolar (ingresar un numero negativo).");
				}
				if(Constantes.MOVIMIENTO_VALOR_DEBITO.equals(obj.getValor().toUpperCase())) {
					if(obj.getCuenta().getSaldoDisponible() < Math.abs(obj.getSaldo()) || obj.getCuenta().getSaldoDisponible() == 0) {
						return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - saldo", "Saldo no Disponible");
					}				
				}
			}
			else if(Constantes.MOVIMIENTO_TIPO_DEPOSITO.equals(obj.getTipoMovimiento().toUpperCase())) {
				if(obj.getSaldo() < 1) {
					return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - saldo", 
							"Debes depositar minimo un 1 dolar (ingresar un numero mayor o igual a 1).");
				}
			}else {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - tipoMovimiento", "El tipo de Movimiento es: "+Constantes.MOVIMIENTO_TIPO_DEPOSITO+" o "+Constantes.MOVIMIENTO_TIPO_RETIRO);
			}
			if(!serviceCuenta.findCuentaById(obj.getCuenta().getIdcuenta()).isPresent()) {
				return Constantes.mensaje(HttpStatus.BAD_REQUEST, "Error - No existe", "No esta registrada la cuenta: "+obj.getCuenta().getIdcuenta());
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
