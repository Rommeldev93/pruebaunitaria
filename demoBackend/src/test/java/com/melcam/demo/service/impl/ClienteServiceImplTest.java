package com.melcam.demo.service.impl;

import com.melcam.demo.entity.Cliente;
import com.melcam.demo.repository.ClienteRepository;
import com.melcam.demo.service.ClienteService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("bbdd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteServiceImplTest {
  public static final String DNITEST = "00001T";
  public static final String DNITEST2 = "00002A";
  public static final String DNITEST3 = "00003C";
  @Autowired
  ClienteRepository dao;
  @Autowired
  ClienteService service;

  @Test
  @Order(1)
  void insertar() {
    //GIVEN
    Optional<Cliente> cli = dao.findById(DNITEST);
    assertFalse(cli.isPresent(), "No existe un cliente con tal DNI");
    //WHEN
    Cliente c = new Cliente(DNITEST, "nombre", "apellido", "hz");
    c = service.insertar(c);
    //THEN
    cli = dao.findById(DNITEST);
    assertTrue(cli.isPresent(), "Si existe el cliente pro DNI");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(c);
  }

  @Test
  @Order(2)
  void modificar() {
    //GIVEN
    String nuevoNombre = "nuevoNombre";
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    c = service.insertar(c);
    Optional<Cliente> cli = dao.findById(DNITEST);
    assertTrue(cli.isPresent(), "Si existe un cliente");
    //WHEN
    c.setNombre(nuevoNombre);
    c = service.modificar(c);
    //THEN
    cli = dao.findById(DNITEST);
    assertTrue(cli.isPresent(), "Si existe el cliente");
    assertEquals(nuevoNombre, cli.get().getNombre(), "Debe tener el mismo nombre");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(c);
  }

  @Test
  @Order(3)
  void eliminar() throws Exception {
    //GIVEN
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    c = service.insertar(c);
    Optional<Cliente> cli = dao.findById(DNITEST);
    assertTrue(cli.isPresent(), "si existe el cliente");
    //WHEN
    service.eliminar(c);
    //THEN
    cli = dao.findById(DNITEST);
    assertFalse(cli.isPresent(), "Ya no existe el cliente");

  }

  @Test
  @Order(4)
  void buscarPorDni() throws Exception {
    //GIVEN
    Cliente c1 = new Cliente(DNITEST, "n", "a", "d");
    dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "nom", "ape", "d2");
    dao.save(c2);
    //WHEN
    Cliente rdo = service.buscarPorDni(DNITEST);
    Cliente rdo2 = service.buscarPorDni("");
    //THEN
    assertNotNull(rdo, "Encuentra cliente en BD");
    assertEquals(DNITEST, rdo.getDni(), "Tiene el DNI buscado");
    assertNull(rdo2, "No encuentra Cliente en BD");
    //Dejarlo todo como estaba inicialmente
    dao.delete(c1);
    dao.delete(c2);
  }

  @Test
  @Order(5)
  void buscarTodo() throws Exception{
    //GIVEN
    Cliente c1 = new Cliente(DNITEST, "n", "a", "d");
    dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "nom", "ape", "d2");
    dao.save(c2);
    Cliente c3 = new Cliente(DNITEST3, "pedro", "apel", "d3");
    dao.save(c3);
    List<Cliente>clienteBD=dao.findAll();
    assertEquals(3, clienteBD.size(),"Hay 3 clientes en BD");
    //WHEN
    List<Cliente>rdo=service.buscarTodo();
    //THEN
    assertEquals(3, clienteBD.size(),"Debe existir 3 clientes en BD");
    //Dejarlo todo como estaba inicialmente
    dao.delete(c1);
    dao.delete(c2);
    dao.delete(c3);
  }

  @Test
  @Order(6)
  void buscarPorNombre() throws Exception{
    //GIVEN
    String ana="ana";
    Cliente c1 = new Cliente(DNITEST, ana, "a", "d");
    dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "nom", "ape", "d2");
    dao.save(c2);
    Cliente c3 = new Cliente(DNITEST3, ana, "apel", "d3");
    dao.save(c3);
    dao.save(c3);
    List<Cliente>clienteBD=dao.findAll();
    assertEquals(3, clienteBD.size(),"Hay 3 clientes en BD");
    //WHEN
    List<Cliente>rdo=service.buscarPorNombre(ana);
    //THEN
    assertEquals(2, rdo.size(),"Solo existe 2 empleados con nombre ana");
    //Dejarlo todo como estaba inicialmente
    dao.delete(c1);
    dao.delete(c2);
    dao.delete(c3);
  }
}