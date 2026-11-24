package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.CarroException;
import br.org.edu.ifrn.LojaCarro.exception.RecursoNaoEncontradoException;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.model.Log;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Service
public class CarroService {

    private static final Logger log = LoggerFactory.getLogger(CarroService.class);
    private static final int ANO_MINIMO = 1886;
    private static final int TAMANHO_MAXIMO_MODELO = 100;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private LogService logService;

    @Transactional
    public Carro save(Carro c) {
        validarCarro(c);
        c.setModelo(c.getModelo().trim());
        Carro savedCarro = carroRepository.save(c);
        registrarLog("salvar carro", savedCarro);
        return savedCarro;
    }

    @Transactional
    public void deleteById(Long id) {
        validarId(id);
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro com ID " + id + " não encontrado."));
        carroRepository.delete(carro);
        registrarLog("deletar carro", carro);
    }

    public Carro findById(Long id) {
        validarId(id);
        return carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro com ID " + id + " não encontrado."));
    }

    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    @Transactional
    public Carro update(Carro c) {
        if (c.getId() == null) {
            throw new CarroException("O ID do carro para atualização não pode ser nulo.");
        }
        Carro existente = carroRepository.findById(c.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Carro com ID " + c.getId() + " não encontrado para atualização."));

        validarCarro(c);
        existente.setModelo(c.getModelo().trim());
        existente.setAno(c.getAno());
        existente.setPreco(c.getPreco());

        Carro savedCarro = carroRepository.save(existente);
        registrarLog("atualizar carro", savedCarro);
        return savedCarro;
    }

    private void validarCarro(Carro c) {
        if (c == null) {
            throw new CarroException("Os dados do carro não podem ser nulos.");
        }
        validarModelo(c.getModelo());
        validarAno(c.getAno());
        validarPreco(c.getPreco());
    }

    private void validarModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new CarroException("O modelo do carro não pode estar vazio.");
        }
        String modeloNormalizado = modelo.trim();
        if (modeloNormalizado.length() > TAMANHO_MAXIMO_MODELO) {
            throw new CarroException("O modelo do carro deve ter no máximo " + TAMANHO_MAXIMO_MODELO
                    + " caracteres. Tamanho atual: " + modeloNormalizado.length());
        }
    }

    private void validarAno(Integer ano) {
        if (ano == null) {
            throw new CarroException("O ano do carro não pode ser nulo.");
        }
        int anoMaximo = Year.now().getValue() + 1;
        if (ano < ANO_MINIMO || ano > anoMaximo) {
            throw new CarroException("O ano do carro deve estar entre " + ANO_MINIMO + " e " + anoMaximo
                    + ". Valor fornecido: " + ano);
        }
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null) {
            throw new CarroException("O preço do carro não pode ser nulo.");
        }
        if (preco.signum() < 0) {
            throw new CarroException("O preço do carro não pode ser negativo. Valor fornecido: " + preco);
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new CarroException("O ID do carro deve ser maior que zero. ID fornecido: " + id);
        }
    }

    private void registrarLog(String operacao, Carro carro) {
        Log registro = new Log();
        registro.setIdCarro(carro.getId());
        registro.setOperacao(operacao);
        registro.setParametros("modelo = " + carro.getModelo() + ", ano = " + carro.getAno()
                + ", preco = " + carro.getPreco());
        logService.save(registro);
    }
}
