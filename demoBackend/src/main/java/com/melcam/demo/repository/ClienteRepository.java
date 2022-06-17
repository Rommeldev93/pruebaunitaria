package com.melcam.demo.repository;

import com.melcam.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
  List<Cliente> findByNombreLike(String nombre);
}
