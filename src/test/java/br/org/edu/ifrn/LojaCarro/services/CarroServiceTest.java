package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarroServiceTest {
    @Test
    public void save() {
        Carro carro = new Carro();
        carro.setModelo("Uno");
        carro.setAno(2018);
        carro.setPreco(44900);

        assertEquals(carro.getAno() > 1980 && carro.getAno() <= LocalDate.now().getYear(), true);
        assertEquals(carro.getPreco() > 0, true);
    }
}
