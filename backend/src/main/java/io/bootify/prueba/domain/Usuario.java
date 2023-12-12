package io.bootify.prueba.domain;

import io.bootify.prueba.model.CuentaUsuario;
import io.bootify.prueba.model.ListaMovimientos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
public class Usuario {

    @Id
    private Long id;

    @Size(max = 255)
    private String nombre;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @Valid
    private CuentaUsuario cuenta;

    @Valid
    private ListaMovimientos movimientos;

}
