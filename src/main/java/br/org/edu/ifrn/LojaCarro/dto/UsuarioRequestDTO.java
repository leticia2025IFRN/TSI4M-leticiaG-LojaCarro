package br.org.edu.ifrn.LojaCarro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome do usuário não pode estar vazio.")
        String nome,

        @NotBlank(message = "O cargo do usuário não pode estar vazio.")
        String cargo,

        @NotNull(message = "O salário do usuário não pode ser nulo.")
        @DecimalMin(value = "0.00", message = "O salário do usuário não pode ser negativo.")
        @Digits(integer = 10, fraction = 2, message = "O salário do usuário deve ter no máximo 2 casas decimais.")
        BigDecimal salario
) {
}
