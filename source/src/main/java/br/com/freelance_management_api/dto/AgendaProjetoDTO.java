package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class AgendaProjetoDTO {

    private UUID id;

    @NotNull(message = "{agendaProjeto.idProjeto.obrigatorio}")
    private UUID idProjeto;

    @NotNull(message = "{agendaProjeto.dataInicio.obrigatoria}")
    private LocalDate dataInicio;

    @NotNull(message = "{agendaProjeto.dataFim.obrigatoria}")
    private LocalDate dataFim;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

}
