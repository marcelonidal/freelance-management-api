package br.com.freelance_management_api.controller;

import br.com.freelance_management_api.service.DisponibilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/disponibilidade")
@Tag(name = "Disponibilidade", description = "Endpoints para verificar a disponibilidade do freelancer e as datas do projeto")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @GetMapping("/freelance")
    @Operation(
            summary = "Verificar Disponibilidade do Freelancer",
            description = "Verifica se um freelancer específico está disponível durante o período de datas fornecido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidade verificada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou parâmetros incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public boolean checkDisponibilidadeFreelance(
            @Parameter(description = "ID do freelancer para verificação") @RequestParam UUID freelanceId,
            @Parameter(description = "Data de início do período de disponibilidade (formato: YYYY-MM-DD)") @RequestParam LocalDate dataInicio,
            @Parameter(description = "Data de término do período de disponibilidade (formato: YYYY-MM-DD)") @RequestParam LocalDate dataFim) {

        return disponibilidadeService.isFreelanceDisponivel(freelanceId, dataInicio, dataFim);
    }

    @GetMapping("/projeto")
    @Operation(
            summary = "Verificar Disponibilidade do Projeto",
            description = "Verifica se o projeto pode acomodar um freelancer durante o período de datas fornecido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidade verificada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou parâmetros incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public boolean checkDisponibilidadeProjeto(
            @Parameter(description = "ID do projeto para verificação") @RequestParam UUID projetoId,
            @Parameter(description = "Data de início do período de disponibilidade (formato: YYYY-MM-DD)") @RequestParam LocalDate dataInicio,
            @Parameter(description = "Data de término do período de disponibilidade (formato: YYYY-MM-DD)") @RequestParam LocalDate dataFim) {
        return disponibilidadeService.isProjetoDisponivel(projetoId, dataInicio, dataFim);
    }
}
