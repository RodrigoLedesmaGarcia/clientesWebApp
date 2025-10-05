package com.spring.www.ms_clientes.services;

import com.spring.www.ms_clientes.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Page<Cliente> paginar (Pageable pageable);

    Optional<Cliente> buscarClientePorId (Long id);

    Optional<Cliente> buscarPorCorreo (String correo);

    List<Cliente> buscarPorNombre(String nombre, String app, String apm);

    Cliente crear (Cliente cliente);

    Cliente editar (Cliente cliente);

    void eliminarCliente (Long id);


}