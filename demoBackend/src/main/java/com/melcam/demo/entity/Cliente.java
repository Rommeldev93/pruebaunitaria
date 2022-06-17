package com.melcam.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class Cliente {
  @Id
  @Column(length =10)
  private String dni;
  private String nombre;
  private String apellidos;
  private String direccion;

  public Cliente() {
  }

  public Cliente(String dni, String nombre, String apellidos, String direccion) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.direccion = direccion;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cliente cliente = (Cliente) o;
    return Objects.equals(dni, cliente.dni);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dni);
  }
}
