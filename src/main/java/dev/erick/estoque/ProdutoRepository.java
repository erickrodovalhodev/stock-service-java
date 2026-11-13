package dev.erick.estoque;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findBySku(String sku);
}
    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller
