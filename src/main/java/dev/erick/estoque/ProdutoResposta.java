package dev.erick.estoque;

public record ProdutoResposta(Long id, String sku, int quantidade) {

    static ProdutoResposta de(Produto p) {
        return new ProdutoResposta(p.getId(), p.getSku(), p.getQuantidade());
    }
}
