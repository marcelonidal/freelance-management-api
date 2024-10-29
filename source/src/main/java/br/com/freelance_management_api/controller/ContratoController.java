package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.ContratoDTO;
import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Contrato;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/contratos")
@Tag(name = "Contrato", description = "Endpoints para gerenciamento de contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Operation(summary = "Cria um novo contrato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Freelancer ou projeto indisponível para o período")
    })
    @PostMapping("/criar")
    public ResponseEntity<ContratoDTO> criarContrato(
            @RequestParam UUID freelanceId,
            @RequestParam UUID projetoId,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        // Create the contract if availability checks pass
        ContratoDTO contratoDTO = contratoService.criarContrato(freelanceId, projetoId, dataInicio, dataFim);
        return ResponseEntity.status(201).body(contratoDTO);
    }

    @Operation(summary = "Busca um contrato pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    @GetMapping("/{contratoId}")
    public ResponseEntity<ContratoDTO> buscarContrato(@PathVariable UUID contratoId) {
        ContratoDTO contratoDTO = contratoService.buscarContrato(contratoId);
        return ResponseEntity.ok(contratoDTO);
    }

}