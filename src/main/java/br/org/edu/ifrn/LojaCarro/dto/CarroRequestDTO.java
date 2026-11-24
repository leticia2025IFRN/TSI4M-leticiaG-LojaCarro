package br.org.edu.ifrn.LojaCarro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CarroRequestDTO(
        @NotBlank(message = "O modelo do carro não pode estar vazio.")
        @Size(max = 100, message = "O modelo do carro deve ter no máximo 100 caracteres.")
        String modelo,

        @NotNull(message = "O ano do carro não pode ser nulo.")
        @Min(value = 1886, message = "O ano do carro deve ser maior ou igual a 1886.")
        @Max(value = 2100, message = "O ano do carro deve ser menor ou igual a 2100.")
        Integer ano,

        @NotNull(message = "O preço do carro não pode ser nulo.")
        @DecimalMin(value = "0.00", message = "O preço do carro não pode ser negativo.")
        @Digits(integer = 10, fraction = 2, message = "O preço do carro deve ter no máximo 2 casas decimais.")
        BigDecimal preco
) {
}
