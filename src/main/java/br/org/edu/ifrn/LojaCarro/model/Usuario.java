package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    String cargo;
}
