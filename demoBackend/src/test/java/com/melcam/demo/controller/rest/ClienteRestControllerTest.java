package com.melcam.demo.controller.rest;

import com.melcam.demo.entity.Cliente;
import com.melcam.demo.repository.ClienteRepository;
import com.melcam.demo.service.ClienteService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@Tag("bd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteRestControllerTest {
  public static final String DNITEST = "00001T";
  public static final String DNITEST2 = "00002A";
  public static final String DNITEST3 = "00003C";
  @Autowired
  ClienteRepository dao;
  @Autowired
  ClienteRestController controller;

  @InjectMocks
  ClienteRestController controllerMock;
  @Mock
  ClienteService serviceMock;

  @Test
  @Order(1)
  void insertarClienteTest() {
    //GIVEN
    Optional<Cliente> cli = dao.findById(DNITEST);
    assertFalse(cli.isPresent(), "No existe un cliente con tal DNI");
    //WHEN
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    ResponseEntity<Cliente> res = controller.insertarCliente(c);
    //THEN
    assertEquals(HttpStatus.CREATED, res.getStatusCode(), "Comprobacion de codigo de error del servicio");
    assertEquals(DNITEST, res.getBody().getDni(), "Mismo DNI");
    Optional<Cliente> op = dao.findById(DNITEST);
    assertTrue(op.isPresent(), "El cliente si esta en BD");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(op.get());
  }

  @Test
  @Order(2)
  void insertarClienteTestException() {
    //GIVEN-->estamos forzando que la que la longitud del DNI sea mayor a 10
    Cliente c = new Cliente(DNITEST + "0123456789", "n", "a", "d");
    //WHEN
    ResponseEntity<Cliente> res = controller.insertarCliente(c);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode(), "Error controlado");
    assertNull(res.getBody().getDni(), "Cliente nulo");
  }

  @Test
  @Order(3)
  void modificarClienteTest() {
    //GIVEN
    String nuevoNombre = "nuevoNombre";
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    c = dao.save(c);
    Optional<Cliente> op = dao.findById(DNITEST);
    assertTrue(op.isPresent(), "Si existe el Cliente");
    //WHEN
    c.setNombre(nuevoNombre);
    ResponseEntity<Cliente> res = controller.modificarCliente(c);
    //THEN
    assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode(), "Comprobacion de codigo de error del servicio");
    assertEquals(DNITEST, res.getBody().getDni(), "Mismo DNI");
    assertEquals(nuevoNombre, res.getBody().getNombre(), "Nombre modificado");

    Optional<Cliente> op2 = dao.findById(DNITEST);
    assertTrue(op2.isPresent(), "Si existe el Cliente");
    assertEquals(nuevoNombre, op2.get().getNombre(), "Nombre modificado");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(op.get());
  }

  @Test
  @Order(4)
  void modificarClienteTestException() {
    //GIVEN-->Estamos forzando a que la longitud del dni sea mayor a 10
    Cliente c = new Cliente(DNITEST + "0123456789", "n", "a", "d");
    //WHEN
    ResponseEntity<Cliente> res = controller.modificarCliente(c);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode(), "Error controlado");
    assertNull(res.getBody().getDni(), "Cliente nulo");
  }

  @Test
  @Order(5)
  void eliminarClienteTest() {
    //GIVEN
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    c = dao.save(c);
    Optional<Cliente> op = dao.findById(DNITEST);
    assertTrue(op.isPresent(), "Si existe el Cliente");
    //WHEN
    ResponseEntity<String> res = controller.eliminarCliente(c);
    //THEN
    assertEquals(HttpStatus.OK, res.getStatusCode(), "Codigo de retorno correcto");
    op = dao.findById(DNITEST);
    assertFalse(op.isPresent(), "Ya no existe el Cliente");
  }

  @Test
  @Order(6)
  void eliminarClienteTestException() throws Exception {
    //GIVEN
    Cliente c = new Cliente(DNITEST, "n", "a", "d");
    //WHEN
    doThrow(new Exception()).when(serviceMock).eliminar(c);
    ResponseEntity<String> res = controllerMock.eliminarCliente(c);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode(), "Error controlado");
  }

  @Test
  @Order(7)
  void buscarPorDniTest() {
    //GIVEN
    Cliente c1 = new Cliente(DNITEST, "n", "a", "d");
    c1 = dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "n", "a", "d");
    c2 = dao.save(c2);
    //WHEN
    ResponseEntity<Cliente> res = controller.buscarPorDni(DNITEST);
    //THEN
    assertEquals(HttpStatus.OK, res.getStatusCode(), "Codigo de retorno correcto");
    assertNotNull(res.getBody(), "Cliente existe");
    assertEquals(DNITEST, res.getBody().getDni(), "Mismo dni");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(c1);
    dao.delete(c2);
  }

  @Test
  @Order(8)
  void buscarPorDniTestException() throws Exception {
    //GIVEN
    List<Cliente> lc = new ArrayList<Cliente>();
    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarPorDni(DNITEST);
    ResponseEntity re = controllerMock.buscarPorDni(DNITEST);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion");
  }

  @Test
  @Order(9)
  void buscarTodosTest() {
    //GIVEN
    Cliente c1 = new Cliente(DNITEST, "n", "a", "d");
    dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "nom", "ape", "d2");
    dao.save(c2);
    Cliente c3 = new Cliente(DNITEST3, "pedro", "apel", "d3");
    dao.save(c3);
    List<Cliente> clienteBD = dao.findAll();
    assertEquals(3, clienteBD.size(), "Hay 3 clientes en BD");
    //WHEN
    ResponseEntity<List<Cliente>> res = controller.buscarTodos();
    //THEN
    assertEquals(HttpStatus.OK, res.getStatusCode(), "Codigo de retorno correcto");
    assertEquals(3, res.getBody().size(), "Numero de Cliente correcto");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(c1);
    dao.delete(c2);
    dao.delete(c3);
  }

  @Test
  @Order(10)
  void buscarTodosTestException() throws Exception {
    //GIVEN
    List<Cliente> lc = new ArrayList<Cliente>();
    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarTodo();
    ResponseEntity<List<Cliente>> re = controllerMock.buscarTodos();
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion");
  }

  @Test
  @Order(11)
  void buscarPorNombreTest() {
    //GIVEN
    String ana = "ana";
    Cliente c1 = new Cliente(DNITEST, ana, "a", "d");
    c1 = dao.save(c1);
    Cliente c2 = new Cliente(DNITEST2, "n", "a2", "d2");
    c2 = dao.save(c2);
    Cliente c3 = new Cliente(DNITEST3, ana, "a3", "d3");
    c2 = dao.save(c3);
    List<Cliente> clienteBD = dao.findAll();
    assertEquals(3, clienteBD.size());
    //WHEN
    ResponseEntity<List<Cliente>> res = controller.buscarPorNombre(ana);
    //THEN
    assertEquals(HttpStatus.OK, res.getStatusCode(), "Codigo de retorno correcto");
    assertEquals(2, res.getBody().size(), "Solo existen dos clientes con nombre ana");
    //DEJARLO COMO ESTABA INICIALMENTE
    dao.delete(c1);
    dao.delete(c2);
    dao.delete(c3);
  }

  @Test
  @Order(12)
  void buscarPorNombreTestException() throws Exception {
    //GIVEN
    String ana = "ana";
    List<Cliente> lc = new ArrayList<Cliente>();
    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarPorNombre(ana);
    ResponseEntity<List<Cliente>> re = controllerMock.buscarPorNombre(ana);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion");
  }
}