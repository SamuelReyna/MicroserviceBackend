package com.example.demo.DTO;

import jakarta.validation.constraints.Pattern;

public class CrearCuenta {

    @Pattern(regexp = "^[A-Z]{3}[0-9]{8}$", message = "El número de cuenta debe tener 3 letras mayúsculas seguidas de 8 dígitos")
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private String nombreTitular;

    public CrearCuenta(String numeroCuenta, String tipoCuenta, Double saldoInicial, String nombreTitular) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.nombreTitular = nombreTitular;
    }

    public CrearCuenta() {

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

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }
}
