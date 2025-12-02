package com.example.demo.RestController;

import com.example.demo.DAO.MovimientoDAOImplementation;
import com.example.demo.JPA.Movimiento;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    @Autowired
    private MovimientoDAOImplementation movimientoDAOImplementation;

    @GetMapping("/{clienteId}/movimientos")
    public ResponseEntity HistorialMovimientos(
            @RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
            @PathVariable("clienteId") int clienteId) {

        if (fechaInicio != null && fechaFin != null) {
            if (fechaFin.isBefore(fechaInicio)) {
                throw new IllegalArgumentException("La fecha fin debe ser igual o posterior a la fecha inicio");
            }
        }
        List<Movimiento> movimientos = movimientoDAOImplementation.GetByCliente(clienteId);

        if (fechaInicio == null && fechaFin == null) {
            return ResponseEntity.ok(movimientos);
        }

        movimientos = movimientos.stream()
                .filter(movimiento -> {
                    LocalDate fechaMovimiento = movimiento.getFecha().toLocalDate();
                    if (fechaInicio != null && fechaFin != null) {
                        return !fechaMovimiento.isBefore(fechaInicio)
                                && !fechaMovimiento.isAfter(fechaFin);
                    }
                    if (fechaInicio != null) {
                        return !fechaMovimiento.isBefore(fechaInicio);
                    }
                    if (fechaFin != null) {
                        return !fechaMovimiento.isAfter(fechaFin);
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(movimientos);

    }
}
