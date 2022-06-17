package com.melcam.demo.service.impl;

import com.melcam.demo.entity.Cliente;
import com.melcam.demo.repository.ClienteRepository;
import com.melcam.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
  @Autowired
  ClienteRepository dao;

  @Override
  public Cliente insertar(Cliente c) {
    return dao.save(c);
  }

  @Override
  public Cliente modificar(Cliente c) {
    return dao.save(c);
  }

  @Override
  public void eliminar(Cliente c) throws Exception {
    dao.delete(c);
  }

  @Override
  public Cliente buscarPorDni(String dni) throws Exception {
    return dao.findById(dni).orElse(null);
  }

  @Override
  public List<Cliente> buscarTodo() throws Exception {
    return dao.findAll();
  }

  @Override
  public List<Cliente> buscarPorNombre(String nombre) throws Exception {
    return dao.findByNombreLike(nombre);
  }
}
