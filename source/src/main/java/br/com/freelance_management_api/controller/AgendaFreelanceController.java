package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.AgendaFreelanceDTO;
import br.com.freelance_management_api.service.AgendaFreelanceService;
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
import java.util.UUID;

@RestController
@RequestMapping("/agendaFreelance")
@Tag(name = "Agenda Freelance", description = "Endpoints para gerenciar agendamentos de freelancers")
public class AgendaFreelanceController {

    @Autowired
    private AgendaFreelanceService agendaFreelanceService;

    @GetMapping
    @Operation(summary = "Listar Agendamentos", description = "Retorna uma lista de todos os agendamentos de freelancers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<AgendaFreelanceDTO>> listarAgendamentos() {
        List<AgendaFreelanceDTO> agendamentos = agendaFreelanceService.listar();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Agendamento", description = "Recupera os detalhes de um agendamento pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaFreelanceDTO> buscarAgendamento(
            @PathVariable @Parameter(description = "ID do agendamento a ser recuperado") UUID id) {
        AgendaFreelanceDTO agendamento = agendaFreelanceService.buscar(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    @Operation(summary = "Criar Agendamento", description = "Cria um novo agendamento de freelancer com os detalhes fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaFreelanceDTO> criarAgendamento(
            @Valid @RequestBody @Parameter(description = "Detalhes do agendamento a ser criado") AgendaFreelanceDTO agendaFreelanceDTO) {
        AgendaFreelanceDTO createdAgendamento = agendaFreelanceService.criar(agendaFreelanceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAgendamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Agendamento", description = "Atualiza os detalhes de um agendamento existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaFreelanceDTO> atualizarAgendamento(
            @PathVariable @Parameter(description = "ID do agendamento a ser atualizado") UUID id,
            @Valid @RequestBody @Parameter(description = "Novos detalhes do agendamento") AgendaFreelanceDTO agendaFreelanceDTO) {
        AgendaFreelanceDTO updatedAgendamento = agendaFreelanceService.atualizar(id, agendaFreelanceDTO);
        return ResponseEntity.ok(updatedAgendamento);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Agendamento", description = "Exclui um agendamento pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarAgendamento(
            @PathVariable @Parameter(description = "ID do agendamento a ser deletado") UUID id) {
        agendaFreelanceService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
