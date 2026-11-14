# stock-service-java

Stock reservation service. Spring Boot 3, JPA with `@Version` optimistic
locking, Flyway.

## run

    docker compose up --build -d     # api :8080 + postgres 16; Flyway aplica V1 e o seed
    docker compose ps
    docker compose down -v           # derruba e apaga o volume

Configuração em `src/main/resources/application.yml`: `DB_URL`, `DB_USERNAME` e
`DB_PASSWORD`, com defaults `jdbc:postgresql://db:5432/estoque`, `postgres`, `postgres`.
O schema é da migration (`ddl-auto: validate`); o Hibernate só confere.

## demo

    # produto 1 = seed SKU-CONC com 50 unidades
    curl -si -X POST 'localhost:8080/produtos/1/reserva?qtd=1' | head -1     # 204
    curl -si -X POST 'localhost:8080/produtos/1/reserva?qtd=999' | head -1   # 409

    # cadastro
    curl -si -X POST localhost:8080/produtos -H 'Content-Type: application/json' \
      -d '{"sku":"SKU-NOVO","quantidade":10}'                                # 201 + Location

## the problem it solves

Two checkouts hitting the same SKU at the same time. A naive read-modify-write
oversells. The `@Version` column makes the second transaction fail and the
client retries against fresh state.

## tests

    mvn -q test     # JDK 21+, sem Docker: H2 em modo PostgreSQL com a mesma migration

`ReservaConcorrenteTest` fires 50 threads at one SKU with 50 units and asserts
that exactly 50 are reserved and none go negative.

## structure

- `ProdutoService.reservar` — the only write path; each attempt runs in its own
  transaction and is retried on optimistic lock conflict (budget: 100 attempts)
- `EstoqueInsuficienteException` — mapped to 409 in `ApiExceptionHandler` (`@RestControllerAdvice`)
- `src/main/resources/db/migration` — Flyway
