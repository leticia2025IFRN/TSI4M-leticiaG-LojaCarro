package br.org.edu.ifrn.LojaCarro.dto;

import br.org.edu.ifrn.LojaCarro.model.Usuario;

import java.math.BigDecimal;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String cargo,
        BigDecimal salario
) {

    public static UsuarioResponseDTO from(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getCargo(), usuario.getSalario());
    }
}
