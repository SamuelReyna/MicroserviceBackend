package com.example.demo.DAO;

import com.example.demo.JPA.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {

}
