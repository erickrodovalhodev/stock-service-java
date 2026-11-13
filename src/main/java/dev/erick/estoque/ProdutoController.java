package dev.erick.estoque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoResposta> criar(@RequestBody NovoProduto novo) {
        ProdutoResposta criado = ProdutoResposta.de(service.criar(novo.sku(), novo.quantidade()));
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PostMapping("/{id}/reserva")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reservar(@PathVariable Long id, @RequestParam int qtd) {
        service.reservar(id, qtd);
    }
}
