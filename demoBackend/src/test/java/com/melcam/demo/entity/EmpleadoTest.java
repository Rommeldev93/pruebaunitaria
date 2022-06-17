package com.melcam.demo.entity;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("normal")
class EmpleadoTest {
  @Test
  void Test() {
    //GIVEN
    String username = "testUsername";
    String nombre = "nombre";
    String apellidos = "apellido";
    long salario = 1500;
//WHEN
    Empleado e1 = new Empleado();
    e1.setUserName(username);
    e1.setNombre(nombre);
    e1.setApellidos(apellidos);
    e1.setSalario(salario);

    Empleado e2 = new Empleado(username, "a", "b", 10);
    //THEN

    //PARA QUE ME IMPRIMA TODOS LOS ERRORES
    assertAll(() -> assertEquals(username, e1.getUserName(), "Mismo username"),
            () -> assertEquals(nombre, e1.getNombre(), "Mismo nombre"),
            () -> assertEquals(apellidos, e1.getApellidos(), "Mismo apellidos"),
            () -> assertEquals(salario, e1.getSalario(), "Mismo salario"),
            () -> assertEquals(e1, e1, "Mismo objeto"),
            () -> assertNotEquals(e1, nombre, "Distinto tipo de objeto"),
            () -> assertEquals(e1, e2, "Objetos distintos con mismo username"),
            () -> assertEquals(Objects.hash(e1.getUserName()), e1.hashCode()));

  }
}