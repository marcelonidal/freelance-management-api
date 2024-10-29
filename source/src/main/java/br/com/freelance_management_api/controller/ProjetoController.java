package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.service.ProjetoService;
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
@RequestMapping("/cadastroprojeto")
@Tag(name = "Projeto", description = "Endpoints para gerenciamento de cadastros")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Operation(summary = "Busca todos os projetos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<Collection<ProjetoDTO>> findAll(){
        Collection<ProjetoDTO> projetos = projetoService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projetos);
    }

    @Operation(summary = "Busca projeto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> findById(@PathVariable UUID id){
        var projeto = projetoService.findById(id);
        return ResponseEntity.ok(toDTO(projeto));
    }

    @Operation(summary = "Cadastra um novo projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Projeto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProjetoDTO> save(@Valid @RequestBody ProjetoDTO projetoDTO){
        var projeto = projetoService.save(toEntity(projetoDTO));
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(toDTO(projeto));
    }

    @Operation(summary = "Atualiza um projeto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> update(@Valid @Parameter(description = "ID do projeto") @PathVariable UUID id,
                                              @RequestBody ProjetoDTO projetoDTO) {
        var projeto = projetoService.update(id, toEntity(projetoDTO));
        return ResponseEntity.ok(toDTO(projeto));
    }

    @Operation(summary = "Exclui um projeto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Projeto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID do projeto") @PathVariable UUID id) {
        projetoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProjetoDTO toDTO(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setIdProjeto(projeto.getIdProjeto());
        dto.setNomeProjeto(projeto.getNomeProjeto());
        dto.setTempoEmHoras(projeto.getTempoEmHoras());
        dto.setEmpresaContratanteProjeto(projeto.getEmpresaContratanteProjeto());
        dto.setPaisProjeto(projeto.getPaisProjeto());
        dto.setTecnologias(projeto.getTecnologias());
        dto.setEmailContato(projeto.getEmailContato());
        dto.setCobreCustoFreelance(projeto.getCobreCustoFreelance());
        dto.setValorCustoPago(projeto.getValorCustoPago());
        dto.setValorHoraPago(projeto.getValorHoraPago());
        return dto;
    }

    private Projeto toEntity(ProjetoDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(dto.getIdProjeto());
        projeto.setNomeProjeto(dto.getNomeProjeto());
        projeto.setTempoEmHoras(dto.getTempoEmHoras());
        projeto.setEmpresaContratanteProjeto(dto.getEmpresaContratanteProjeto());
        projeto.setPaisProjeto(dto.getPaisProjeto());
        projeto.setTecnologias(dto.getTecnologias());
        projeto.setEmailContato(dto.getEmailContato());
        projeto.setCobreCustoFreelance(dto.getCobreCustoFreelance());
        projeto.setValorCustoPago(dto.getValorCustoPago());
        projeto.setValorHoraPago(dto.getValorHoraPago());
        return projeto;
    }

}
