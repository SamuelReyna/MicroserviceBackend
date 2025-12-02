package com.example.demo.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuentaid", referencedColumnName = "id")
    private Cuenta cuenta;
    @Column(name = "saldoanterior")
    private Double saldoAnterior;
    @Column(name = "saldonuevo")
    private Double saldoNuevo;
    @Column(name = "operacion")
    private String operacion;
    @Column(name = "mensaje")
    private String mensaje;

    public Transaccion(int id, Cuenta cuenta, Double saldoAnterior, Double saldoNuevo, String operacion, String mensaje) {
        this.id = id;
        this.cuenta = cuenta;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
        this.operacion = operacion;
        this.mensaje = mensaje;
    }

    public Transaccion() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setIdCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(Double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public Double getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(Double saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
