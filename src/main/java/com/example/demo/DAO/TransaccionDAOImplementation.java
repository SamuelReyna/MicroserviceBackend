package com.example.demo.DAO;

import com.example.demo.JPA.Transaccion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransaccionDAOImplementation implements ITransaccion {

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public Transaccion Add(Transaccion transaccion) {
        try {
            transaccion = entityManager.merge(transaccion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaccion; 
    }

}
