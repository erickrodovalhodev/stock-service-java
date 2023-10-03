# stock-service-java

Stock reservation service. Spring Boot 3, JPA with `@Version` optimistic
locking, Flyway.

## run

    docker compose up

## the problem it solves

Two checkouts hitting the same SKU at the same time. A naive read-modify-write
oversells. The `@Version` column makes the second transaction fail and the
client retries against fresh state.

## tests

`ReservaConcorrenteTest` fires 50 threads at one SKU with 50 units and asserts
that exactly 50 are reserved and none go negative.

## structure

- `ProdutoService.reservar` — the only write path, `@Transactional`
- `EstoqueInsuficienteException` — mapped to 409
- `src/main/resources/db/migration` — Flyway
