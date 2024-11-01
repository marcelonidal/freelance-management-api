package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.ContratoDTO;
import br.com.freelance_management_api.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contrato")
@Tag(name = "Contrato", description = "Endpoints para gerenciar contratos entre freelancers e projetos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    @Operation(summary = "Listar Contratos", description = "Retorna uma lista de todos os contratos.")
    public ResponseEntity<List<ContratoDTO>> listarContratos() {
        List<ContratoDTO> contratos = contratoService.listar();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Contrato", description = "Recupera um contrato pelo ID fornecido.")
    public ResponseEntity<ContratoDTO> buscarContrato(@PathVariable UUID id) {
        return ResponseEntity.ok(contratoService.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Criar Contrato", description = "Cria um novo contrato com os detalhes fornecidos.")
    public ResponseEntity<ContratoDTO> criarContrato(@Valid @RequestBody ContratoDTO contratoDTO) {
        ContratoDTO novoContrato = contratoService.criar(contratoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.criar(novoContrato));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Contrato", description = "Atualiza um contrato existente com os novos detalhes fornecidos.")
    public ResponseEntity<ContratoDTO> atualizarContrato(
            @PathVariable UUID id, @Valid @RequestBody ContratoDTO contratoDTO) {
        return ResponseEntity.ok(contratoService.atualizar(id, contratoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Contrato", description = "Deleta um contrato pelo ID fornecido.")
    public ResponseEntity<Void> deletarContrato(@PathVariable UUID id) {
        contratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}