package br.com.freelance_management_api.controller;


import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.service.FreeLanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/freelance")
@Tag(name = "Freelance", description = "Endpoints para gerenciamento de freelancers")
public class FreelanceController {

    @Autowired
    private FreeLanceService freeLanceService;

    @Operation(summary = "Busca todos os freelancers cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancers encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<Collection<FreelanceDTO>> findAll() {
        Collection<FreelanceDTO> freelanceDtos = freeLanceService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()); // ou outra coleção, como Set, se desejado
        return ResponseEntity.ok(freelanceDtos);
    }

    @Operation(summary = "Busca freelancer pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer encontrado"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FreelanceDTO> findById(@PathVariable UUID id){
        var freelance = freeLanceService.findById(id);
        return ResponseEntity.ok(toDTO(freelance));
    }

    @Operation(summary = "Cadastra um novo freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Freelancer cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<FreelanceDTO> save(@Valid @RequestBody FreelanceDTO freelanceDTO)    {
        var freelance = freeLanceService.save(toEntity(freelanceDTO));
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(toDTO(freelance));
    }

    @Operation(summary = "Atualiza um freelancer pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FreelanceDTO> update(@Valid @Parameter(description = "ID do freelancer") @PathVariable UUID id,
                                               @RequestBody FreelanceDTO freelanceDTO){
        var freelance = freeLanceService.update(id, toEntity(freelanceDTO));
        return ResponseEntity.ok(toDTO(freelance));
    }

    @Operation(summary = "Exclui um freelancer pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Freelancer excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Freelancer não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID do freelancer") @PathVariable UUID id){
        freeLanceService.delete(id);
        return ResponseEntity.noContent().build();

    }

    private FreelanceDTO toDTO(Freelance freeLance) {
        FreelanceDTO dto = new FreelanceDTO();
        dto.setIdFreelance(freeLance.getIdFreelance());
        dto.setNome(freeLance.getNome());
        dto.seteMail(freeLance.geteMail());
        dto.setTecnologias(freeLance.getTecnologias());
        dto.setEnderecoResidencia(freeLance.getEnderecoResidencia());
        dto.setNumeroResidencia(freeLance.getNumeroResidencia());
        dto.setComplementoEndereco(freeLance.getComplementoEndereco());
        dto.setQtdAnosExperiencia(freeLance.getQtdAnosExperiencia());
        dto.setValorHora(freeLance.getValorHora());
        return dto;
    }

    private Freelance toEntity(FreelanceDTO dto) {
        Freelance freeLance = new Freelance();
        freeLance.setIdFreelance(dto.getIdFreelance());
        freeLance.setNome(dto.getNome());
        freeLance.seteMail(dto.geteMail());
        freeLance.setTecnologias(dto.getTecnologias());
        freeLance.setEnderecoResidencia(dto.getEnderecoResidencia());
        freeLance.setNumeroResidencia(dto.getNumeroResidencia());
        freeLance.setComplementoEndereco(dto.getComplementoEndereco());
        freeLance.setQtdAnosExperiencia(dto.getQtdAnosExperiencia());
        freeLance.setValorHora(dto.getValorHora());
        return freeLance;
    }

}
