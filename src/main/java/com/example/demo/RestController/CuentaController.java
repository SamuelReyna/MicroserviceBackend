package com.example.demo.RestController;

import com.example.demo.DAO.CuentaDAOImplementation;
import com.example.demo.DTO.CrearCuenta;
import com.example.demo.DTO.CuentaDTO;
import com.example.demo.DTO.OperacionDTO;
import com.example.demo.JPA.Cuenta;
import com.example.demo.JPA.Movimiento;
import com.example.demo.JPA.Transaccion;
import com.example.demo.Service.CuentaService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaDAOImplementation cuentaDAOImplementation;

    public CuentaController(CuentaService cuentaService, CuentaDAOImplementation cuentaDAOImplementation) {
        this.cuentaDAOImplementation = cuentaDAOImplementation;
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> GetAll(
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "activa", required = false) Boolean activa) {

        List<CuentaDTO> cuentas = cuentaService.GetAll();

        cuentas = cuentas.stream()
                .filter(c -> activa == null || c.isActiva() == activa)
                .filter(c -> tipo == null || tipo.isEmpty() || c.getTipoCuenta().equalsIgnoreCase(tipo))
                .toList();

        return ResponseEntity.ok(cuentas);
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> Create(@RequestBody CrearCuenta cuenta) {
        CuentaDTO newCuenta = cuentaService.Add(cuenta);
        return ResponseEntity.ok(newCuenta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> GetById(@PathVariable("id") int id) {
        CuentaDTO cuenta = cuentaService.GetById(id);
        return ResponseEntity.ok(cuenta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity LogicalDelete(@PathVariable("id") int id) {
        Cuenta cuenta = cuentaDAOImplementation.BajaLogica(id);
        Map<String, String> message = Map.of(
                "message", "Cuenta " + cuenta.getNumeroCuenta() + " desactivada exitosamente"
        );
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/retiros")
    public ResponseEntity<?> Retiro(@PathVariable("id") int id, @RequestBody OperacionDTO operacion) {
        Movimiento movimiento = new Movimiento();
        movimiento.setMonto(operacion.getMonto());
        movimiento.setDescripcion(operacion.getDescripcion());
        movimiento.setTipo("retiro");
        return cuentaService.Retiro(id, movimiento);
    }

    @PostMapping("/{id}/depositos")
    public ResponseEntity Deposito(@PathVariable("id") int id, @RequestBody OperacionDTO operacion) {
        Movimiento movimiento = new Movimiento();
        movimiento.setMonto(operacion.getMonto());
        movimiento.setDescripcion(operacion.getDescripcion());
        movimiento.setTipo("deposito");
        return cuentaService.Deposito(id, movimiento);
    }
}
