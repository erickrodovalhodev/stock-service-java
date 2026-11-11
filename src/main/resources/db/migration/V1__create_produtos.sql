-- bigserial (e não serial): a entidade usa Long id, e ddl-auto=validate exige bigint.
CREATE TABLE produtos (
    id         BIGSERIAL    PRIMARY KEY,
    sku        VARCHAR(64)  NOT NULL UNIQUE,
    quantidade INTEGER      NOT NULL CHECK (quantidade >= 0),
    versao     BIGINT       NOT NULL DEFAULT 0
);

-- Seed usado na demo e no teste de concorrência.
INSERT INTO produtos (sku, quantidade, versao) VALUES ('SKU-CONC', 50, 0);
