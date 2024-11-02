package br.com.freelance_management_api.controller;


import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.service.FreelanceService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/freelance")
@Tag(name = "Freelance", description = "Endpoints para gerenciar freelancers cadastrados")
public class FreelanceController {

    @Autowired
    private FreelanceService freelanceService;

    @GetMapping
    @Operation(summary = "Listar Freelancers", description = "Retorna uma lista de todos os freelancers cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de freelancers recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<FreelanceDTO>> listarFreelancers() {
        return ResponseEntity.ok(freelanceService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Freelancer", description = "Recupera os detalhes de um freelancer pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Freelancer encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FreelanceDTO> buscarFreelancer(@PathVariable UUID id) {
        return ResponseEntity.ok(freelanceService.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Criar Freelancer", description = "Cria um novo freelancer com os detalhes fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Freelancer criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FreelanceDTO> criarFreelancer(@Valid @RequestBody FreelanceDTO freelanceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(freelanceService.criar(freelanceDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Freelancer", description = "Atualiza os detalhes de um freelancer existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FreelanceDTO> atualizarFreelancer(
            @PathVariable UUID id, @Valid @RequestBody FreelanceDTO freelanceDTO) {
        return ResponseEntity.ok(freelanceService.atualizar(id, freelanceDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Freelancer", description = "Exclui um freelancer pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Freelancer excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarFreelancer(@PathVariable UUID id) {
        freelanceService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
