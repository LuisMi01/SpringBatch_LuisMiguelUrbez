package io.uax.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CuentaBanco {

    @NotNull
    @Size(max = 255)
    private String iban;

    private List<Double> totalDinero;

}
