package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.AgendaProjetoDTO;
import br.com.freelance_management_api.service.AgendaProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendaProjetos")
@Tag(name = "Agenda Projetos", description = "Endpoints para gerenciamento de agendamentos de projetos")
public class AgendaProjetoController {

    @Autowired
    private AgendaProjetoService agendaProjetoService;

    @GetMapping
    @Operation(summary = "Listar Agendamentos de Projetos", description = "Retorna uma lista de todos os agendamentos de projetos.")
    public ResponseEntity<List<AgendaProjetoDTO>> listarAgendamentos() {
        List<AgendaProjetoDTO> agendamentos = agendaProjetoService.listar();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Agendamento de Projeto", description = "Busca um agendamento de projeto pelo ID fornecido.")
    public ResponseEntity<AgendaProjetoDTO> buscarAgendamento(@PathVariable Long id) {
        AgendaProjetoDTO agendamento = agendaProjetoService.buscar(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    @Operation(summary = "Criar Agendamento de Projeto", description = "Cria um novo agendamento de projeto.")
    public ResponseEntity<AgendaProjetoDTO> criarAgendamento(@Valid @RequestBody AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjetoDTO novoAgendamento = agendaProjetoService.criar(agendaProjetoDTO);
        return ResponseEntity.ok(novoAgendamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Agendamento de Projeto", description = "Atualiza um agendamento de projeto pelo ID fornecido.")
    public ResponseEntity<AgendaProjetoDTO> atualizarAgendamento(
            @PathVariable Long id, @Valid @RequestBody AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjetoDTO agendamentoAtualizado = agendaProjetoService.atualizar(id, agendaProjetoDTO);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Agendamento de Projeto", description = "Deleta um agendamento de projeto pelo ID fornecido.")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendaProjetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}