package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.dto.CarroRequestDTO;
import br.org.edu.ifrn.LojaCarro.dto.CarroResponseDTO;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping
    public ResponseEntity<CarroResponseDTO> salvar(@Valid @RequestBody CarroRequestDTO dto) {
        Carro salvo = carroService.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarroResponseDTO.from(salvo));
    }

    @GetMapping
    public ResponseEntity<List<CarroResponseDTO>> listar() {
        List<CarroResponseDTO> carros = carroService.findAll().stream()
                .map(CarroResponseDTO::from)
                .toList();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(CarroResponseDTO.from(carroService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroResponseDTO> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody CarroRequestDTO dto) {
        Carro carro = toEntity(dto);
        carro.setId(id);
        return ResponseEntity.ok(CarroResponseDTO.from(carroService.update(carro)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Carro toEntity(CarroRequestDTO dto) {
        Carro carro = new Carro();
        carro.setModelo(dto.modelo());
        carro.setAno(dto.ano());
        carro.setPreco(dto.preco());
        return carro;
    }
}
