package br.com.freelance_management_api.freelance_management_api.controller;


import br.com.freelance_management_api.freelance_management_api.entities.CadastroFreeLance;
import br.com.freelance_management_api.freelance_management_api.service.CadastroFreeLanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/freelance")
public class FreeLanceController {

    @Autowired
    private CadastroFreeLanceService cadastroFreeLanceService;

    /**
     * Busca Todos os Free Lance que estão na Base
     *
     * @return Collection<CadastroFreeLance>
     */
    @GetMapping
    public ResponseEntity<Collection<CadastroFreeLance>> findAll() {
        var freelance = cadastroFreeLanceService.findAll();
        return ResponseEntity.ok(freelance);
    }

    /**
     * Busca Free Lance pelo Id
     *
     * @param id
     * @return CadastroFreeLance
     */
    @GetMapping("/{id}")
    public ResponseEntity<CadastroFreeLance> findById(@PathVariable UUID id){
        var freelance = cadastroFreeLanceService.findById(id);
        return ResponseEntity.ok(freelance);
    }

    /**
     * Inclui novo Free lance
     *
     * @param freelance
     * @return CadastroFreeLance
     */
    @PostMapping
    public ResponseEntity<CadastroFreeLance> save(@RequestBody CadastroFreeLance freelance)    {
        freelance = cadastroFreeLanceService.save(freelance);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(freelance);
    }

    /**
     * Upade do FreeLance
     *
     * @param id
     * @param freelance
     * @return CadastroFreeLance
     */
    @PutMapping("/{id}")
    public ResponseEntity<CadastroFreeLance> update(@PathVariable UUID id, @RequestBody CadastroFreeLance freelance){
        freelance = cadastroFreeLanceService.update(id, freelance);
        return ResponseEntity.ok(freelance);
    }

    /**
     * Delete CadastroFreeLance
     *
     * @param id
     * @return noContent
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        cadastroFreeLanceService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
