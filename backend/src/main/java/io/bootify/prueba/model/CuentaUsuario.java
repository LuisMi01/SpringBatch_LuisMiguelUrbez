package io.bootify.prueba.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CuentaUsuario {

    @Size(max = 255)
    private String iban;

    private Double saldo;

}
