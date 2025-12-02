package com.example.demo.DAO;

import com.example.demo.JPA.Movimiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovimientoDAOImplementation implements IMovimiento {

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public Movimiento Add(Movimiento movimiento) {

        try {
            movimiento = entityManager.merge(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movimiento;

    }

    @Override
    public List<Movimiento> GetByCliente(int idCliente) {
        List<Movimiento> movimientos = new ArrayList<>();
        try {
            TypedQuery<Movimiento> queryMovimiento = entityManager.createQuery(
                    "SELECT m FROM Movimiento m "
                    + "JOIN FETCH m.cuenta c "
                    + "WHERE c.cliente.id = :idCliente",
                    Movimiento.class
            );
            queryMovimiento.setParameter("idCliente", idCliente);

            movimientos = queryMovimiento.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movimientos;

    }

}
