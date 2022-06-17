package com.melcam.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class Empleado {

  @Id
  @Column(length = 15)
  private String userName;

  private String nombre;
  private String apellidos;
  private long salario;

  public Empleado() {
  }

  public Empleado(String userName, String nombre, String apellidos, long salario) {
    this.userName = userName;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.salario = salario;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public long getSalario() {
    return salario;
  }

  public void setSalario(long salario) {
    this.salario = salario;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Empleado empleado = (Empleado) o;
    return Objects.equals(userName, empleado.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }
}
