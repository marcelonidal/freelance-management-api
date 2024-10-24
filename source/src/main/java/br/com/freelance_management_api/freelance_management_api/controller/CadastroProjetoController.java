package br.com.freelance_management_api.freelance_management_api.controller;

import br.com.freelance_management_api.freelance_management_api.entities.CadastroProjeto;
import br.com.freelance_management_api.freelance_management_api.service.CadastroProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/cadastroprojeto")
public class CadastroProjetoController {

    @Autowired
    private CadastroProjetoService cadastroProjetoService;

    /**
     * Retorna Todos os Projetos Cadstrados
     *
     * @return Collection<CadastroProjeto>
     */
    @GetMapping
    public ResponseEntity<Collection<CadastroProjeto>> findAll(){
        var cadastroProjeto = cadastroProjetoService.findAll();
        return ResponseEntity.ok(cadastroProjeto);
    }

    /**
     * Retorna Projeto Pelo Id
     *
     * @param id
     * @return CadastroProjeto
     */
    @GetMapping("/{id}")
    public ResponseEntity<CadastroProjeto> findById(@PathVariable UUID id){
        var cadastroProjeto = cadastroProjetoService.findById(id);
        return ResponseEntity.ok(cadastroProjeto);
    }

    /**
     * Inclui Novo Projeto
     *
     * @param cadastroProjeto
     * @return CadastroProjeto
     */
    @PostMapping
    public ResponseEntity<CadastroProjeto> save(@RequestBody CadastroProjeto cadastroProjeto){
        cadastroProjeto = cadastroProjetoService.save(cadastroProjeto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(cadastroProjeto);
    }

    /**
     * Update Projeto Cadastrado
     *
     * @param id
     * @param cadastroProjeto
     * @return CadastroProjeto
     */
    @PutMapping("/{id}")
    public ResponseEntity<CadastroProjeto> update(@PathVariable UUID id, @RequestBody CadastroProjeto cadastroProjeto){
        cadastroProjeto = cadastroProjetoService.update(id, cadastroProjeto);
        return ResponseEntity.ok(cadastroProjeto);
    }

    /**
     * Exclui Projeto
     *
     * @param id
     * @return noContent
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        cadastroProjetoService.detelete(id);
        return ResponseEntity.noContent().build();

    }

}
