package com.serverdevsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverdevsu.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
