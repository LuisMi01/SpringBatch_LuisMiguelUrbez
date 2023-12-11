package io.uax.backend.model;

import java.util.List;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "banco")
public class MovimientosUsuario {

    private List<Double> movimientos;

}
