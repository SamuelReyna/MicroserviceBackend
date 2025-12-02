package com.example.demo.DAO;

import com.example.demo.JPA.Cuenta;
import java.util.List;

public interface ICuenta {

    Cuenta Add(Cuenta cuenta);

    Cuenta GetById(int id);
    
    List<Cuenta> GetAll();
    
    Cuenta BajaLogica(int id);
    
    Cuenta Update(Cuenta cuenta);
}
