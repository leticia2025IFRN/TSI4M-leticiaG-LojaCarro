package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do usuário não pode estar vazio.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O cargo do usuário não pode estar vazio.")
    @Column(nullable = false)
    private String cargo;

    @NotNull(message = "O salário do usuário não pode ser nulo.")
    @DecimalMin(value = "0.00", message = "O salário do usuário não pode ser negativo.")
    @Digits(integer = 10, fraction = 2, message = "O salário do usuário deve ter no máximo 2 casas decimais.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salario = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public void setSalario(BigDecimal salario) {
        if (salario == null) {
            this.salario = null;
            return;
        }
        this.salario = salario.setScale(2, RoundingMode.HALF_UP);
    }
}
