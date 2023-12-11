package io.uax.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.uax.backend.model.CuentaBanco;
import io.uax.backend.model.MovimientosUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "banco")
public class Usuario {

    @Id
    @NotNull
    private Integer id;

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
    @DocumentReference
    private CuentaBanco cuenta;

    @NotNull
    private List<Double> movimientos;

}