package com.serverdevsu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.serverdevsu.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Query(value = "select c from Cliente c where c.identificacion = :ide and c.idpersona != :id")
	public abstract Optional<Cliente> findCuentByIdentificacion(@Param("ide")String ide,@Param("id")Integer id ); 
}
