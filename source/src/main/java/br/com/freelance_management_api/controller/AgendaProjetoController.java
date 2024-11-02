package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.AgendaProjetoDTO;
import br.com.freelance_management_api.service.AgendaProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendaProjetos")
@Tag(name = "Agenda Projetos", description = "Endpoints para gerenciamento de agendamentos de projetos")
public class AgendaProjetoController {

    @Autowired
    private AgendaProjetoService agendaProjetoService;

    @GetMapping
    @Operation(summary = "Listar Agendamentos de Projetos", description = "Retorna uma lista de todos os agendamentos de projetos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos de projetos recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<AgendaProjetoDTO>> listarAgendamentos() {
        List<AgendaProjetoDTO> agendamentos = agendaProjetoService.listar();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Agendamento de Projeto", description = "Busca um agendamento de projeto pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento de projeto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento de projeto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaProjetoDTO> buscarAgendamento(@PathVariable UUID id) {
        AgendaProjetoDTO agendamento = agendaProjetoService.buscar(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    @Operation(summary = "Criar Agendamento de Projeto", description = "Cria um novo agendamento de projeto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento de projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaProjetoDTO> criarAgendamento(@Valid @RequestBody AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjetoDTO novoAgendamento = agendaProjetoService.criar(agendaProjetoDTO);
        return ResponseEntity.ok(novoAgendamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Agendamento de Projeto", description = "Atualiza um agendamento de projeto pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento de projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Agendamento de projeto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AgendaProjetoDTO> atualizarAgendamento(
            @PathVariable UUID id, @Valid @RequestBody AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjetoDTO agendamentoAtualizado = agendaProjetoService.atualizar(id, agendaProjetoDTO);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Agendamento de Projeto", description = "Deleta um agendamento de projeto pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agendamento de projeto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento de projeto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarAgendamento(@PathVariable UUID id) {
        agendaProjetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}