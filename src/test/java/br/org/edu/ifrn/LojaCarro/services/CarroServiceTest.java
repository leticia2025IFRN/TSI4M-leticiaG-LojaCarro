package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.CarroException;
import br.org.edu.ifrn.LojaCarro.exception.RecursoNaoEncontradoException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CarroServiceTest {

    @Autowired
    private CarroService carroService;

    @Autowired
    private CarroRepository carroRepository;

    @Test
    void deveSalvarCarroArredondandoPrecoParaDuasCasas() {
        Carro salvo = carroService.save(criarCarro("Fusca", 1990, "19999.995"));

        assertNotNull(salvo.getId());
        assertEquals(new BigDecimal("20000.00"), salvo.getPreco());
    }

    @Test
    void deveRejeitarPrecoNegativo() {
        assertThrows(CarroException.class,
                () -> carroService.save(criarCarro("Fusca", 1990, "-0.01")));
        assertEquals(0, carroRepository.count());
    }

    @Test
    void deveRejeitarModeloVazio() {
        assertThrows(CarroException.class,
                () -> carroService.save(criarCarro("   ", 1990, "100.00")));
    }

    @Test
    void deveRejeitarModeloComMaisDeCemCaracteres() {
        assertThrows(CarroException.class,
                () -> carroService.save(criarCarro("a".repeat(101), 1990, "100.00")));
    }

    @Test
    void deveRejeitarAnoForaDaFaixa() {
        assertThrows(CarroException.class,
                () -> carroService.save(criarCarro("Fusca", 1800, "100.00")));
        assertThrows(CarroException.class,
                () -> carroService.save(criarCarro("Fusca", Year.now().getValue() + 2, "100.00")));
    }

    @Test
    void deveBuscarCarroPorId() {
        Carro salvo = carroService.save(criarCarro("Fusca", 1990, "100.00"));

        Carro encontrado = carroService.findById(salvo.getId());

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Fusca", encontrado.getModelo());
    }

    @Test
    void deveLancarErroAoBuscarIdInexistente() {
        assertThrows(RecursoNaoEncontradoException.class, () -> carroService.findById(999L));
    }

    @Test
    void deveAtualizarCarroPreservandoId() {
        Carro salvo = carroService.save(criarCarro("Fusca", 1990, "100.00"));
        Carro atualizado = criarCarro("Gol", 2000, "250.00");
        atualizado.setId(salvo.getId());

        Carro resultado = carroService.update(atualizado);

        assertEquals(salvo.getId(), resultado.getId());
        assertEquals("Gol", resultado.getModelo());
        assertEquals(2000, resultado.getAno());
        assertEquals(new BigDecimal("250.00"), resultado.getPreco());
    }

    @Test
    void deveRemoverCarro() {
        Carro salvo = carroService.save(criarCarro("Fusca", 1990, "100.00"));

        carroService.deleteById(salvo.getId());

        assertTrue(carroService.findAll().isEmpty());
    }

    private Carro criarCarro(String modelo, int ano, String preco) {
        return new Carro(modelo, ano, new BigDecimal(preco));
    }
}
