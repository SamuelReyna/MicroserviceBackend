package com.example.demo.DAO;

import com.example.demo.JPA.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

}
