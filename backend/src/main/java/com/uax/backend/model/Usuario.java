package com.uax.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "banco")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Double saldo;
    private String cuenta;
    private List<Double> movimientos;
}
