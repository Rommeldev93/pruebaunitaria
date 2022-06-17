package com.melcam.demo.controller.rest;

import com.melcam.demo.entity.Empleado;
import com.melcam.demo.repository.EmpleadoRepository;
import com.melcam.demo.service.EmpleadoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Tag("Mockeado")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmpleadoRestControllerTest {

  private static final String username = "userTest";
  @Mock
  EmpleadoRepository daoMock;
  @Mock
  EmpleadoService serviceMock;
  @InjectMocks
  EmpleadoRestController controllerMock;

  @Test
  @Order(1)
  void insertarTest() throws Exception {
    //GIVEN
    Empleado e1 = new Empleado(username, "nombre", "apellido", 1200);
    Empleado e2 = new Empleado(username, "nombre", "apellido", 2000);
    //WHEN
    when(serviceMock.insert(e1)).thenReturn(e2);
    ResponseEntity<Empleado> ree = controllerMock.insertar(e1);
    //THEN
    assertAll(
            () -> assertEquals(HttpStatus.CREATED, ree.getStatusCode(), "Codigo de vuelta"),
            () -> assertEquals(e1, ree.getBody(), "Mismo empleado"),
            () -> assertEquals(2000, ree.getBody().getSalario(), "Nuevo salario")
    );
  }

  @Test
  @Order(2)
  void insertarTestException() throws Exception {
    //GIVEN
    Empleado e1 = new Empleado(username, "nombre", "apellido", 1200);
    //WHEN
    doThrow(new Exception()).when(serviceMock).insert(e1);
    ResponseEntity<Empleado> ree = controllerMock.insertar(e1);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ree.getStatusCode(), "Salta Excepción");
  }

  @Test
  @Order(3)
  void modificarTest() throws Exception {
    //GIVEN
    long nuevoSalario = 3000;
    Empleado e1 = new Empleado(username, "nombre", "apellido", 0);
    Empleado e2 = new Empleado(username, "nombre", "apellido", nuevoSalario);
    //WHEN
    when(serviceMock.modificar(e1)).thenReturn(e2);
    ResponseEntity<Empleado> ree = controllerMock.modificar(e1);
    //THEN
    assertEquals(HttpStatus.NO_CONTENT, ree.getStatusCode(), "Comprobacion de codigo de error de servicio");
    assertEquals(e2, ree.getBody(), "Mismo empleado");
    assertEquals(nuevoSalario, ree.getBody().getSalario(), "Nuevo salario igual");
  }

  @Test
  @Order(4)
  void modificarTestException() throws Exception {
    //GIVEN
    Empleado e = new Empleado(username, "nombre", "apellido", 0);
    //WHEN
    doThrow(new Exception()).when(serviceMock).modificar(e);
    ResponseEntity<Empleado> ree = controllerMock.modificar(e);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ree.getStatusCode(), "Salta Excepcion");
  }

  @Test
  @Order(5)
  void buscarPorUsernameTest() throws Exception {
    //GIVEN
    Empleado e = new Empleado(username, "nombre", "apellido", 0);
    //WHEN
    when(serviceMock.buscarPorUsername(username)).thenReturn(e);
    ResponseEntity<Empleado> ree = controllerMock.buscarPorUsername(username);
    //THEN
    assertAll(
            () -> assertEquals(HttpStatus.OK, ree.getStatusCode(), "Resultado OK"),
            () -> assertEquals(username, ree.getBody().getUserName(), "Mismo username")
    );
  }

  @Test
  @Order(6)
  void buscarPorUsernameTestException() throws Exception {
    //GIVEN

    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarPorUsername(username);
    ResponseEntity<Empleado> ree = controllerMock.buscarPorUsername(username);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ree.getStatusCode(), "Salta Excepcion");
  }

  @Test
  @Order(7)
  void eliminarTest() throws Exception {
    //GIVEN
    Empleado e = new Empleado(username, "nombre", "apellido", 0);
    //WHEN
    ResponseEntity<String> re = controllerMock.eliminar(e);
    //THEN
    assertEquals(HttpStatus.OK, re.getStatusCode(), "Codigo de retorno correcto");
  }

  @Test
  @Order(8)
  void eliminarTestException() throws Exception {
    //GIVEN
    Empleado e = new Empleado(username, "nombre", "apellido", 0);
    //WHEN
    doThrow(new Exception()).when(serviceMock).eliminar(e);
    ResponseEntity<String> re = controllerMock.eliminar(e);
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta Excepcion");
  }

  @Test
  @Order(9)
  void buscarTodosTest() throws Exception {
    //GIVEN
    Empleado e1 = new Empleado(username, "nombre", "apellido", 0);
    Empleado e2 = new Empleado(username, "nombre", "apellido", 100);
    List<Empleado> li = Arrays.asList(e1, e2);
    //WHEN
    when(serviceMock.buscarTodos()).thenReturn(li);
    ResponseEntity<List<Empleado>> re = controllerMock.buscarTodos();
    //THEN
    assertEquals(HttpStatus.OK, re.getStatusCode(), "Codigo de retorno correcto");
    assertEquals(2, re.getBody().size(), "Devuelve los dos empleados existentes");
  }

  @Test
  @Order(10)
  void buscarTodosTestException() throws Exception {
    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarTodos();
    ResponseEntity<List<Empleado>> re = controllerMock.buscarTodos();
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion");
  }
  @Test
  @Order(11)
  void buscarPorNombreTest() throws Exception {
    //GIVEN
    String nombre = "nombreEmpleado";
    Empleado e = new Empleado(username, nombre, "apellido", 0);
    List<Empleado> li = Arrays.asList(e);
    //WHEN
    when(serviceMock.buscarPorNombre(nombre)).thenReturn(li);
    ResponseEntity<List<Empleado>> re = controllerMock.buscarPorNombre(nombre);
    //THEN
    assertEquals(HttpStatus.OK, re.getStatusCode(), "Codigo de retorno correcto");
    assertEquals(1, re.getBody().size(), "Devuelve el empleado existentes");
    assertEquals(nombre,re.getBody().get(0).getNombre());
  }

  @Test
  @Order(12)
  void buscarPorNombreTestException() throws Exception {
    //WHEN
    doThrow(new Exception()).when(serviceMock).buscarPorNombre(ArgumentMatchers.anyString());
    ResponseEntity<List<Empleado>> re = controllerMock.buscarPorNombre(ArgumentMatchers.anyString());
    //THEN
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion");
  }
}