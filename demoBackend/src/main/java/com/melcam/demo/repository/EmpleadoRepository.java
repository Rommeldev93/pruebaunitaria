package com.melcam.demo.repository;

import java.util.List;
import com.melcam.demo.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado,String> {
  List<Empleado> findByNombreLike(String nombre);
}
