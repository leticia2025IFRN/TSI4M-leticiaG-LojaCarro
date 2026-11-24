package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.exception.RecursoNaoEncontradoException;
import br.org.edu.ifrn.LojaCarro.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void deveSalvarUsuarioArredondandoSalario() {
        Usuario salvo = usuarioService.save(criarUsuario("Maria", "Gerente", "1500.555"));

        assertNotNull(salvo.getId());
        assertEquals(new BigDecimal("1500.56"), salvo.getSalario());
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario salvo = usuarioService.save(criarUsuario("Maria", "Gerente", "1500.00"));
        Usuario novo = criarUsuario("Joana", "Diretora", "3000.00");

        Usuario atualizado = usuarioService.update(novo, salvo.getId());

        assertEquals(salvo.getId(), atualizado.getId());
        assertEquals("Joana", atualizado.getNome());
        assertEquals("Diretora", atualizado.getCargo());
        assertEquals(new BigDecimal("3000.00"), atualizado.getSalario());
    }

    @Test
    void deveLancarErroAoBuscarUsuarioInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.findById(999L));
    }

    @Test
    void deveLancarErroAoAtualizarUsuarioInexistente() {
        assertThrows(RecursoNaoEncontradoException.class,
                () -> usuarioService.update(criarUsuario("Maria", "Gerente", "1500.00"), 999L));
    }

    private Usuario criarUsuario(String nome, String cargo, String salario) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCargo(cargo);
        usuario.setSalario(new BigDecimal(salario));
        return usuario;
    }
}
