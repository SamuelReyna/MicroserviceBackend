package com.example.demo.DAO;

import com.example.demo.JPA.Movimiento;
import java.util.List;

public interface IMovimiento {

    Movimiento Add(Movimiento movimiento);

    List<Movimiento> GetByCliente(int idCliente);   

}
