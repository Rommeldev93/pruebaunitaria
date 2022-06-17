package com.melcam.demo.service;

import com.melcam.demo.entity.Empleado;

import java.util.List;

public interface EmpleadoService {
  Empleado insert(Empleado e)throws Exception;

  Empleado modificar(Empleado e)throws Exception;

  Empleado buscarPorUsername(String username)throws Exception;

  void eliminar(Empleado e)throws Exception;

  List<Empleado> buscarTodos()throws Exception;

  List<Empleado> buscarPorNombre(String nombre)throws Exception;
}
