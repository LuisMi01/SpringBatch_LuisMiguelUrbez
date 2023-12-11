package io.uax.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "banco")
public class CuentaBanco {

    @NotNull
    @Size(max = 255)
    private String iban;

    private Double saldo;

}
