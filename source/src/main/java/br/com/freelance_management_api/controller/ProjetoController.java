package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/projeto")
@Tag(name = "Projeto", description = "Endpoints para gerenciar projetos cadastrados")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    @Operation(summary = "Listar Projetos", description = "Retorna uma lista de todos os projetos cadastrados.")
    public ResponseEntity<Collection<ProjetoDTO>> listarProjetos() {
        return ResponseEntity.ok(projetoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Projeto", description = "Recupera os detalhes de um projeto pelo ID fornecido.")
    public ResponseEntity<ProjetoDTO> buscarProjeto(@PathVariable UUID id) {
        return ResponseEntity.ok(projetoService.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Criar Projeto", description = "Cria um novo projeto com os detalhes fornecidos.")
    public ResponseEntity<ProjetoDTO> criarProjeto(@Valid @RequestBody ProjetoDTO projetoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.criar(projetoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Projeto", description = "Atualiza os detalhes de um projeto existente.")
    public ResponseEntity<ProjetoDTO> atualizarProjeto(
            @PathVariable UUID id, @Valid @RequestBody ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.atualizar(id, projetoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Projeto", description = "Exclui um projeto pelo ID fornecido.")
    public ResponseEntity<Void> deletarProjeto(@PathVariable UUID id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
