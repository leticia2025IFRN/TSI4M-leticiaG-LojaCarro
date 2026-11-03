package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.model.Log;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarroServiceTest {
    @Test
    public void save() {
        Carro carro = createCar("Uno", 2018, 44900.00);

        assertEquals(carro.getAno() > 1980 && carro.getAno() <= LocalDate.now().getYear(), true);
        assertEquals(carro.getPreco() > 0, true);
        assertEquals(carro.getId() > 0, true);
    }

    private Carro createCar(String modelo, int ano, Double preco) {
        Carro carro = new Carro();

        carro.setModelo(modelo);
        carro.setAno(ano);
        carro.setPreco(preco);

        return carro;
    }
}
