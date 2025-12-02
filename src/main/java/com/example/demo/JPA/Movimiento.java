package com.example.demo.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Movimiento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    private LocalDateTime fecha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuentaid", referencedColumnName = "id")
    private Cuenta cuenta;

    public Movimiento() {

    }

    public Movimiento(int id, String tipo, Double monto, String descripcion, LocalDateTime fecha, Cuenta cuenta) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cuenta = cuenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

}
