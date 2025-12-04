package com.example.demo.Service;

import com.example.demo.DAO.ClienteDAOImplementation;
import com.example.demo.DAO.CuentaDAOImplementation;
import com.example.demo.DAO.MovimientoDAOImplementation;
import com.example.demo.DAO.TransaccionDAOImplementation;
import com.example.demo.DTO.CrearCuenta;
import com.example.demo.DTO.CuentaDTO;
import com.example.demo.JPA.Cliente;
import com.example.demo.JPA.Cuenta;
import com.example.demo.JPA.Movimiento;
import com.example.demo.DTO.Response;
import com.example.demo.JPA.Transaccion;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private ClienteDAOImplementation clienteDAOImplementation;
    @Autowired
    private CuentaDAOImplementation cuentaDAOImplementation;
    @Autowired
    private MovimientoDAOImplementation movimientoDAOImplementation;
    @Autowired
    private TransaccionDAOImplementation transaccionDAOImplentation;

    @Transactional
    public CuentaDTO Add(CrearCuenta cuenta) {

        String nombreTitular = cuenta.getNombreTitular();
        String[] partes = nombreTitular.trim().split("\\s+");

        String nombres = partes.length > 0 ? partes[0] : "";
        String apellidoPaterno = partes.length > 1 ? partes[1] : "";
        String apellidoMaterno = partes.length > 2 ? partes[2] : "";
        // Si hay más de 3 palabras, concatenar los nombres adicionales
        if (partes.length > 3) {
            apellidoMaterno = partes[partes.length - 1];
            apellidoPaterno = partes[partes.length - 2];
            nombres = String.join(" ", Arrays.copyOfRange(partes, 0, partes.length - 2));
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(nombres);
        cliente.setApellidoPaterno(apellidoPaterno);
        cliente.setApellidoMaterno(apellidoMaterno);
        clienteDAOImplementation.Add(cliente);

        Cuenta cuentaDB = new Cuenta();
        cuentaDB.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDB.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDB.setSaldo(cuenta.getSaldoInicial());
        cuentaDB.setFechaApertura(LocalDate.now());
        cuentaDB.setActiva(true);
        cuentaDB.setCliente(cliente);
        if (ValidarCuenta(cuentaDB)) {
            cuentaDAOImplementation.Add(cuentaDB);
        }
        Movimiento movimiento = new Movimiento();
        movimiento.setMonto(cuentaDB.getSaldo());
        movimiento.setDescripcion("Deposito inicial");
        movimiento.setCuenta(cuentaDB);
        movimiento.setTipo("deposito");
        movimiento.setFecha(LocalDateTime.now());
        movimientoDAOImplementation.Add(movimiento);
        Transaccion transaccion = new Transaccion();
        transaccion.setIdCuenta(cuentaDB);
        transaccion.setMensaje("Depósito realizado con éxito");
        transaccion.setOperacion("DEPOSITO");
        transaccion.setSaldoNuevo(cuentaDB.getSaldo());
        transaccion.setSaldoAnterior(cuentaDB.getSaldo());
        transaccionDAOImplentation.Add(transaccion);

        return convertirACuentaDTO(cuentaDB);
    }

    public CuentaDTO GetById(int id) {
        return convertirACuentaDTO(cuentaDAOImplementation.GetById(id));
    }

    public CuentaDTO convertirACuentaDTO(Cuenta cuenta) {
        String nombreTitular = cuenta.getCliente().getNombre() + " "
                + cuenta.getCliente().getApellidoPaterno() + " "
                + cuenta.getCliente().getApellidoMaterno();

        return new CuentaDTO(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldo(),
                cuenta.getFechaApertura().toString(),
                cuenta.isActiva(),
                nombreTitular.trim()
        );
    }

    private boolean ValidarCuenta(Cuenta cuenta) {
        String regex = "[A-Z]{3}[0-9]{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cuenta.getNumeroCuenta());

        return matcher.matches();
    }

    public List<CuentaDTO> GetAll() {
        List<CuentaDTO> cuentas = new ArrayList<>();
        for (Cuenta cuenta : cuentaDAOImplementation.GetAll()) {
            cuentas.add(convertirACuentaDTO(cuenta));
        }
        return cuentas;
    }

    public ResponseEntity<?> Retiro(int idCuenta, Movimiento movimiento) {
        try {
            Cuenta cuenta = cuentaDAOImplementation.GetById(idCuenta);
            if (cuenta.isActiva()) {
                if (movimiento.getMonto() > 0) {
                    Transaccion transaccion = new Transaccion();
                    transaccion.setSaldoAnterior(cuenta.getSaldo());

                    if (cuenta.getSaldo() >= movimiento.getMonto()) {
                        cuenta.setSaldo(cuenta.getSaldo() - movimiento.getMonto());
                        movimiento.setFecha(LocalDateTime.now());
                        movimiento.setCuenta(cuenta);
                        Movimiento movimientoBD = movimientoDAOImplementation.Add(movimiento);

                        Cuenta nuevoSaldo = cuentaDAOImplementation.GetById(idCuenta);
                        transaccion.setIdCuenta(cuenta);
                        transaccion.setMensaje("Retiro realizado con éxito");
                        transaccion.setOperacion("RETIRO");
                        transaccion.setSaldoNuevo(nuevoSaldo.getSaldo());
                        transaccion = transaccionDAOImplentation.Add(transaccion);

                        return ResponseEntity.ok(transaccion);
                    } else {
                        Response response = new Response();
                        response.mensaje = "Saldo insuficiente. Intenta retirar: " + movimiento.getMonto()
                                + ". Saldo disponible: " + cuenta.getSaldo();
                        response.codigo = 400;
                        response.fecha = LocalDateTime.now();
                        response.ruta = "api/cuentas/" + cuenta.getId() + "/retiros";
                        response.error = "Solicitud incorrecta";

                        return ResponseEntity.status(400).body(response);
                    }
                } else {
                    Map<String, String> message = Map.of("mensaje", "Monto no válido, debe ser mayor de 0");
                    return ResponseEntity.status(400).body(message);
                }
            } else {
                Map<String, String> message = Map.of("mensaje", "Cuenta inactiva");
                return ResponseEntity.status(400).body(message);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> Deposito(int idCuenta, Movimiento movimiento) {
        Cuenta cuenta = cuentaDAOImplementation.GetById(idCuenta);
        if (cuenta.isActiva()) {
            if (movimiento.getMonto() > 0) {
                Transaccion transaccion = new Transaccion();
                transaccion.setSaldoAnterior(cuenta.getSaldo());
                cuenta.setSaldo(cuenta.getSaldo() + movimiento.getMonto());
                movimiento.setFecha(LocalDateTime.now());
                movimiento.setCuenta(cuenta);
                Movimiento movimientoBD = movimientoDAOImplementation.Add(movimiento);
                Cuenta nuevoSaldo = cuentaDAOImplementation.GetById(idCuenta);
                transaccion.setIdCuenta(cuenta);
                transaccion.setMensaje("Deposito realizado con éxito");
                transaccion.setOperacion("DEPOSITO");
                transaccion.setSaldoNuevo(nuevoSaldo.getSaldo());
                transaccion = transaccionDAOImplentation.Add(transaccion);
                return ResponseEntity.ok(transaccion);
            } else {
                Map<String, String> message = Map.of("mensaje", "Monto no válido, debe ser mayor de 0");
                return ResponseEntity.status(400).body(message);
            }
        } else {
            Map<String, String> message = Map.of("mensaje", "Cuenta inactiva");
            return ResponseEntity.status(400).body(message);
        }

    }

}
