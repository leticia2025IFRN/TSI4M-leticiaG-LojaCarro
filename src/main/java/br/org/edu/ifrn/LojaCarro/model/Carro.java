package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo do carro não pode estar vazio.")
    @Size(max = 100, message = "O modelo do carro deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotNull(message = "O ano do carro não pode ser nulo.")
    @Min(value = 1886, message = "O ano do carro deve ser maior ou igual a 1886.")
    @Max(value = 2100, message = "O ano do carro deve ser menor ou igual a 2100.")
    @Column(nullable = false)
    private Integer ano;

    @NotNull(message = "O preço do carro não pode ser nulo.")
    @DecimalMin(value = "0.00", message = "O preço do carro não pode ser negativo.")
    @Digits(integer = 10, fraction = 2, message = "O preço do carro deve ter no máximo 2 casas decimais.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public Carro() {
    }

    public Carro(String modelo, Integer ano) {
        this.modelo = modelo;
        this.ano = ano;
        this.preco = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public Carro(String modelo, Integer ano, BigDecimal preco) {
        this.modelo = modelo;
        this.ano = ano;
        setPreco(preco);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = normalizarPreco(preco);
    }

    private BigDecimal normalizarPreco(BigDecimal preco) {
        if (preco == null) {
            return null;
        }
        return preco.setScale(2, RoundingMode.HALF_UP);
    }
}
