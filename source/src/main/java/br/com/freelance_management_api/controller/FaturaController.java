package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.FaturaDTO;
import br.com.freelance_management_api.service.FaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fatura")
@Tag(name = "Fatura", description = "Endpoints para gerenciar faturas")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    @GetMapping
    @Operation(summary = "Listar Faturas", description = "Retorna uma lista de todas as faturas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de faturas recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<FaturaDTO>> listarFaturas() {
        List<FaturaDTO> faturas = faturaService.listar();
        return ResponseEntity.ok(faturas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Fatura", description = "Recupera os detalhes de uma fatura pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fatura encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FaturaDTO> buscarFatura(
            @PathVariable @Parameter(description = "ID da fatura a ser recuperada") Long id) {
        Optional<FaturaDTO> fatura = faturaService.buscar(id);
        return fatura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar Fatura", description = "Cria uma nova fatura com os detalhes fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fatura criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FaturaDTO> criarFatura(
            @Valid @RequestBody @Parameter(description = "Detalhes da fatura a ser criada") FaturaDTO faturaDTO) {
        FaturaDTO createdFatura = faturaService.criar(faturaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFatura);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Fatura", description = "Atualiza os detalhes de uma fatura existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fatura atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FaturaDTO> atualizarFatura(
            @PathVariable @Parameter(description = "ID da fatura a ser atualizada") Long id,
            @Valid @RequestBody @Parameter(description = "Novos detalhes da fatura") FaturaDTO faturaDTO) {
        FaturaDTO updatedFatura = faturaService.atualizar(id, faturaDTO);
        return ResponseEntity.ok(updatedFatura);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Fatura", description = "Exclui uma fatura pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fatura excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fatura não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarFatura(
            @PathVariable @Parameter(description = "ID da fatura a ser deletada") Long id) {
        faturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
