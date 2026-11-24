package br.org.edu.ifrn.LojaCarro.dto;

import br.org.edu.ifrn.LojaCarro.model.Carro;

import java.math.BigDecimal;

public record CarroResponseDTO(
        Long id,
        String modelo,
        Integer ano,
        BigDecimal preco
) {

    public static CarroResponseDTO from(Carro carro) {
        return new CarroResponseDTO(carro.getId(), carro.getModelo(), carro.getAno(), carro.getPreco());
    }
}
