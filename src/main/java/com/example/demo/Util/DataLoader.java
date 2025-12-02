package com.example.demo.Util;

import com.example.demo.DAO.IClienteRepository;
import com.example.demo.DAO.ICuentaRepository;
import com.example.demo.JPA.Cliente;
import com.example.demo.JPA.Cuenta;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final IClienteRepository iClienteRepository;
    private final ICuentaRepository iCuentaRepository;

    public DataLoader(IClienteRepository iClienteRepository, ICuentaRepository iCuentaRepository) {
        this.iClienteRepository = iClienteRepository;
        this.iCuentaRepository = iCuentaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (iClienteRepository.count() == 0) {
            iClienteRepository.save(new Cliente("Samuel", "Reyna", "Gonzalez"));
            iClienteRepository.save(new Cliente("Daniel Alejandro", "Garcia", "Torres"));
        }
        if (iClienteRepository.count() == 2 && iCuentaRepository.count() == 0) {
            List<Cliente> clientes = iClienteRepository.findAll();
            Random random = new Random();
            int min = 10000000;
            int max = 99999999;
            for (Cliente cliente : clientes) {
                int numeroAleatorio = random.nextInt(max - min + 1) + min;
                Cliente clienteRef = iClienteRepository.getReferenceById(Long.valueOf(cliente.getId()));

                iCuentaRepository.save(new Cuenta(
                        "BAN" + numeroAleatorio,
                        "AHORROS",
                        1000.50,
                        LocalDate.now(),
                        true,
                        clienteRef));
            }

        }
    }

}
