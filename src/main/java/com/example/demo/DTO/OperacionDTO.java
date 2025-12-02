package com.example.demo.DTO;

public class OperacionDTO {

    private Double monto;
    private String descripcion;

    public OperacionDTO(Double monto, String descripcion) {
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public OperacionDTO() {

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
}
