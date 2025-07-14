package com.banking.eagle.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private TransactionType type;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    private Account account;
}
