package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsuario;
    private Long idCarro;
    private LocalDateTime dataHora;
    private String operacao;
    private String parametro;
}
