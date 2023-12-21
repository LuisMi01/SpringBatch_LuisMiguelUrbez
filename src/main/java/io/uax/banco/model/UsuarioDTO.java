package io.uax.banco.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String accountId;

    @NotNull
    @Size(max = 255)
    private String transactionType;

    @NotNull
    private Double amount;

    private String transactionDate;

}
