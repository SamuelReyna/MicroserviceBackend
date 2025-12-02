package com.example.demo.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "numeroCuenta", unique = true)
    private String numeroCuenta;
    @Column(name = "tipoCuenta")
    private String tipoCuenta;
    @Column(name = "saldo")
    private Double saldo;
    @Column(name = "fechaApertura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaApertura;
    @Column(name = "activa")
    private boolean activa;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "idcliente", referencedColumnName = "id")
    private Cliente cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Movimiento> movimientos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Transaccion> transaccion = new ArrayList<>();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cuenta(String numeroCuenta, String tipoCuenta, Double saldoInicial, LocalDate fechaApertura, boolean estatus, Cliente cliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldoInicial;
        this.fechaApertura = fechaApertura;
        this.activa = estatus;
        this.cliente = cliente;
    }

    public Cuenta(int id, String numeroCuenta, String tipoCuenta, Double saldoInicial, LocalDate fechaApertura, boolean estatus) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldoInicial;
        this.fechaApertura = fechaApertura;
        this.activa = estatus;
    }

    public Cuenta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

}
