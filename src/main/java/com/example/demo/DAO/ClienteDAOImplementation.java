package com.example.demo.DAO;

import com.example.demo.JPA.Cliente;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDAOImplementation implements ICliente {

    @Autowired
    EntityManager entityManager;

    @Override
    public Cliente Add(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

}
