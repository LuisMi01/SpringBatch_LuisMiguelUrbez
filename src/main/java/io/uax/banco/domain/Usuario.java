package io.uax.banco.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false, name = "id")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, name = "account_id")
    private String accountId;

    @Column(nullable = false, name = "transaction_type")
    private String transactionType;

    @Column(nullable = false, name = "amount")
    private Double amount;

    @Column(nullable = false, name = "transaction_date")
    private String transactionDate;

}
