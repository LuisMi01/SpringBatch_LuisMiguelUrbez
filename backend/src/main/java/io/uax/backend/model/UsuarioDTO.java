package io.uax.backend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "banco")
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Valid
    private CuentaBanco cuenta;

    @NotNull
    @Valid
    private MovimientosUsuario movimientos;

}
