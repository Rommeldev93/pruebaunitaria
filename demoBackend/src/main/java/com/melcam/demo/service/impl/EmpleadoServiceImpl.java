package com.melcam.demo.service.impl;

import com.melcam.demo.entity.Empleado;
import com.melcam.demo.repository.EmpleadoRepository;
import com.melcam.demo.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

  @Autowired
  EmpleadoRepository dao;

  @Override
  public Empleado insert(Empleado e) {
    return dao.save(e);
  }

  @Override
  public Empleado modificar(Empleado e) {
    return dao.save(e);
  }

  @Override
  public Empleado buscarPorUsername(String username) {
    return dao.findById(username).orElse(null);
  }

  @Override
  public void eliminar(Empleado e) {
    dao.delete(e);
  }

  @Override
  public List<Empleado> buscarTodos() {
    return dao.findAll();
  }

  @Override
  public List<Empleado> buscarPorNombre(String nombre) {
    return dao.findByNombreLike(nombre);
  }
}
