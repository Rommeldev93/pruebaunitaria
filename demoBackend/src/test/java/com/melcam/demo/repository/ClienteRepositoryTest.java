package com.melcam.demo.repository;

import com.melcam.demo.entity.Cliente;
import com.melcam.demo.entity.Empleado;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("bbdd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ClienteRepositoryTest {
  @Autowired
  ClienteRepository dao;

  @Test
  void test() {
    //GIVEN
    String dnitest = "00001T";
    Optional<Cliente> op= dao.findById(dnitest);
    assertFalse(op.isPresent());
    //WHEN
    Cliente c=new Cliente(dnitest,"nombre","apell","hz");
    c=dao.save(c);
    //THEN
    Optional<Cliente> oc=dao.findById(dnitest);
    assertTrue(oc.isPresent());
    //Dejarlo todo en el estado inicial
    dao.delete(c);
  }
}