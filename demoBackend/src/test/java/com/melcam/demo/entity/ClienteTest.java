package com.melcam.demo.entity;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("normal")
class ClienteTest {
  @Test
  void test() {
    //GIVEN
    String dni = "00001111";
    String nombre = "nombre";
    String apellidos = "apellidos";
    String direccion = "direccion";

    //WHEN
    Cliente c1 = new Cliente();
    c1.setDni(dni);
    c1.setNombre(nombre);
    c1.setApellidos(apellidos);
    c1.setDireccion(direccion);

    Cliente c2 = new Cliente(dni, "nom2", "ape2", "av.");

    //THEN
    assertAll(
            ()->assertEquals(dni, c1.getDni(),"Mismo dni"),
            ()->assertEquals(nombre, c1.getNombre(),"Mismo nombre"),
            ()->assertEquals(apellidos, c1.getApellidos(),"Mismo apellido"),
            ()->assertEquals(direccion, c1.getDireccion(),"Mismo direccion"),
            ()->assertEquals(c1,c1,"Mismo objeto"),
            ()->assertNotEquals(dni,c2,"Distinto tipo de objeto"),
            ()->assertEquals(c1,c2,"Objetos distintos con el mismo dni"),
            ()->assertEquals(Objects.hash(c1.getDni()),c1.hashCode())
    );
  }

}