package dev.erick.estoque;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservaConcorrenteTest {

    @Autowired ProdutoService service;
    @Autowired ProdutoRepository repositorio;

    @Test
    void fiftyThreadsOneSku() throws Exception {
        Produto p = repositorio.save(new Produto("SKU-CONC", 50));
        ExecutorService pool = Executors.newFixedThreadPool(50);
        AtomicInteger ok = new AtomicInteger();
        CountDownLatch largada = new CountDownLatch(1);

        for (int i = 0; i < 50; i++) {
            pool.submit(() -> {
                largada.await();
                try { service.reservar(p.getId(), 1); ok.incrementAndGet(); }
                catch (Exception ignored) {}
                return null;
            });
        }
        largada.countDown();
        pool.shutdown();
        assertTrue(pool.awaitTermination(30, TimeUnit.SECONDS));

        assertEquals(0, repositorio.findById(p.getId()).orElseThrow().getQuantidade());
        assertEquals(50, ok.get());
    }
}
    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller
