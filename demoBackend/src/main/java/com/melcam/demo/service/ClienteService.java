package com.melcam.demo.service;

import com.melcam.demo.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteService {
  Cliente insertar(Cliente c);

  Cliente modificar(Cliente c);

  void eliminar(Cliente c) throws Exception;

  Cliente buscarPorDni(String dni) throws Exception;

  List<Cliente> buscarTodo() throws Exception;

  List<Cliente> buscarPorNombre(String nombre) throws Exception;
}
