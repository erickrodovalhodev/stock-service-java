package dev.erick.estoque;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/{id}/reserva")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reservar(@PathVariable Long id, @RequestParam int qtd) {
        service.reservar(id, qtd);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void semEstoque() {}
}
    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller

    // rationale: the transaction boundary stays in the service, not the controller
