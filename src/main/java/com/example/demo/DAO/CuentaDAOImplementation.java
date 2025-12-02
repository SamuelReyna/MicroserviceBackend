package com.example.demo.DAO;

import com.example.demo.JPA.Cuenta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CuentaDAOImplementation implements ICuenta {

    @Autowired
    EntityManager entityManager;

    @Override
    public Cuenta Add(Cuenta cuenta) {
        entityManager.persist(cuenta);
        return cuenta;
    }

    @Override
    public Cuenta GetById(int id) {
        Cuenta cuenta = entityManager.find(Cuenta.class, id);
        return cuenta;
    }

    @Transactional
    @Override
    public Cuenta BajaLogica(int id) {
        Cuenta cuenta = entityManager.find(Cuenta.class, id);
        cuenta.setActiva(!cuenta.isActiva());
        Cuenta merge = entityManager.merge(cuenta);
        return merge;
    }

    @Override
    public List<Cuenta> GetAll() {
        List<Cuenta> cuentas = new ArrayList<>();
        try {
            TypedQuery<Cuenta> query
                    = entityManager.createQuery("FROM Cuenta", Cuenta.class);
            cuentas = query.getResultList();
        } catch (Exception e) {
            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return cuentas;
    }

    @Override
    public Cuenta Update(Cuenta cuenta) {
        Cuenta cuentaModified = new Cuenta();
        try {
            cuentaModified = entityManager.merge(cuenta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cuentaModified;
    }

}
