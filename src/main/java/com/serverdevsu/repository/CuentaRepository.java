package com.serverdevsu.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.serverdevsu.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
	
	@Transactional
	@Modifying
	@Query("update Cuenta c set c.saldoDisponible = :saldo where c.idcuenta = :id")
	public abstract void updateSaldo(@Param("saldo")Double saldo,@Param("id")int id);
	
	@Query(value = "select c from Cuenta c where c.numeroCuenta = :nro")
	public abstract Optional<Cuenta> findCuentByNroCuenta(@Param("nro")String nro); 
}
