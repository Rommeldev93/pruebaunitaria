package com.melcam.demo.repository;

import com.melcam.demo.entity.Empleado;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@Tag("mockeado")
@ExtendWith(MockitoExtension.class)
public class EmpleadoRepositoryTest {

  @Mock
  EmpleadoRepository empleadoMock;

  @Test
  void test(){
    //GIVEN
    String userName="testUser";
    Empleado e=new Empleado(userName,"nombre","apellidos",3000);

    Optional<Empleado> ope=Optional.of(e);
    //WHEN
    when(empleadoMock.findById(userName)).thenReturn(ope);
     Empleado rdo1=empleadoMock.findById(userName).orElse(null);
     Empleado rdo2=empleadoMock.findById("").orElse(null);

     //THEN
    assertEquals(e,rdo1);
    assertNull(rdo2);

  }
}
