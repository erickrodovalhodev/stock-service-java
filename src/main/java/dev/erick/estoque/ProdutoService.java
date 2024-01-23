package dev.erick.estoque;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository repositorio;

    public ProdutoService(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public void reservar(Long id, int qtd) {
        Produto p = repositorio.findById(id).orElseThrow();
        p.reservar(qtd);
        repositorio.save(p);
    }
}
    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller
