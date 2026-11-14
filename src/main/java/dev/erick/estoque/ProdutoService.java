package dev.erick.estoque;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ProdutoService {

    /** Orçamento de retry do lock otimista: cada rodada tem ao menos um vencedor. */
    static final int TENTATIVAS = 100;

    private final ProdutoRepository repositorio;
    private final TransactionTemplate tx;

    public ProdutoService(ProdutoRepository repositorio, PlatformTransactionManager txManager) {
        this.repositorio = repositorio;
        this.tx = new TransactionTemplate(txManager);
    }

    @Transactional
    public Produto criar(String sku, int quantidade) {
        return repositorio.save(new Produto(sku, quantidade));
    }

    /**
     * Único caminho de escrita do estoque. Cada tentativa é uma transação própria
     * (lê, valida, grava com @Version); se outra transação gravou antes, a versão
     * não bate, a tentativa é descartada e repetida contra o estado novo.
     */
    public void reservar(Long id, int qtd) {
        for (int tentativa = 1; ; tentativa++) {
            try {
                tx.executeWithoutResult(status -> {
                    Produto p = repositorio.findById(id).orElseThrow();
                    p.reservar(qtd);
                    repositorio.saveAndFlush(p);
                });
                return;
            } catch (OptimisticLockingFailureException conflito) {
                if (tentativa >= TENTATIVAS) throw conflito;
                pausa(tentativa);
            }
        }
    }

    private static void pausa(int tentativa) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1, 2L + Math.min(tentativa, 10)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
