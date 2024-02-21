package dev.erick.estoque;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private int quantidade;

    @Version
    private Long versao;

    protected Produto() {}

    public Produto(String sku, int quantidade) {
        this.sku = sku;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public int getQuantidade() { return quantidade; }

    public void reservar(int qtd) {
        if (quantidade < qtd) throw new EstoqueInsuficienteException();
        quantidade -= qtd;
    }
}
    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller
