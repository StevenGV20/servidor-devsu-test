package com.serverdevsu.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverdevsu.entity.Cuenta;
import com.serverdevsu.entity.Movimiento;
import com.serverdevsu.entity.ReportMovimiento;
import com.serverdevsu.repository.CuentaRepository;
import com.serverdevsu.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{

	@Autowired
	private MovimientoRepository repository;
	
	@Autowired
	private CuentaRepository cuentaRepository;
	
	@Override
	public Movimiento saveMovimiento(Movimiento obj) {
		obj.getCuenta().setSaldoDisponible(obj.getCuenta().getSaldoDisponible() + obj.getSaldo());
		Cuenta cuenta = obj.getCuenta();
		Movimiento res = repository.save(obj);
		if(res != null) {
			cuentaRepository.updateSaldo(cuenta.getSaldoDisponible(), cuenta.getIdcuenta());
		}
		return res;
	}

	@Override
	public List<Movimiento> listAllMovimiento() {
		return repository.findAll();
	}

	@Override
	public void deleteMovimiento(Integer id) {
		repository.deleteById(id);		
	}

	@Override
	public Optional<Movimiento> findMovimientoById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public List<ReportMovimiento> listMovimientosByFechaAndCliente(String fecha_ini, String fecha_fin, Integer cliente) {
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	    Date dateIni = null;
	    Date dateFin = null;
		try {
			dateIni = formater.parse(fecha_ini);
			dateFin = formater.parse(fecha_fin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Movimiento> lista = repository.listMovimientosByFechaAndCliente(dateIni, dateFin, cliente);
		List<ReportMovimiento> listReport = new ArrayList<ReportMovimiento>();
		ReportMovimiento report = null;
		for (Movimiento obj : lista) {
			report = new ReportMovimiento();
			report.setCliente(obj.getCuenta().getCliente().getNombres());
			report.setFechaRegistro(obj.getFechaRegistro());
			report.setMovimiento(obj.getSaldo());
			report.setTipo(obj.getCuenta().getTipoCuenta());
			report.setNumeroCuenta(obj.getCuenta().getNumeroCuenta());
			report.setSaldoDisponible(obj.getCuenta().getSaldoDisponible());
			report.setEstado(obj.getCuenta().getEstado());
			listReport.add(report);
		}
		return listReport;
	}

}
